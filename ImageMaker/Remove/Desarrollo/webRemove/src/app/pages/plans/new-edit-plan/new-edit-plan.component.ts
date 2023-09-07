import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, NgForm} from '@angular/forms';
import {CommonService} from '../../../services/common.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {PlanService} from '../../../services/plan.service';
import {UtilService} from '../../../helpers/util.service';
import {SuscriptionService} from '../../../services/suscription.service';
import {AngularEditorConfig} from "@kolkov/angular-editor";

@Component({
  selector: 'app-new-edit-plan',
  templateUrl: './new-edit-plan.component.html',
  styleUrls: ['./new-edit-plan.component.css']
})
export class NewEditPlanComponent implements OnInit {
  @ViewChild('client_name') inputName;
  isNuevo = false;
  isEditable = false;
  info: any = {};
  endingButton = false;
  loading = false;
  placeholderBuscar = '';
  numberOfCharacters = 0;

  noResults = '';
  allTypes = [];
  client = {
    client_name: '',
    client_email: '',

  };
  clientsToSend = [];
  encontrado = false;
  addClient = false;
  isClient = true;
  sendForm = false;
  validName = true;
  validMail = true;
  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: 'auto',
    minHeight: '10rem',
    maxHeight: '12rem',
    width: 'auto',
    minWidth: '0',
    translate: 'yes',
    enableToolbar: true,
    showToolbar: true,
    placeholder: '',
    defaultParagraphSeparator: '',
    defaultFontName: '',
    defaultFontSize: '',
    uploadWithCredentials: false,
    sanitize: true,
    toolbarPosition: 'top',
    toolbarHiddenButtons: [
      ['fontName', 'heading'],
      [
        'backgroundColor',
        'customClasses',
        'insertVideo',
        'removeFormat',
        'insertImage'
      ]
    ]
  };

  /**
   * update or edit information about a plan
   * @param formBuilder
   * @param utilService
   * @param commonService
   * @param planService
   * @param suscriptionService
   * @param data
   * @param dialogRef
   * @param translate
   */
  constructor(private formBuilder: FormBuilder, public utilService: UtilService,
              private commonService: CommonService,
              private planService: PlanService,
              private suscriptionService: SuscriptionService,
              @Inject(MAT_DIALOG_DATA) private data: any,
              private dialogRef: MatDialogRef<NewEditPlanComponent>,
              public translate: TranslateService) {
    this.isNuevo = data.nuevo;
    this.isEditable = data.editable;
    this.info = data.info;
    this.clientsToSend = data.info.clientSuggestions;

  }

  ngOnInit(): void {
    //('this.info', this.info);
    this.placeholderBuscar = this.translate.instant('placeholder.search');
    this.noResults = this.translate.instant('placeholder.no_result');
    this.loadCommons();


  }

  /*
load commons services to fill all select inputs
* */
  loadCommons(): void {
    this.commonService.listSearchTypes(response => {
      this.allTypes = response;
    });
  }

  /**
   * call services update or save as appropriate
   * @param form
   */
  onConfirmClick(form: NgForm): void {
    this.endingButton = true;
    //console.log('oncofirm', this.info);
    if (form.valid) {
      if (this.checkLicence(this.info.access_scanner, this.info.limit_credits) &&
        this.checkLicence(this.info.access_monitor, this.info.total_monitor_licenses) &&
        this.checkLicence(this.info.access_transforma, this.info.total_transforma_licenses) &&
        this.checkLicence(this.info.access_deindexation, this.info.max_url_to_deindexate)) {
        if (this.info.access_transforma) {
          if (this.info.total_transforma_licenses && (this.info.max_url_to_impulse || this.info.max_url_to_remove) && this.info.target_page) {
            const data = Object.assign(this.info, form.value);
            data.clientSuggestions = this.clientsToSend;
            if (this.isNuevo) {
              this.planService.save(data, () => {
                this.dialogRef.close(true);
              });
            } else {
              this.planService.update(data, () => {
                this.dialogRef.close(true);
              });
            }
          } else {
            this.utilService.showNotification('error.plan_transform', 'danger');
          }
        } else {
          const data = Object.assign(this.info, form.value);
          data.clientSuggestions = this.clientsToSend;
          if (this.isNuevo) {
            this.planService.save(data, () => {
              this.dialogRef.close(true);
            });
          } else {
            this.planService.update(data, () => {
              this.dialogRef.close(true);
            });
          }
        }
      } else {
        this.utilService.showNotification('error.licence_no', 'danger');
      }
    } else {
      this.utilService.showNotification('error.required_fields', 'danger');
    }
  }

  /**
   * helper function to check if acess has a licence
   * @param access
   * @param amount
   */
  checkLicence(access: boolean, amount: number) {
    //console.log(access, amount);
    if (access && (!amount || amount === null || amount === undefined)) {
      return false;
    }
    return !(access && amount === 0);
  }

  /**
   * add clients to an array, to send a plan
   * @param item
   */
  addItem(item): void {
    this.validName = true;
    this.validMail = true;
    this.encontrado = false;
    let addToList = {};
    if (Object.keys(item).length !== 0 && item.client_name && item.client_email) {
      addToList = {
        client_name: item.client_name,
        client_email: item.client_email
      };

      if (this.clientsToSend.length !== 0) {
        this.clientsToSend.forEach(obj => {
          if (this.utilService.checkCaracter(obj.client_email) === this.utilService.checkCaracter(item.client_email)) {
            this.encontrado = true;
          }
        });
        if (!this.encontrado) {
          this.clientsToSend.push(addToList);
          this.addClient = false;
          this.sendForm = false;
          this.cleanFiels();
        }
      } else {
        this.clientsToSend.push(addToList);
        this.addClient = false;
        this.sendForm = false;
        this.cleanFiels();
      }
    } else {

      if (Object.keys(item).length === 0) {
        this.validName = false;
        this.validMail = false;
      } else {
        if (!item.client_name) {
          this.validName = false;
        }
        if (!item.client_email) {
          this.validMail = false;
        }
      }
    }
  }

  /**
   * Remove clientes from a list
   * @param data
   */
  removeItem(data: any) {
    if (data.id) {
      this.clientsToSend.forEach((item) => {
        if (item === data) {
          data.markToDelete = !data.markToDelete;
        }
      });
    } else {
      this.clientsToSend.forEach((item) => {
        if (item === data) {
          const index = this.clientsToSend.findIndex((i) => i === data);
          this.clientsToSend.splice(index, 1);
        }
      });
    }
  }

  /* removeItem(data: any): void {
     this.clientsToSend.forEach(item => {
       if (item === data) {
         const index = this.clientsToSend.findIndex(i => i === data);
         this.clientsToSend.splice(index, 1);
       }
     });
   }*/
  /**
   * Clean clients fields
   * @private
   */
  private cleanFiels(): void {
    this.client = {
      client_name: '',
      client_email: '',
    };
  }

  /**
   * Send email suggestions
   * @param suggestion
   */
  sendEmail(suggestion: any): void {
    this.planService.sendSuggestions([suggestion.uuid]);
  }

  /**
   * validate email client
   * @param clientEmail
   */
  checkEmail(clientEmail: string): void {
    this.loading = false;
    const isMail = this.utilService.validateEmail(clientEmail);
    if (clientEmail !== '' && isMail) {
      this.loading = true;
      this.sendForm = false;
      this.isClient = true;
      if (clientEmail !== '') {
        this.commonService.verfyEmailClient(clientEmail,
          (response) => {
            this.sendForm = true;
            this.loading = false;
            if (!response) {
              this.isClient = false;
            }
          });
      }
      this.addClient = true;
    } else {
      this.sendForm = false;
    }

  }

  /**
   * count characters
   * @param textValue
   */
  onModelChange(textValue: string): void {
    this.numberOfCharacters = textValue.length;
  }
}
