import {Component, OnInit, Renderer2} from '@angular/core';
import {UtilService} from '../helpers/util.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateHelperService} from '../helpers/translate-helper.service';
import {TranslateService} from '@ngx-translate/core';
import {NgForm} from '@angular/forms';
import {ResetPassService} from '../services/reset-pass.service';

@Component({
  selector: 'app-new-key',
  templateUrl: './new-key.component.html',
  styleUrls: ['./new-key.component.css']
})
export class NewKeyComponent implements OnInit {
  pass: any;
  confirmPass: any;
  language: string;
  checkParams: any = {};
  uuid: string;
  expired = false;
  message = '';
  isClient = true;
  showGoLogin = false;
  hide = true;

  /**
   * Manage client or administrator password reset
   * @param utilService
   * @param router
   * @param renderer
   * @param translateHelper
   * @param translate
   * @param activatedRoute
   * @param restoreService
   */
  constructor(private utilService: UtilService, private router: Router, private renderer: Renderer2,
              public translateHelper: TranslateHelperService, public translate: TranslateService,
              private activatedRoute: ActivatedRoute, private restoreService: ResetPassService) {

    this.language = translateHelper.getTransLanguage();

    this.activatedRoute.data.subscribe(data => {
      this.checkParams = data;
      this.renderer.addClass(document.body, this.checkParams.class);
      if (this.checkParams.ADMIN) {
        this.isClient = false;
      }
    });

    this.renderer.addClass(document.body, this.checkParams.class);

    this.uuid = this.activatedRoute.snapshot.paramMap.get('uuid');

    this.restoreService.checkRequestValid(this.uuid, () => {
      // El uuid es válido
      // TODO: Se puede restaurar la clave (se muestra el form)
    }, {
      errorFunction: error => {
        this.expired = true;
        this.message = error.tag;
        // this.utilService.showNotification(this.translate.instant(error.tag), 'danger');
      }
    });
  }

  ngOnInit(): void {

  }

  /**
   * function to change language
   *
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }

  /**
   * redirects to forgot key
   */
  forgotKey() {
    this.router.navigate([this.checkParams.service_key + '/forgotKey']);
  }

  /**
   * Receives the data of the main form, allows to update the password
   * @param keyForm
   */
  newKey(keyForm: NgForm) {
    if (keyForm.valid) {
      this.restoreService.updatePassword(keyForm.value.pass, this.uuid, () => {
        this.showGoLogin = true;
        this.utilService.showNotification(this.translate.instant('message.confirmation.password.change'), 'success');
        setTimeout(() => {
          this.router.navigate([this.checkParams.service_key + '/login']);
        }, 10000);

      }, {
        ignoreSuccessMessage: true,
        errorFunction: error => {
          this.expired = true;
          this.message = error.tag;
          // this.utilService.showNotification(this.translate.instant(error.tag), 'danger');

        }
      });
    }
  }
  /**
   * check admin or client, and then redirrects to login
   */
  goLogin() {
    this.router.navigate([this.checkParams.service_key + '/login']);
  }
}
