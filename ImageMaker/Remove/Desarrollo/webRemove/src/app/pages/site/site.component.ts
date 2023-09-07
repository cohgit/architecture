import {ChangeDetectorRef, Component, OnDestroy, OnInit, Renderer2,} from '@angular/core';
import {NgForm} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';
import {UtilService} from 'src/app/helpers/util.service';
import {WordpressApiService} from './../../services/wordpress-api.service';
import {XternalCommonService} from './../../services/xternal-common.service';
import {Subscription} from 'rxjs';
import {MediaMatcher} from '@angular/cdk/layout';
import {
  NgcCookieConsentService,
  NgcInitializeEvent,
  NgcNoCookieLawEvent,
  NgcStatusChangeEvent,
} from 'ngx-cookieconsent';
import {CallYouComponent} from './call-you/call-you.component';
import {MatHorizontalStepper} from '@angular/material/stepper';

@Component({
  selector: 'app-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.css'],
})
export class SiteComponent implements OnInit, OnDestroy {
  language: string;
  checkParams: any = {};
  contact: any = {};
  contactPlan: any = {};
  company: any = {};
  contactCompany: any = {};
  tabs = {
    first: false,
    second: true,
    third: false,
  };
  news: any = [];
  allPlans: any = [];
  isLineal = false;
  companyEmail = '';
  showThird = false;
  // keep refs to subscriptions to be able to unsubscribe later
  private popupOpenSubscription: Subscription;
  private popupCloseSubscription: Subscription;
  private initializeSubscription: Subscription;
  private statusChangeSubscription: Subscription;
  private revokeChoiceSubscription: Subscription;
  private noCookieLawSubscription: Subscription;

  /**
   * manage landig page information
   * @param translateHelper
   * @param renderer
   * @param activatedRoute
   * @param router
   * @param dialog
   * @param utilService
   * @param worpressService
   * @param externalServices
   * @param changeDetectorRef
   * @param media
   * @param ccService
   */
  constructor(
    public translateHelper: TranslateHelperService,
    private renderer: Renderer2,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    public utilService: UtilService,
    private worpressService: WordpressApiService,
    private externalServices: XternalCommonService,
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private ccService: NgcCookieConsentService
  ) {
    this.language = translateHelper.getTransLanguage();
    this.activatedRoute.data.subscribe((data) => {
      this.checkParams = data;
    });

    this.renderer.addClass(document.body, this.checkParams.class);
  }

  ngOnInit(): void {
    this.getNews();
    this.getPlans();
    // subscribe to cookieconsent observables to react to main events
    this.popupOpenSubscription = this.ccService.popupOpen$.subscribe(() => {
    });

    this.popupCloseSubscription = this.ccService.popupClose$.subscribe(() => {
    });

    this.initializeSubscription = this.ccService.initialize$.subscribe(
      (event: NgcInitializeEvent) => {
      }
    );

    this.statusChangeSubscription = this.ccService.statusChange$.subscribe(
      (event: NgcStatusChangeEvent) => {
      }
    );

    this.revokeChoiceSubscription = this.ccService.revokeChoice$.subscribe(
      () => {
      }
    );

    this.noCookieLawSubscription = this.ccService.noCookieLaw$.subscribe(
      (event: NgcNoCookieLawEvent) => {
      }
    );
  }

  /**
   * unsubscribe to cookieconsent observables to prevent memory leaks
   */
  ngOnDestroy() {
    this.popupOpenSubscription.unsubscribe();
    this.popupCloseSubscription.unsubscribe();
    this.initializeSubscription.unsubscribe();
    this.statusChangeSubscription.unsubscribe();
    this.revokeChoiceSubscription.unsubscribe();
    this.noCookieLawSubscription.unsubscribe();
  }


  /**
   *  function to open modal
   * @param data
   * @param componente
   */
  openModal(data: any, componente: any): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        info: data,
      },
    });
  }

  /**
   *
   * @param form Envía el formulario de contacto
   */
  sendContact(form: NgForm, tittle: string): void {
    let sendForm: any;
    if (form.valid) {
      sendForm = {
        'title': tittle,
        'atts': [
          {'key': 'Nombre del Contacto', 'value': form.value.name},
          {'key': 'Email', 'value': form.value.email},
          {'key': 'Teléfono', 'value': form.value.phone},
          {'key': 'Mensaje', 'value': form.value.message}
        ]
      };
      if (tittle === 'Formulario Inicial') {
        this.contact = {};
      } else {
        this.contactPlan = {};
      }
      this.externalServices.contact(sendForm);
    }
  }

  /**
   *
   * @param form Envía el formulario de Empresa Email
   */
  sendContactCompanyEmail(form: NgForm, tittle: string, step: MatHorizontalStepper): void {
    let sendForm: any;
    if (form.valid) {
      step.next();
      sendForm = {
        'title': tittle,
        'atts': [
          {'key': 'Email', 'value': form.value.email},
        ]
      };
      this.externalServices.contact(sendForm, null, {
        ignoreSuccessMessage: true
      });
      this.companyEmail = form.value.email
    }
  }

  /**
   *
   * @param form Envía el formulario de Empresa
   */
  sendContactCompanyFull(form: NgForm, tittle: string, step: MatHorizontalStepper): void {
    let sendForm: any;
    if (form.valid) {
      step.next();
      sendForm = {
        'title': tittle,
        'atts': [
          {'key': 'Nombre', 'value': form.value.name},
          {'key': 'Apellido', 'value': form.value.lastname},
          {'key': 'Compañia', 'value': form.value.company},
          {'key': 'Infracciones', 'value': form.value.message},
          {'key': 'Email', 'value': this.companyEmail},
        ]
      };
      this.externalServices.contact(sendForm);
      this.company = {}
      this.showThird = true;
    }
  }

  /**
   * abre un modal para solicitar los datos de una persona para llamarlos
   */
  openCallYou()
    :
    void {
    let empty
      :
      '';
    this.openModal(empty, CallYouComponent);
  }

  /**
   *Cambia la daa entre la pestaña de servicios
   * @param data
   */
  changeTab(data
              :
              string
  ) {
    if (data === 'first') {
      this.tabs = {
        first: true,
        second: false,
        third: false,
      };
    }
    if (data === 'second') {
      this.tabs = {
        first: false,
        second: true,
        third: false,
      };
    }
    if (data === 'third') {
      this.tabs = {
        first: false,
        second: false,
        third: true,
      };
    }
  }

  /**
   * nvoca a la API de wordpress para traer los servicios
   */
  getNews() {
    this.worpressService.getNews((result) => {
      this.news = result.slice(0, 4);
    });
  }

  /**
   * trae todos los planes del servicio
   */
  getPlans(): void {
    this.externalServices.listPlans(
      (response) => {
        this.allPlans = response.plans.slice(0, 3)
      });
  }


}
