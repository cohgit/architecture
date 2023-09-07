import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, NgForm } from '@angular/forms';
import { CommonService } from '../../../services/common.service';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHelperService } from '../../../helpers/translate-helper.service';
import { ClientService } from '../../../services/client.service';
import { UploaderFileService } from '../../../helpers/uploader-file.service';
import { FileUploadComponent } from '../../../shared/file-upload/file-upload.component';
import { UtilService } from '../../../helpers/util.service';
import { SessionService } from 'src/app/helpers/session.service';

@Component({
  selector: 'app-new-edit-client',
  templateUrl: './new-edit-client.component.html',
  styleUrls: ['./new-edit-client.component.css'],
})
export class NewEditClientComponent implements OnInit {
  info: any = {};
  language: string;
  @Input() infoClient: any = {};
  @Output() close = new EventEmitter<any>();
  /**
   * update a client information
   * @param formBuilder
   * @param commonService
   * @param translate
   * @param clientService
   * @param translateHelper
   */
  fileType = '.jpg,.jpeg,.png';
  thumbnailPreview = '';
  selectedFiles: FileList;
  isBig = false;
  model: any;
  logoRemove = false;
  newName = '';
  showPeriocity = false;
  userInfo = {};
  constructor(
    private formBuilder: FormBuilder,
    private commonService: CommonService,
    public translate: TranslateService,
    private clientService: ClientService,
    public translateHelper: TranslateHelperService,
    public ufs: UploaderFileService,
    private utilService: UtilService,
    public session: SessionService
  ) {}

  ngOnInit(): void {
    if (
      this.infoClient.reference_link_logo &&
      this.infoClient.reference_link_logo !== ''
    ) {
      this.getImagen(this.infoClient.reference_link_logo);
    } else {
      this.thumbnailPreview = '../../../assets/img/logo.png';
    }
    this.userInfo = this.session?.getUser();
    this.checkUser(this.userInfo);
  }

  checkUser(user) {
    console.log(user.permissions.profile);
    if (user.permissions?.profile?.ADMIN || user.permissions?.profile?.EDITOR) {
      this.showPeriocity = true;
    }
  }
  /**
   *
   * image preview
   * @param route
   */
  getImagen(route) {
    this.ufs.getData(route, (data) => {
      this.thumbnailPreview = data;
    });
  }
  /**
   * Receives the data of the main form, allows to update a new client
   * @param form
   */
  onConfirmClick(form: NgForm) {
    if (form.valid) {
      this.uploadandSave(form.value);
    }
  }
  /**
   * function to change language
   *
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }

  /**
   * select a file
   * @param event
   */
  selectFile(event) {
    this.isBig = false;
    if (event.target.files[0].size > 2097152) {
      this.isBig = true;
      this.utilService.showNotification('error.file_size', 'danger');
    } else {
      this.selectedFiles = event.target.files;
    }
  }

  /**
   * Upload a file and save the client
   */
  uploadandSave(form) {
    const data = Object.assign(this.infoClient, form);

    console.log('data', data);
    // check if logo size is too big
    if (!this.isBig) {
      // check if the client upload a new logo or restore remove logo
      if (!this.logoRemove) {
        //if there is no logo call update service
        if (!this.selectedFiles) {
          this.clientService.update(data, (response) => {
            this.info = response;
            this.close.emit();
          });
        } else {
          //if there is a logo call update service and upload service, to AWS bucket
          const file = this.selectedFiles.item(0);
          this.ufs.upload(file, (keyFile) => {
            this.model = keyFile;
            this.getImagen(this.model);
            data.reference_link_logo = this.model;
            this.clientService.update(data, (response) => {
              this.info = response;
              this.close.emit();
            });
          });
        }
      } else {
        data.reference_link_logo = null;
        this.clientService.update(data, (response) => {
          this.info = response;
          this.close.emit();
        });
      }
    } else {
      this.utilService.showNotification('error.file_size', 'danger');
    }
  }

  /**
   * restore Remove logo
   */
  restoreLogo() {
    this.thumbnailPreview = '../../../assets/img/logo.png';
    this.logoRemove = true;
  }
}
