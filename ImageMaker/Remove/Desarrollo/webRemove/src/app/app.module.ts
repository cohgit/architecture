import {LOCALE_ID, NgModule} from '@angular/core';
import es from '@angular/common/locales/es';

import { registerLocaleData } from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {DatePipe, HashLocationStrategy, LocationStrategy} from '@angular/common';
import {MatTableModule} from '@angular/material/table';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {routes} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './shared/navbar/navbar.component';
import {UsersComponent} from './pages/users/users.component';
import {LoginComponent} from './login/login.component';
import {OnlynumberDirective} from './directives/Onlynumber';
import {MaterialFileInputModule} from 'ngx-material-file-input';
import {MatIconModule} from '@angular/material/icon';
import {NgxUiLoaderConfig, NgxUiLoaderHttpModule, NgxUiLoaderModule} from 'ngx-ui-loader';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {AutocompleteLibModule} from 'angular-ng-autocomplete';
import {FileInputComponent} from './shared/file-input/file-input.component';
import {NewEditUserComponent} from './pages/users/new-edit-user/new-edit-user.component';
import {ModalComponent} from './shared/modal/modal.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';

import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {ForgotKeyComponent} from './forgot-key/forgot-key.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatBadgeModule} from '@angular/material/badge';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatCardModule} from '@angular/material/card';
import {MatTabsModule} from '@angular/material/tabs';
import {MatChipsModule} from '@angular/material/chips';

import {RegisterComponent} from './register/register.component';
import {HomeComponent} from './pages/home/home.component';
import {ErrorPageComponent} from './pages/error-page/error-page.component';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import '@angular/common/locales/global/es-AR';
import {FiltersComponent} from './shared/filters/filters.component';
import {NgxFlagIconCssModule} from 'ngx-flag-icon-css';
import {FirstTimeComponent} from './pages/home/first-time/first-time.component';
import {UrlService} from './helpers/url.service';

import {ScannerComponent} from './pages/scanner/scanner.component';
import {ConfigScannerComponent} from './pages/scanner/config-scanner/config-scanner.component';
import {ResultScannerComponent} from './pages/scanner/result-scanner/result-scanner.component';
import {DashboardScannerComponent} from './pages/scanner/dashboard-scanner/dashboard-scanner.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AddItemComponent} from './pages/scanner/config-scanner/add-item/add-item.component';
import {FeelingColorsPipe} from './pipes/feeling-colors.pipe';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {JwPaginationModule} from 'jw-angular-pagination';
import {MatSelectFilterModule} from 'mat-select-filter';
import {TableFilterPipe} from './pipes/table-filter.pipe';
import {PlansComponent} from './pages/plans/plans.component';
import {NewEditPlanComponent} from './pages/plans/new-edit-plan/new-edit-plan.component';
import {ClientsComponent} from './pages/clients/clients.component';
import {UserConfigComponent} from './shared/navbar/user-config/user-config.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {NewEditClientComponent} from './pages/clients/new-edit-client/new-edit-client.component';
import {NewKeyComponent} from './new-key/new-key.component';
import {HistoryComponent} from './pages/clients/history/history.component';
import {ChangePlanComponent} from './pages/clients/change-plan/change-plan.component';
import {TrackingwordsComponent} from './pages/trackingwords/trackingwords.component';
import {
  NewEditTrackingwordsComponent
} from './pages/trackingwords/new-edit-trackingwords/new-edit-trackingwords.component';
import {ImpulseComponent} from './pages/impulse/impulse.component';
import {NewEditImpulseComponent} from './pages/impulse/new-edit-impulse/new-edit-impulse.component';
import {AngularEditorModule} from '@kolkov/angular-editor';
import {SidebarComponent} from './shared/sidebar/sidebar.component';
import {MatStepperModule} from '@angular/material/stepper';
import {SelectDateComponent} from './pages/impulse/select-date/select-date.component';
import {FinalURLComponent} from './pages/impulse/final-url/final-url.component';
import {ManageNewsComponent} from './pages/impulse/manage-news/manage-news.component';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatRadioModule} from '@angular/material/radio';
import {
  TableTrackingProgressComponent
} from './pages/scanner/dashboard-scanner/gadgets/table-tracking-progress/table-tracking-progress.component';
import {TrackingPipe} from './pipes/tracking.pipe';
import {NotificationsComponent} from './notifications/notifications.component';
import {CheckoutCartComponent} from './pages/checkout-cart/checkout-cart.component';
import {ChoosePlanComponent} from './pages/checkout-cart/choose-plan/choose-plan.component';
import {NewAccountComponent} from './pages/checkout-cart/new-account/new-account.component';
import {TermsComponent} from './pages/checkout-cart/new-account/terms/terms.component';
import {SuccessComponent} from './pages/checkout-cart/success/success.component';
import {FailComponent} from './pages/checkout-cart/fail/fail.component';
import {VerifiedEmailComponent} from './verified-email/verified-email.component';
import {environment} from '../environments/environment';
import {StripeModule} from 'stripe-angular';
import {ScannerReportComponent} from './reports/scanner-report/scanner-report.component';
import {FooterReportComponent} from './reports/footer-report/footer-report.component';
import {HeaderReportComponent} from './reports/header-report/header-report.component';
import {BaseReportComponent} from './reports/base-report/base-report.component';
import {ConfigReportComponent} from './reports/scanner-report/config-report/config-report.component';
import {ResultReportComponent} from './reports/scanner-report/result-report/result-report.component';
import {DashReportComponent} from './reports/scanner-report/dash-report/dash-report.component';
import {HelpPageComponent} from './pages/help-page/help-page.component';
import {ClientBillComponent} from './pages/clients/client-bill/client-bill.component';
import {ConfirmChangeComponent} from './shared/sidebar/confirm-change/confirm-change.component';
import {DashAdminComponent} from './pages/dash-admin/dash-admin.component';
import {CardBrandsPipe} from './pipes/card-brands.pipe';
import {NewEditDeindexingComponent} from './pages/deindexing/new-edit-deindexing/new-edit-deindexing.component';
import {DeindexingComponent} from './pages/deindexing/deindexing.component';
import {ViewDeindexingComponent} from './pages/deindexing/view-deindexing/view-deindexing.component';
import {MatListModule} from '@angular/material/list';
import {DynamicFormComponent} from './shared/dynamic-form/dynamic-form.component';
import {RedirectPageComponent} from './shared/redirect-page/redirect-page.component';
import {DynamicDisplayComponent} from './shared/dynamic-display/dynamic-display.component';
import {TrackingDeindexingComponent} from './pages/deindexing/tracking-deindexing/tracking-deindexing.component';
import {GlobalViewComponent} from './pages/scanner/global-view/global-view.component';
import {InfoClientsComponent} from './pages/clients/info-clients/info-clients.component';
import {CustomPlanComponent} from './shared/custom-plan/custom-plan.component';
import {ActiveLicencesComponent} from './shared/sidebar/active-licences/active-licences.component';
import {CustomCheckoutComponent} from './pages/checkout-cart/custom-checkout/custom-checkout.component';
import {FinalTransferComponent} from './pages/checkout-cart/new-account/final-transfer/final-transfer.component';
import {SuccessDemoComponent} from './pages/checkout-cart/new-account/success-demo/success-demo.component';
import {PaymentsBillsComponent} from './pages/payments-bills/payments-bills.component';
import {PaymentsMethodsPipe} from './pipes/payments-methods.pipe';
import {SiteComponent} from './pages/site/site.component';
import {ContentModalComponent} from './pages/site/content-modal/content-modal.component';
import {PoliticsModalComponent} from './pages/site/politics-modal/politics-modal.component';
import {FaqComponent} from './pages/site/faq/faq.component';
import {CookiesComponent} from './pages/site/cookies/cookies.component';
import {ContactComponent} from './pages/site/contact/contact.component';
import {DemoComponent} from './pages/site/demo/demo.component';
import {CallYouComponent} from './pages/site/call-you/call-you.component';
import {NgcCookieConsentConfig, NgcCookieConsentModule} from 'ngx-cookieconsent';
import {FileUploadComponent} from './shared/file-upload/file-upload.component';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from './pages/site/header/header.component';
import {FooterComponent} from './pages/site/footer/footer.component';
import {CustomizedPipe} from './pipes/customized.pipe';
import {UnsubscribeComponent} from './pages/site/unsubscribe/unsubscribe.component';
import {ForbiddenWordsComponent} from './pages/forbidden-words/forbidden-words.component';
import {
  NewEditForbiddenWordsComponent
} from './pages/forbidden-words/new-edit-forbidden-words/new-edit-forbidden-words.component';
import {HistoryAuditUserComponent} from './pages/users/history-audit-user/history-audit-user.component';
import {MatSliderModule} from "@angular/material/slider";
import {ApproveImpulseComponent} from './pages/impulse/approve-impulse/approve-impulse.component';
import {RECAPTCHA_V3_SITE_KEY, RecaptchaModule, RecaptchaV3Module} from "ng-recaptcha";
import {KeywordTableComponent} from './pages/scanner/dashboard-scanner/gadgets/keyword-table/keyword-table.component';
import {
  TransformSectionComponent
} from './pages/scanner/dashboard-scanner/gadgets/transform-section/transform-section.component';
import {VisibilityPipe} from './pipes/visibility.pipe';
import {
  AlertSectionsComponent
} from './pages/scanner/dashboard-scanner/gadgets/alert-sections/alert-sections.component';
import { HighlightPipe } from './pipes/highlight.pipe';
import { AdvaceReportComponent } from './reports/advace-report/advace-report.component';
import { ConfirmScannerComponent } from './pages/scanner/config-scanner/confirm-scanner/confirm-scanner.component';
import { CommentScannerComponent } from './pages/scanner/comment-scanner/comment-scanner.component';
import { CommentHistoryComponent } from './pages/scanner/comment-history/comment-history.component';
import { AlertScannersComponent } from './pages/scanner/alert-scanners/alert-scanners.component';


registerLocaleData(es, 'es');
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

// configuración de waiting
const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  bgsColor: '#1b4962',
  bgsOpacity: 0.1,
  bgsPosition: 'bottom-right',
  bgsSize: 60,
  bgsType: 'rotating-plane',
  blur: 5,
  delay: 0,
  fastFadeOut: true,
  fgsColor: '#1b4962',
  fgsPosition: 'center-center',
  fgsSize: 70,
  fgsType: 'rotating-plane',
  gap: 24,
  // logoUrl: '../../assets/img/isotipo2.png',
  logoPosition: 'center-center',
  logoSize: 120,
  masterLoaderId: 'master',
  overlayBorderRadius: '0',
  overlayColor: 'rgba(40,40,40,0.1)',
  pbColor: '#eb5353',
  pbDirection: 'ltr',
  pbThickness: 3,
  hasProgressBar: true,
  textColor: '#565656',
  textPosition: 'center-center',
  maxTime: -1,
  minTime: 300
};

const cookieConfig: NgcCookieConsentConfig = {
  cookie: {
    domain: window.location.hostname,
  },
  palette: {
    popup: {
      background: 'rgba(255, 255, 255, 0.87)',
      text: '#000000'
    },
    button: {
      background: '#0a4e7d',
    },
  },
  position: 'top-left',
  theme: 'classic',
  type: 'opt-out',
  content: {
    message:
      '<strong>Su privacidad es importante para nosotros</strong> <br> <br>' +
      'En Remove almacenamos o accedemos a información en un dispositivo, tales como cookies y procesamos datos' +
      ' personales tales como identificadores únicos e información estándar enviada por un dispositivo, para' +
      ' anuncios y contenido personalizado, medición de anuncios y del contenido e información sobre el público, así' +
      ' como para desarrollar y mejorar productos <br></br>' +
      'Con su permiso, nosotros y nuestros socios podemos utilizar datos de localización geográfica precisa e identificación mediante las características de dispositivos' +
      'Puede hacer clic para otorgarnos so consentimiento a nosotros y a nuestros socios para que llevemos el' +
      ' procedimiento previamente descrito. De forma alternativa, puede acceder a la información más detallada y cambiar sus preferencias antes de otorgar o negar su consentimiento. ' +
      ' <br></br>' +
      'Tenga en cuenta que algún procesamiento de sus datos personales puede no requerir de su consentimiento, pero' +
      ' usted tiene el derecho de rechazar tal procesamiento. Sus preferencias se aplicarán solo a este sitio web. Puede cambiar sus preferencias en cualquier momento entrando de nuevo al sitio web. '
    ,
    dismiss: 'Ok',
    deny: 'Rechazar ',
    link: 'Aprender más',
    href: '/#/client/site/cookies',
    policy: 'Políticas de Cookies',
    allow: 'Permitir ',
  },
};

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    UsersComponent,
    LoginComponent,
    OnlynumberDirective,
    FileInputComponent,
    NewEditUserComponent,
    ModalComponent,
    ForgotKeyComponent,
    ScannerComponent,
    RegisterComponent,
    HomeComponent,
    ErrorPageComponent,
    ConfigScannerComponent,
    FiltersComponent,
    FirstTimeComponent,
    ResultScannerComponent,
    DashboardScannerComponent,
    AddItemComponent,
    FeelingColorsPipe,
    TableFilterPipe,
    PlansComponent,
    NewEditPlanComponent,
    ClientsComponent,
    UserConfigComponent,
    NewEditClientComponent,
    NewKeyComponent,
    HistoryComponent,
    ChangePlanComponent,
    TrackingwordsComponent,
    NewEditTrackingwordsComponent,
    ImpulseComponent,
    NewEditImpulseComponent,
    SidebarComponent,
    TableTrackingProgressComponent,
    SelectDateComponent,
    FinalURLComponent,
    ManageNewsComponent,
    TrackingPipe,
    NotificationsComponent,
    ChoosePlanComponent,
    CheckoutCartComponent,
    NewAccountComponent,
    TermsComponent,
    SuccessComponent,
    FailComponent,
    VerifiedEmailComponent,
    ScannerReportComponent,
    FooterReportComponent,
    HeaderReportComponent,
    BaseReportComponent,
    ConfigReportComponent,
    ResultReportComponent,
    DashReportComponent,
    HelpPageComponent,
    ClientBillComponent,
    ConfirmChangeComponent,
    DashAdminComponent,
    CardBrandsPipe,
    NewEditDeindexingComponent,
    DeindexingComponent,
    ViewDeindexingComponent,
    DynamicFormComponent,
    RedirectPageComponent,
    DynamicDisplayComponent,
    TrackingDeindexingComponent,
    GlobalViewComponent,
    InfoClientsComponent,
    CustomPlanComponent,
    ActiveLicencesComponent,
    CustomCheckoutComponent,
    FinalTransferComponent,
    SuccessDemoComponent,
    PaymentsBillsComponent,
    PaymentsMethodsPipe,
    SiteComponent,
    ContentModalComponent,
    PoliticsModalComponent,
    FaqComponent,
    CookiesComponent,
    ContactComponent,
    DemoComponent,
    CallYouComponent,
    FileUploadComponent,
    HeaderComponent,
    FooterComponent,
    CustomizedPipe,
    UnsubscribeComponent,
    ForbiddenWordsComponent,
    NewEditForbiddenWordsComponent,
    HistoryAuditUserComponent,
    ApproveImpulseComponent,
    KeywordTableComponent,
    TransformSectionComponent,
    VisibilityPipe,
    AlertSectionsComponent,
    HighlightPipe,
    AdvaceReportComponent,
    ConfirmScannerComponent,
    CommentScannerComponent,
    CommentHistoryComponent,
    AlertScannersComponent,
  ],
  imports: [
    BrowserModule,
    //   AppRoutingModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    HttpClientModule,
    MatTableModule,
    MatSlideToggleModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSelectModule,
    MaterialFileInputModule,
    MatIconModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
    NgxUiLoaderHttpModule.forRoot({
      loaderId: 'external-loader',
      showForeground: true,
      excludeRegexp: ['/api/rest/common/notifications*', '/api/rest/common/clients/email']
    }),
    MatAutocompleteModule,
    AutocompleteLibModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatSortModule,
    MatMenuModule,
    MatButtonModule,
    MatBadgeModule,
    MatSidenavModule,
    MatCheckboxModule,
    MatCardModule,
    MatTabsModule,
    MatChipsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    NgxFlagIconCssModule,
    NgbModule,
    NgxChartsModule,
    JwPaginationModule,
    MatSelectFilterModule,
    MatProgressBarModule,
    AngularEditorModule,
    MatStepperModule,
    MatButtonToggleModule,
    MatRadioModule,
    StripeModule.forRoot(environment.public_key),
    MatListModule,
    NgcCookieConsentModule.forRoot(cookieConfig),
    MatSliderModule,
    RecaptchaV3Module,
    RecaptchaModule
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    //  { provide: APP_BASE_HREF, useValue: '/' },
    {provide: LOCALE_ID, useValue: 'es-AR'},
    DatePipe,
    UrlService,
    {
      provide: RECAPTCHA_V3_SITE_KEY,
      useValue: environment.captcha.sitekey,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
