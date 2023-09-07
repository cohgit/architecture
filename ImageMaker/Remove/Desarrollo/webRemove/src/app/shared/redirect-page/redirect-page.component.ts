import {Component, OnInit, Renderer2} from '@angular/core';
import {UtilService} from '../../helpers/util.service';
import {TranslateHelperService} from '../../helpers/translate-helper.service';
import {AccountConfigService} from '../../services/account-config.service';
import {SessionService} from '../../helpers/session.service';
import {MatDialog} from '@angular/material/dialog';
import {PaymentsBillsComponent} from 'src/app/pages/payments-bills/payments-bills.component';

@Component({
  selector: 'app-redirect-page',
  templateUrl: './redirect-page.component.html',
  styleUrls: ['./redirect-page.component.css'],
})
export class RedirectPageComponent implements OnInit {
  reason = '';

  /**
   * 404 error page used in all the system
   * @param renderer
   * @param utilService
   * @param translateHelper
   * @param configService
   * @param session
   * @param dialog
   */
  constructor(
    private renderer: Renderer2,
    private utilService: UtilService,
    public translateHelper: TranslateHelperService,
    private configService: AccountConfigService,
    public session: SessionService,
    private dialog: MatDialog
  ) {
    this.renderer.addClass(document.body, 'login-body');
  }

  ngOnInit(): void {

    this.reason = this.session.getUser().message;

  }

  /**
   * go home in the application
   */
  back(): void {
    this.utilService.goHome();
  }

  /**
   * Open Stripe customer portal
   */
  openBills() {
    this.configService.getInfo((response) => {
      this.dialog.open(PaymentsBillsComponent, {
        width: '60%',
        data: response,
      });
    });
  }

  reSendMail() {
    this.configService.requestVerificationEmail();
  }
}
