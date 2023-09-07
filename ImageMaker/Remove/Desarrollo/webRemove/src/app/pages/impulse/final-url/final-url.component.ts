import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, NgForm } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ImpulseService } from '../../../services/impulse.service';
import { UtilService } from '../../../helpers/util.service';

@Component({
  selector: 'app-final-url',
  templateUrl: './final-url.component.html',
  styleUrls: ['./final-url.component.css'],
})
export class FinalURLComponent implements OnInit {
  info: any;
  today = new Date();
  urlOK = true;
  validAndProtocol = true;
  impulses = [];
  urltoAssigned = '';

  /**
   * save the final URL of an impulse
   * @param formBuilder
   * @param impulseService
   * @param data
   * @param utilService
   * @param dialogRef
   * @param translate
   */
  constructor(
    private formBuilder: FormBuilder,
    private impulseService: ImpulseService,
    @Inject(MAT_DIALOG_DATA) private data: any,
    public utilService: UtilService,
    private dialogRef: MatDialogRef<FinalURLComponent>,
    public translate: TranslateService
  ) {
    this.info = data.info;
    this.impulses = this.data.impulses;
  }

  ngOnInit(): void {
    //  console.log('this.info', this.data);
  }

  /**
   *  call services save pubish link
   * @param form
   */
  onConfirmClick(form: NgForm): void {
    if (form.valid) {
      if (this.urlOK && this.validAndProtocol) {
        this.info.real_publish_link = this.urltoAssigned;
        this.impulseService.publish(this.info, () => {
          this.dialogRef.close(true);
        });
        this.dialogRef.close(true);
      } else {
        this.utilService.showNotification(
          'error.urlDuplicatedInvalid',
          'danger'
        );
      }
    } else {
      this.utilService.showNotification('error.required_fields', 'danger');
    }
  }

  /**
   * Check if an URL is valid or is repeated
   * @param url
   */
  checkURL(url: any) {
    this.urlOK = true;
    this.validAndProtocol = true;
    // const isValid = this.utilService.validURL(url);
    const isValid = this.utilService.checkSpaces(url);
    const validProtocol = this.utilService.checkProtocolo(url);
    if (url) {
      if (isValid && validProtocol) {
        if (this.impulses.length !== 0) {
          this.impulses.forEach((u) => {
            if (
              u.real_publish_link &&
              u.real_publish_link.toLowerCase() === url.toLowerCase()
            ) {
              this.urlOK = false;
            }
          });
        } else {
          this.urlOK = true;
        }
      } else {
        this.validAndProtocol = false;
      }
    }
  }
}
