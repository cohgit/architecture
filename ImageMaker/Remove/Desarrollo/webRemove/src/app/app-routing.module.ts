import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ForgotKeyComponent } from './forgot-key/forgot-key.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { ErrorPageComponent } from './pages/error-page/error-page.component';
import { ScannerComponent } from './pages/scanner/scanner.component';
import { UsersComponent } from './pages/users/users.component';
import { PlansComponent } from './pages/plans/plans.component';
import { ClientsComponent } from './pages/clients/clients.component';
import { UserConfigComponent } from './shared/navbar/user-config/user-config.component';
import { NewKeyComponent } from './new-key/new-key.component';
import { TrackingwordsComponent } from './pages/trackingwords/trackingwords.component';
import { ImpulseComponent } from './pages/impulse/impulse.component';
import { CheckoutCartComponent } from './pages/checkout-cart/checkout-cart.component';
import { SuccessComponent } from './pages/checkout-cart/success/success.component';
import { FailComponent } from './pages/checkout-cart/fail/fail.component';
import { VerifiedEmailComponent } from './verified-email/verified-email.component';
import { HelpPageComponent } from './pages/help-page/help-page.component';
import { DashAdminComponent } from './pages/dash-admin/dash-admin.component';
import { DeindexingComponent } from './pages/deindexing/deindexing.component';
import { NewEditDeindexingComponent } from './pages/deindexing/new-edit-deindexing/new-edit-deindexing.component';
import { RedirectPageComponent } from './shared/redirect-page/redirect-page.component';
import { PaymentGuard } from './helpers/payment.guard';
import { ViewDeindexingComponent } from './pages/deindexing/view-deindexing/view-deindexing.component';
import { GlobalViewComponent } from './pages/scanner/global-view/global-view.component';
import { FinalTransferComponent } from './pages/checkout-cart/new-account/final-transfer/final-transfer.component';
import { SuccessDemoComponent } from './pages/checkout-cart/new-account/success-demo/success-demo.component';
import { ForbiddenWordsComponent } from './pages/forbidden-words/forbidden-words.component';
import { ContactComponent } from './pages/site/contact/contact.component';
import { CookiesComponent } from './pages/site/cookies/cookies.component';
import { DemoComponent } from './pages/site/demo/demo.component';
import { FaqComponent } from './pages/site/faq/faq.component';
import { SiteComponent } from './pages/site/site.component';
import { UnsubscribeComponent } from './pages/site/unsubscribe/unsubscribe.component';

import { environment } from '../environments/environment';

const routesBase: Routes = [
  // CLIENT ROUTES
  // EXTERNAL VIEWS
  {
    path: '',
    redirectTo: 'client/login',
    pathMatch: 'full',
  },
  {
    path: 'client/login',
    component: LoginComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/forgotKey',
    component: ForgotKeyComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/newKey/:uuid',
    component: NewKeyComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/verifyEmail/:uuid',
    component: VerifiedEmailComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/checkout',
    component: CheckoutCartComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/checkout/:uuid',
    component: CheckoutCartComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/register',
    component: RegisterComponent,
    data: { isOutside: true },
  },
  {
    path: 'client/check/success/:session_id',
    component: SuccessComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/check/fail',
    component: FailComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/check/finalTransfer',
    component: FinalTransferComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/check/successDemo',
    component: SuccessDemoComponent,
    data: {
      isOutside: true,
      class: 'login-body',
      service_key: 'client',
      CLIENT: true,
    },
  },

  // CLIENT PAGES
  {
    path: 'client/module/home',
    component: HomeComponent,
    data: { isOutside: false },
  },
  {
    path: 'client/module/help',
    component: HelpPageComponent,
    data: { isOutside: false },
  },
  {
    path: 'client/module/user-config',
    component: UserConfigComponent,
    data: { isOutside: false },
  },
  {
    path: 'client/module/redirect',
    component: RedirectPageComponent,
    data: { isOutside: true },
  },

  // CLIENT SCANNER VIEWS
  {
    path: 'client/module/scanner/global_view/one_shot',
    component: GlobalViewComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/scanner/global_view/monitor',
    component: GlobalViewComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/scanner/global_view/transform',
    component: GlobalViewComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },

  {
    path: 'client/module/scanner/one_shot/:uuid_scanner/:operation',
    component: ScannerComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/scanner/one_shot',
    component: ScannerComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/scanner/monitor/:uuid_scanner/:operation',
    component: ScannerComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/scanner/monitor',
    component: ScannerComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/scanner/transform/:uuid_scanner/:operation',
    component: ScannerComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/scanner/transform',
    component: ScannerComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/impulse/:uuid_scanner',
    component: ImpulseComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },

  // DEINDEXING VIEWS
  {
    path: 'client/module/deindexing',
    component: DeindexingComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/deindexing/form/:uuid_deindexation',
    component: NewEditDeindexingComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },
  {
    path: 'client/module/deindexing/form',
    component: NewEditDeindexingComponent,
    data: { isOutside: false },
    canActivate: [PaymentGuard],
  },

  // ADMIN ROUTES
  // EXTERNAL
  {
    path: 'admin/login',
    component: LoginComponent,
    data: {
      isOutside: true,
      class: 'login-body-admin',
      service_key: 'admin',
      ADMIN: true,
    },
  },
  {
    path: 'admin/verifyEmail/:uuid',
    component: VerifiedEmailComponent,
    data: {
      isOutside: true,
      class: 'login-body-admin',
      service_key: 'admin',
      ADMIN: true,
    },
  },
  {
    path: 'admin/forgotKey',
    component: ForgotKeyComponent,
    data: {
      isOutside: true,
      class: 'login-body-admin',
      service_key: 'admin',
      ADMIN: true,
    },
  },
  {
    path: 'admin/newKey/:uuid',
    component: NewKeyComponent,
    data: {
      isOutside: true,
      class: 'login-body-admin',
      service_key: 'admin',
      ADMIN: true,
    },
  },

  // ADMIN PAGES
  {
    path: 'admin/module/home',
    component: HomeComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/help',
    component: HelpPageComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/user-config',
    component: UserConfigComponent,
    data: { isOutside: false },
  },
  {
    path: 'module/error',
    component: ErrorPageComponent,
    data: { isOutside: true },
  },

  // ADMIN SCANNER VIEWS
  {
    path: 'admin/module/scanner/global_view/one_shot/:uuid_client/:id',
    component: GlobalViewComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/scanner/global_view/monitor/:uuid_client/:id',
    component: GlobalViewComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/scanner/global_view/transform/:uuid_client/:id',
    component: GlobalViewComponent,
    data: { isOutside: false },
  },

  {
    path: 'admin/module/scanner/one_shot/:uuid_client/:uuid_scanner/:id/:operation',
    component: ScannerComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/scanner/one_shot/:uuid_client/:id',
    component: ScannerComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/scanner/monitor/:uuid_client/:uuid_scanner/:id/:operation',
    component: ScannerComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/scanner/monitor/:uuid_client/:id',
    component: ScannerComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/scanner/transform/:uuid_client/:uuid_scanner/:id/:operation',
    component: ScannerComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/scanner/transform/:uuid_client/:id',
    component: ScannerComponent,
    data: { isOutside: false },
  },

  {
    path: 'admin/module/impulse/:uuid_scanner',
    component: ImpulseComponent,
    data: { isOutside: false },
  },

  // DEINDEXING VIEWS
  {
    path: 'admin/module/deindexing/:uuid_client',
    component: DeindexingComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/deindexing/:uuid_client/form/:uuid_deindexation',
    component: NewEditDeindexingComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/deindexing/:uuid_client/form',
    component: NewEditDeindexingComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/deindexing/:uuid_client/view/:uuid_deindexation',
    component: ViewDeindexingComponent,
    data: { isOutside: false },
  },

  // CONFIG MODULES
  {
    path: 'admin/module/users',
    component: UsersComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/plans',
    component: PlansComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/clients',
    component: ClientsComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/trackingword',
    component: TrackingwordsComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/forbiddenword',
    component: ForbiddenWordsComponent,
    data: { isOutside: false },
  },
  {
    path: 'admin/module/dashboard',
    component: DashAdminComponent,
    data: { isOutside: false },
  },
];

const routesExtras: Routes = [
  {
    path: 'client/site',
    component: SiteComponent,
    data: {
      isOutside: true,
      class: 'backgroundWhite',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/site/contact',
    component: ContactComponent,
    data: {
      isOutside: true,
      class: 'backgroundWhite',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/site/demo',
    component: DemoComponent,
    data: {
      isOutside: true,
      class: 'backgroundWhite',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/site/faq',
    component: FaqComponent,
    data: {
      isOutside: true,
      class: 'backgroundWhite',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/site/unsuscribe',
    component: UnsubscribeComponent,
    data: {
      isOutside: true,
      class: 'backgroundWhite',
      service_key: 'client',
      CLIENT: true,
    },
  },
  {
    path: 'client/site/cookies',
    component: CookiesComponent,
    data: {
      isOutside: true,
      class: 'backgroundWhite',
      service_key: 'client',
      CLIENT: true,
    },
  },
];

const routesLast: Routes = [
  {
    path: '**',
    redirectTo: 'module/error',
  },
];

export const routes: Routes = (
  environment.production ? routesBase : routesBase.concat(routesExtras)
).concat(routesLast);
