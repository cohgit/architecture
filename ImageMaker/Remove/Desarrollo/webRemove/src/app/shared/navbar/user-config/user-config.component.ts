import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { CommonService } from '../../../services/common.service';
import { TranslateHelperService } from '../../../helpers/translate-helper.service';
import { AccountConfigService } from 'src/app/services/account-config.service';
import { SessionService } from 'src/app/helpers/session.service';
import { UtilService } from '../../../helpers/util.service';
import { UploaderFileService } from '../../../helpers/uploader-file.service';

class DialogData {}

@Component({
  selector: 'app-user-config',
  templateUrl: './user-config.component.html',
  styleUrls: ['./user-config.component.css'],
})
export class UserConfigComponent implements OnInit {
  user: any;
  placeholderBuscar = '';
  noResults = '';

  password = {
    oldPassword: '',
    newPassword: '',
  };
  samePass = false;
  payment = {
    card: '',
    expired: '',
    cvv: '',
    cardholder: '',
  };
  listPayment = [];
  language: string;
  allCountries = [];
  fltCountries = [];
  allLanguages = [];
  hide = true;
  sendFormKey = false;
  fileType = '.jpg,.jpeg,.png';
  thumbnailPreview = '';
  selectedFiles: FileList;
  isBig = false;
  model: any;
  isAdmin = false;
  logoRemove = false;
  is15 = 15;
  is1 = 1;
  constructor(
    public dialogRef: MatDialogRef<UserConfigComponent>,
    public commonService: CommonService,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    public translate: TranslateService,
    public session: SessionService,
    public translateHelper: TranslateHelperService,
    private configService: AccountConfigService,
    public utilService: UtilService,
    public ufs: UploaderFileService
  ) {
    this.user = this.data;
    console.log('this.user', this.user);
  }

  ngOnInit(): void {
    this.checkProfile();
    //  validación del previsualizar, si tiene algo muestra la imagen si no el reference_link_logo de remove
    if (this.user.reference_link_logo && this.user.reference_link_logo !== '') {
      this.getImagen(this.user.reference_link_logo);
    } else {
      this.thumbnailPreview = '../../../assets/img/logo.png';
    }
    this.placeholderBuscar = this.translate.instant('placeholder.search');
    this.noResults = this.translate.instant('placeholder.no_result');
    this.loadCommons();
  }

  /**
   * check if a user is admin
   */
  checkProfile() {
    if (this.session.getAccessConfig().ADMIN) {
      this.isAdmin = true;
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
   *
   * @param c1
   * @param c2
   */
  compareTag(c1: any, c2: any): boolean {
    return c1 && c2 && c1.tag === c2.tag;
  }

  /**
   * Load commons services
   */
  loadCommons() {
    this.commonService.listLanguages((response) => {
      this.allLanguages = response;
    });
    this.commonService.listCountries((response) => {
      this.allCountries = response;
      this.fltCountries = this.allCountries.slice();
    });
  }

  /**
   * change language of the user
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }

  /**
   * update configuration
   * @param form
   */
  updateConfig(form: any): void {
    if (form.valid && !this.isAdmin) {
      this.uploadandSave(form.value);
    }
    if (form.valid && this.isAdmin) {
      this.configService.update(this.user, () => {
        this.session.refreshClientSession(this.user);
        this.translateHelper.setTransLanguage(this.user.language);
        //  this.dialogRef.close(true);
      });
    }
  }

  /**
   * update password
   * @param form
   */
  updatePassword(form: any): void {
    this.sendFormKey = true;
    if (form.valid) {
      if (this.password.newPassword !== this.password.oldPassword) {
        this.configService.updatePassword(
          this.password,
          () => {
            this.password = { newPassword: '', oldPassword: '' };
            this.sendFormKey = false;
            this.dialogRef.close(true);
          },
          {
            errorFunction: () => {
              this.password = { newPassword: '', oldPassword: '' };
              this.sendFormKey = false;
            },
          }
        );
      } else {
        this.utilService.showNotification('error.same_pass', 'danger');
      }
    }
  }

  /**
   * select a file
   * @param event
   */
  selectFile(event) {
    this.isBig = false;
    //console.log('event', event.target.files[0].size);
    if (event.target.files[0].size > 2097152) {
      this.isBig = true;
      this.utilService.showNotification('error.file_size', 'danger');
    } else {
      this.selectedFiles = event.target.files;
    }
  }

  /**
   * Upload a file and save the user
   */
  uploadandSave(form) {
    const data = Object.assign(this.user, form);
    // console.log('this.isBig', this.isBig);
    if (!this.isBig) {
      if (!this.logoRemove) {
        if (!this.selectedFiles) {
          data.reference_link_logo = this.model;
          this.configService.update(this.user, () => {
            this.session.refreshClientSession(this.user);
            this.translateHelper.setTransLanguage(this.user.language);
          });
        } else {
          const file = this.selectedFiles.item(0);
          this.ufs.upload(file, (keyFile) => {
            this.model = keyFile;
            this.getImagen(this.model);
            data.reference_link_logo = this.model;
            //console.log('data antes del update', data);
            this.configService.update(this.user, () => {
              this.session.refreshClientSession(this.user);
              this.translateHelper.setTransLanguage(this.user.language);
            });
          });
        }
      } else {
        data.reference_link_logo = null;
        this.configService.update(this.user, () => {
          this.session.refreshClientSession(this.user);
          this.translateHelper.setTransLanguage(this.user.language);
          //  this.dialogRef.close(true);
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

  checkEquals(pass) {
    this.samePass = false;
    if (pass.newPassword === pass.oldPassword) {
      this.samePass = true;
    }
  }
}
