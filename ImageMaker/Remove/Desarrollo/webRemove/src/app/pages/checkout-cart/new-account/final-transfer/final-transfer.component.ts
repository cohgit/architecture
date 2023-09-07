import {Component, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SessionService} from 'src/app/helpers/session.service';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';

@Component({
  selector: 'app-final-transfer',
  templateUrl: './final-transfer.component.html',
  styleUrls: ['./final-transfer.component.css'],
})
export class FinalTransferComponent implements OnInit {
  language: string;
  checkParams: any = {};
  transfer: any;

  /**
   * it shows after a transfers payments
   * @param translateHelper
   * @param renderer
   * @param sessionService
   * @param activatedRoute
   * @param router
   * @param route
   */
  constructor(
    public translateHelper: TranslateHelperService,
    private renderer: Renderer2,
    private sessionService: SessionService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.language = translateHelper.getTransLanguage();

    this.activatedRoute.data.subscribe((data) => {
      this.checkParams = data;
    });

    this.renderer.addClass(document.body, this.checkParams.class);
  }

  ngOnInit(): void {
    this.transfer = {
      owner: 'Remove Group',
      bank: 'IBAN',
      type: 'Tipo de Cuenta',
      account: 'ES22 3183 2800 1310 0653 8423',
      email: 'correo de pago de remove',
    };
    this.goLogin();
  }

  /**
   * go to login
   */
  goLogin(): void {
    setTimeout(() => {
      this.router.navigate(['/client/login']);
    }, 10000);
  }

  /**
   * change the language of this page
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }
}
