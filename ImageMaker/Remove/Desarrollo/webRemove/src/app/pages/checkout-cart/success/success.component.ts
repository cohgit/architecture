import {Component, OnInit, Renderer2} from '@angular/core';
import {TranslateHelperService} from '../../../helpers/translate-helper.service';
import {ActivatedRoute, Router} from '@angular/router';
import { SessionService } from 'src/app/helpers/session.service';
import { SuscriptionService } from 'src/app/services/suscription.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {
  language: string;
  checkParams: any = {};

  /**
   * after a success transition from a stripe payment
   * @param translateHelper
   * @param renderer
   * @param sessionService
   * @param activatedRoute
   * @param router
   * @param route
   * @param suscriptionService
   */
  constructor(public translateHelper: TranslateHelperService, private renderer: Renderer2, private sessionService: SessionService,
              private activatedRoute: ActivatedRoute, private router: Router, private route: ActivatedRoute,
              private suscriptionService: SuscriptionService) {
    this.language = translateHelper.getTransLanguage();

    this.activatedRoute.data.subscribe(data => {
      this.checkParams = data;
    });

    this.renderer.addClass(document.body,  this.checkParams.class);
  }

  ngOnInit(): void {
    const suscription = this.sessionService.rescueTempSuscription();
    suscription.paymentTrackingCode = this.route.snapshot.paramMap.get('session_id');
    this.suscriptionService.suscribeAsStripe(suscription, () => {
      this.goLogin();
    });
  }

  /**
   * go to login
   */
  goLogin(): void{
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
