import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {CommonService} from '../../../services/common.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {ImpulseService} from '../../../services/impulse.service';
import {UtilService} from '../../../helpers/util.service';
import {AngularEditorConfig} from '@kolkov/angular-editor';
import {SessionService} from '../../../helpers/session.service';
import {UploaderFileService} from "../../../helpers/uploader-file.service";
import {ModalService} from "../../../helpers/modal.service";

@Component({
  selector: 'app-new-edit-impulse',
  templateUrl: './new-edit-impulse.component.html',
  styleUrls: ['./new-edit-impulse.component.css']
})
export class NewEditImpulseComponent implements OnInit {
  comment = '';
  allComments = [];

  copyArray: Array<any>;
  isTrue = true;
  isFalse = false;
  aprove: any = null;
  allTypes: any;
  numberOfCharacters = 0;
  numberOfWordsRedaction = 0;
  maxNumberOfCharacters = 3000;
  urlOK = true;
  validAndProtocol = true;
  counting = true;
  editorConfig: AngularEditorConfig = {
    editable: this.data.info.editableSolitude,
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
  fileType = 'image/*';
  imageFile: any;
  editorContentConfig: AngularEditorConfig = {
    editable: this.data.info.editableSolitude,
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
  thumbnailPreview: any;

  /**
   * update or edit information about an impulse
   * @param formBuilder
   * @param session
   * @param commonService
   * @param data
   * @param impulseService
   * @param modal
   * @param dialogRef
   * @param translate
   * @param utilService
   * @param ufs
   */
  constructor(private formBuilder: FormBuilder, private session: SessionService,
              private commonService: CommonService, @Inject(MAT_DIALOG_DATA) public data: any,
              private impulseService: ImpulseService, private modal: ModalService,
              private dialogRef: MatDialogRef<NewEditImpulseComponent>,
              public translate: TranslateService, private utilService: UtilService, public ufs: UploaderFileService) {
    this.editorConfig.placeholder = this.translate.instant('label.impulse.content');
    this.editorContentConfig.placeholder = this.translate.instant('label.impulse.content');

    this.editorConfig.editable = this.data.info.editableSolitude;
    this.editorContentConfig.editable = this.data.info.editableContent;

    if (data?.info?.content?.image_link) {
      this.ufs.getData(this.data.info.content.image_link, data => {
        this.thumbnailPreview = data;
      })
    }
  }

  ngOnInit(): void {
    // console.log('this.info', this.data.info);
    this.allComments = this.data.info.content.observations;
    this.loadCommons();
    if (this.data.info.id) {
      if (this.data.info.typeObj?.PUBLISHED_URL) {
        this.onkeyup(this.data.info.comments, this.isFalse);
      }
      if (this.data.info.typeObj?.OWN_WRITING) {
        this.onkeyup(this.data.info.content.content, this.isTrue);
      }
      if (this.data.info.typeObj?.WORDING_REQUESTED && this.data.info.id) {
        this.onkeyup(this.data.info.comments, this.isFalse);
        this.onkeyup(this.data.info.content.content, this.isTrue);
      }
      if (this.data.info.typeObj?.WORDING_REQUESTED && !this.data.info.id) {
        this.onkeyup(this.data.info.comments, this.isFalse);
      }

    }

  }

  /*
load commons services to fill all select inputs
* */
  loadCommons(): void {
    this.commonService.listImpulseTypes(response => {
      // Se comenta según incidencia de Carlos
      /*if (this.session.getAccessConfig().CLIENT) {
        response.shift();
      }*/
      this.allTypes = response;
    });
  }

  /**
   * realiza el guardado de los temas de rastreo, según si es masiva o carga individual
   * @param form
   */
  save(form: any) {
    if (this.data.info.id_keyword && this.data.info.typeObj) {
      if (form.valid) {
        if (this.urlOK && this.validAndProtocol){
          if (this.checkWords(this.data) < this.maxNumberOfCharacters) {
            if (this.data.nuevo) {
              this.impulseService.save(this.data.scanner.uuid, this.data.info, () => {
                this.dialogRef.close(true);
              });
            } else {
              this.impulseService.update(this.data.info, () => {
                this.dialogRef.close(true);
              });
            }
          } else {
            this.utilService.showNotification('error.long_content', 'danger');
          }

        } else{
          this.utilService.showNotification('error.urlDuplicatedInvalid', 'danger');
        }

      } else {
        this.utilService.showNotification('error.incomplete.form', 'danger');
      }
    } else {
      this.utilService.showNotification('error.incomplete.form', 'danger');
    }
  }

  /**
   *call a service to ask for aproval to another profile
   * @param form
   */
  checkWords(data) {
    if (this.data.info.typeObj?.WORDING_REQUESTED || this.data.info.typeObj?.PUBLISHED_URL) {
      return this.utilService.countingWords(data.info.comments);
    }
    if (this.data.info.typeObj?.OWN_WRITING) {
      return this.utilService.countingWords(data.info.content.content);
    }
  }

  /**
   * aks to a client or admin to approve an impulse
   * @param form
   */
  askApproval(form: any) {
    if (form.valid && this.data.info.content) {
      if (this.checkWords(this.data) < this.maxNumberOfCharacters) {
        this.modal.openConfirmation({
          message: 'message.confirmation.impulse.ask_approved',
          onConfirm: () => {
            if (this.data.nuevo) {
              this.impulseService.save(this.data.scanner.uuid, this.data.info, () => {
                this.impulseService.askApproval(this.data.info.content.id, this.comment, () => {
                  this.dialogRef.close(true);
                });
              });
            } else {
              this.impulseService.update(this.data.info, response => {
                this.impulseService.askApproval(response.content.id, this.comment, () => {
                  this.dialogRef.close(true);
                });
              });
            }
          },
        });


      } else {
        this.utilService.showNotification('error.long_content', 'danger');
      }
    } else {
      this.utilService.showNotification('error.required_fields', 'danger');
    }
  }

  /** call a approve to    /* call a approve to  reject an impulse
   an impulse
   * @param form
   */
  //TODO revisar el que falta  del perfil de quien aprueba
  approve(form: any) {
    this.modal.openConfirmation({
      message: 'message.confirmation.impulse.approved',
      onConfirm: () => {
        this.impulseService.approve(this.data.info.content.id, this.comment, response => {
          response.user = this.session.getUser().name;
          response.profile = 'falta el nombre completo de quien agregó este comentario y su perfil';
          this.allComments.push(response);
          this.dialogRef.close(true);
        });
      },
    });
  }

  /**
   * call a service to  reject an impulse
   * @param form
   */
  reject(form: any) {
    if (this.comment !== '' && this.aprove !== null) {
      this.modal.openConfirmation({
        message: 'message.confirmation.impulse.reject',
        onConfirm: () => {
          this.impulseService.reject(this.data.info.content.id, this.comment, response => {
            response.user = this.session.getUser().name;
            response.profile = 'falta el nombre completo de quien agregó este comentario y su perfil';
            this.allComments.push(response);
            this.dialogRef.close(true);
          });
        },
      });

    } else {
      this.utilService.showNotification('error.impulse.comment', 'danger');
    }
  }

  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   * count characters
   * @param data
   * @param isRedaction
   */
  onkeyup(data: any, isRedaction) {
    if (isRedaction) {
      this.numberOfWordsRedaction = this.utilService.countingWords(data);
    } else {
      this.numberOfCharacters = this.utilService.countingWords(data);
    }

  }

  /**
   *
   * image preview
   * @param $event
   */
  checkFile($event: any) {
    this.data.info.content.image_link = $event;

    this.ufs.getData(this.data.info.content.image_link, data => {
      this.thumbnailPreview = data;
    })
  }

  /**
   * cleanProofAtt fields after add a proof
   */
  private cleanImagen(): void {
    this.imageFile = {
      content: {
        lastModified: 0,
        name: '',
        size: 0,
        type: '',
      },
      file_address: '',
      file_type: '',
      file_description: '',
    };
  }

  /**
   * Check if an URL is valid or is repeated
   * @param url
   */
  checkURL(url: any) {

    if (url){
      this.urlOK = true;
      this.validAndProtocol =  true;
    //  const isValid = this.utilService.validURL(url);
      const isValid = this.utilService.checkSpaces(url);
      const validProtocol = this.utilService.checkProtocolo(url);
      // console.log('hay url');
      if (isValid && validProtocol) {
        this.validAndProtocol =  true;
      } else {
      //  console.log('no es valida');
        this.validAndProtocol =  false;

      }
    }
  }
  checkURL_DEPRECATED(url: any) {
    this.urlOK = true;
    this.validAndProtocol =  true;
  //  const isValid = this.utilService.validURL(url);
    const isValid = this.utilService.checkSpaces(url);
    const validProtocol = this.utilService.checkProtocolo(url);
    if (url){
      // console.log('hay url');
      if (isValid && validProtocol) {
        // console.log('es valida ');
        if (this.data.impulses.length !== 0){
          // console.log('hay más de un impulso');
          this.data.impulses.forEach(u => {
            // console.log('u.real_publish_link.toLowerCase()', u.real_publish_link.toLowerCase());
            // console.log('=== url.toLowerCase()', url.toLowerCase());
            if (u.real_publish_link && (u.real_publish_link.toLowerCase() === url.toLowerCase())) {
              // console.log('la encontré');
              this.urlOK =  false;
            }
          });
        } else {
          // console.log('no hay más de un impulso, asi que pasa');
          this.urlOK = true;
        }
      } else {
      //  console.log('no es valida');
        this.validAndProtocol =  false;

      }
    }
  }

  /**
   *  check if an Url is valid and is not duplicated
   *  Remove asked not valid url format
   * @param url
   */
  checkValidURL(url: any) {
    this.validAndProtocol = true;
    // const isValid = this.utilService.validURL(url);
    const isValid = this.utilService.checkSpaces(url);
    const validProtocol = this.utilService.checkProtocolo(url);
    if (!isValid || !validProtocol){
      this.validAndProtocol =  false;
    }
  }
}
