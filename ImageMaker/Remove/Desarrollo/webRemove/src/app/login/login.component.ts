import {Component, OnInit, Renderer2} from '@angular/core';
import {SessionLogService} from 'src/app/services/session-log.service';
import {ActivatedRoute} from '@angular/router';
import {UtilService} from "../helpers/util.service";
import {TranslateHelperService} from "../helpers/translate-helper.service";
import {SessionService} from '../helpers/session.service';
import {ReCaptchaV3Service} from "ng-recaptcha";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: any = {};
  language: string;
  checkParams: any = {};
  hide = true;
  tokenGenerated = '';
  public recaptchaMode = 'v3';

  /**
   * Manage customer or admin login
   * @param loginService
   * @param recaptchaV3Service
   * @param renderer
   * @param session
   * @param utilService
   * @param translateHelper
   * @param activatedRoute
   */
  constructor(private loginService: SessionLogService, private recaptchaV3Service: ReCaptchaV3Service,
              private renderer: Renderer2, private session: SessionService,
              private utilService: UtilService, public translateHelper: TranslateHelperService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.language = this.translateHelper.getTransLanguage();

    this.activatedRoute.data.subscribe(data => {
      this.checkParams = data;
      this.renderer.addClass(document.body, this.checkParams.class);

      if (this.checkParams.service_key === 'client' && this.session.getClientUser()) {
        this.session.goClientInitPage();
      } else if (this.checkParams.service_key === 'admin' && this.session.getAdminUser()) {
        this.session.goAdminInitPage();
      }
    });
  }

  /**
   * Receives the data of the main form, allows the entry to the system for admin and for client
   * @param loginForm
   */
  login(loginForm: any) {
    //this.executeRecaptchaV3();
    this.recaptchaV3Service.execute('myAction').subscribe(
      (token) => {
        this.tokenGenerated = token;
        if (this.loginForm.mail && this.loginForm.password) {
          if (this.utilService.validateEmail(loginForm.mail)) {
            if (this.checkParams.ADMIN) {
              this.loginService.loginForAdministrator(this.loginForm.mail, this.loginForm.password, null, {
                errorFunction: () => {
                  this.loginForm = {};
                }
              });
            } else if (this.checkParams.CLIENT) {
              this.loginService.loginForClient(this.loginForm.mail, this.loginForm.password, response => {
                if (response.message === 'warning.client.must.verify.email') {
                  this.utilService.showNotification('error.verify_email', 'danger');
                  // show send verify email again
                }
              }, {
                errorFunction: () => {
                  this.loginForm = {};
                }
              });
            }
          } else {
            this.utilService.showNotification('error.invalid.email', 'danger');
          }
        } else {
          this.utilService.showNotification('error.incomplete.form', 'danger');
        }
      },
      (error) => {
        console.error(`Recaptcha v3 error:`, error);
      }
    );

  }
  /**
   * function to change language
   *
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }


}
