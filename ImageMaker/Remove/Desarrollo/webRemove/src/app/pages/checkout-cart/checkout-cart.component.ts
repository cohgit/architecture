import {Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import { environment } from '../../../environments/environment';
import {XternalCommonService} from "../../services/xternal-common.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {TranslateHelperService} from "../../helpers/translate-helper.service";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {UtilService} from "../../helpers/util.service";
import {SuscriptionService} from 'src/app/services/suscription.service';
import {MatHorizontalStepper} from '@angular/material/stepper';
import {NgxUiLoaderService} from 'ngx-ui-loader';

@Component({
  selector: 'app-checkout-cart',
  templateUrl: './checkout-cart.component.html',
  styleUrls: ['./checkout-cart.component.css'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {displayDefaultIndicatorType: false}
  }]
})
export class CheckoutCartComponent implements OnInit {
  @ViewChild('stepperCheckout') private stepperCheckout: MatHorizontalStepper;

  isLineal: true;
  language: string;
  checkParams: any = {};
  allPlans: any;
  suggestion: any;
  payResult: any;
  previousUrl: string = undefined;
  configParams: any = {};
  error = false;
  message = '';
  uuidSuggestion: string;
  showHeaderFooter: boolean = !environment.production;

  /**
   * Organize the different components that make up the checkout stepper
   * @param renderer
   * @param router
   * @param suscribeService
   * @param externalService
   * @param translateHelper
   * @param activatedRoute
   * @param utilService
   * @param ngxLoader
   */
  constructor(private renderer: Renderer2, private router: Router, private suscribeService: SuscriptionService,
              private externalService: XternalCommonService, public translateHelper: TranslateHelperService,
              private activatedRoute: ActivatedRoute, public utilService: UtilService, private ngxLoader: NgxUiLoaderService) {
    this.language = translateHelper.getTransLanguage();
    this.checkRoutes();
    // recibe parámetros de la rutas
    this.activatedRoute.data.subscribe(data => {
      this.checkParams = data;
    });
    this.uuidSuggestion = this.activatedRoute.snapshot.paramMap.get('uuid');
    this.renderer.addClass(document.body, this.checkParams.class);
  }

  ngOnInit(): void {
    this.loadCommons();
  }

  /**
   * load commons elements, combo, filters and check if this pages is access external (suggest) or internal
   */
  loadCommons(): void {
    this.externalService.listPlans(resp => {
      this.configParams = resp.params;
      this.allPlans = resp.plans;

      if (this.uuidSuggestion != null) {                  // Has path param
        if (isNaN(Number(this.uuidSuggestion))) {         // Is an uuid from a recomendation
          this.suscribeService.verifySuggestion(this.uuidSuggestion, response => {
            this.suggestion = response;
            this.nextStep();
          }, {
            errorFunction: () => {
              console.warn('Error: Suggested plan not found.');
            }
          });
        } else {
          let planFound = null;
          this.allPlans.forEach(plan => {
            if (plan.id == Number(this.uuidSuggestion)) {
              planFound = plan;
            }
          });

          if (planFound != null) {
            this.suggestion = {plan: planFound};
            this.nextStep();
          } else {
            this.externalService.getPlan(Number(this.uuidSuggestion), response => {
              if (response.plans.length > 0 && response.plans[0] != null) {
                this.suggestion = {plan: response.plans[0]};
                this.nextStep();
              } else {
                console.warn('Error: Suggested plan not found.');
              }
            });
          }
        }
      }
    });
  }

  /**
   * it allows to change language
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }

  /**
   * check what plan is suggested or selected
   * @param $event
   */
  checkPlan($event: any): void {
    if (this.suggestion) {
      this.suggestion.plan = $event;
    } else {
      this.suggestion = {plan: $event};
    }

    this.nextStep();
  }

  /**
   * change to next step
   */
  nextStep() {
    this.ngxLoader.startLoader('external-loader');
    setTimeout(() => {
      this.stepperCheckout.next();
      this.ngxLoader.stopLoader('external-loader');
    }, 1000);
  }

  /**
   * Check the payment result from new account component
   * @param $event
   */
  checkPayment($event: any): void {
    this.payResult = $event;
  }

  /**
   * Check the actual URL
   */
  checkRoutes(): void {
    const currentUrl = this.router.url;

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.previousUrl = currentUrl;
      }
    });
  }
}
