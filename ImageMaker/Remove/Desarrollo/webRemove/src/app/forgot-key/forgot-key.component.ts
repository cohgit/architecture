import {Component, OnInit, Renderer2} from '@angular/core';
import {UtilService} from "../helpers/util.service";
import {ResetPassService} from "../services/reset-pass.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateHelperService} from "../helpers/translate-helper.service";
import {TranslateService} from "@ngx-translate/core";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-forgot-key',
  templateUrl: './forgot-key.component.html',
  styleUrls: ['./forgot-key.component.css']
})
export class ForgotKeyComponent implements OnInit {
  email: any;
  language: string;
  checkParams: any = {};
  isClient = true;
  showGoLogin = false;

  /**
   * it controlls the forget password component
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
   *
   * @param forgotForm
   * receives the data from the main form, allows the change of password and redirects to the login
   */
  restoreKey(forgotForm: NgForm) {
    if (forgotForm.valid) {
      this.restoreService.requestNewPassword(forgotForm.value.email, () => {
        this.utilService.showNotification('message.forgot_key', 'success');
        this.showGoLogin = true;
        setTimeout(() => {
          this.router.navigate([this.checkParams.service_key + '/login']);
        }, 10000);
      }, {ignoreSuccessMessage: true});
    }
  }

  /**
   * check admin or client, and then redirrects to login
   */
  goLogin() {
    this.router.navigate([this.checkParams.service_key + '/login']);
  }
}
