'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">remove-app documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Comenzando</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Descripción general
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>Léeme
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencias
                                    </a>
                                </li>
                                <li class="link">
                                    <a href="properties.html" data-type="chapter-link">
                                        <span class="icon ion-ios-apps"></span>Propiedades
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-toggle="collapse" ${ isNormalMode ?
                                'data-target="#modules-links"' : 'data-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Módulos</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link" >AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' : 'data-target="#xs-components-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Componentes</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' :
                                            'id="xs-components-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                            <li class="link">
                                                <a href="components/ActiveLicencesComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ActiveLicencesComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AddItemComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AddItemComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AdvaceReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AdvaceReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AlertScannersComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AlertScannersComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AlertSectionsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AlertSectionsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AppComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AppComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ApproveImpulseComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ApproveImpulseComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/BaseReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >BaseReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CallYouComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CallYouComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ChangePlanComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ChangePlanComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CheckoutCartComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CheckoutCartComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ChoosePlanComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ChoosePlanComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ClientBillComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ClientBillComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ClientsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ClientsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CommentHistoryComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CommentHistoryComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CommentScannerComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CommentScannerComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ConfigReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ConfigReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ConfigScannerComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ConfigScannerComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ConfirmChangeComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ConfirmChangeComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ConfirmScannerComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ConfirmScannerComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ContactComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ContactComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ContentModalComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ContentModalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CookiesComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CookiesComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CustomCheckoutComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CustomCheckoutComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CustomPlanComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CustomPlanComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DashAdminComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DashAdminComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DashReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DashReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DashboardScannerComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DashboardScannerComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DeindexingComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DeindexingComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DemoComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DemoComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DynamicDisplayComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DynamicDisplayComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DynamicFormComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DynamicFormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ErrorPageComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ErrorPageComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FailComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FailComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FaqComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FaqComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FileInputComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FileInputComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FileUploadComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FileUploadComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FiltersComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FiltersComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FinalTransferComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FinalTransferComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FinalURLComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FinalURLComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FirstTimeComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FirstTimeComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FooterComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FooterComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FooterReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FooterReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ForbiddenWordsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ForbiddenWordsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ForgotKeyComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ForgotKeyComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/GlobalViewComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >GlobalViewComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HeaderComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HeaderComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HeaderReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HeaderReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HelpPageComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HelpPageComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HistoryAuditUserComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HistoryAuditUserComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HistoryComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HistoryComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HomeComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HomeComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ImpulseComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ImpulseComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/InfoClientsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >InfoClientsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/KeywordTableComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >KeywordTableComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LoginComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LoginComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ManageNewsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ManageNewsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ModalComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ModalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NavbarComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NavbarComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewAccountComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewAccountComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEditClientComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEditClientComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEditDeindexingComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEditDeindexingComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEditForbiddenWordsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEditForbiddenWordsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEditImpulseComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEditImpulseComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEditPlanComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEditPlanComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEditTrackingwordsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEditTrackingwordsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEditUserComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEditUserComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewKeyComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewKeyComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NotificationsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NotificationsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PaymentsBillsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PaymentsBillsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PlansComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PlansComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PoliticsModalComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PoliticsModalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/RedirectPageComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >RedirectPageComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/RegisterComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >RegisterComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ResultReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ResultReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ResultScannerComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ResultScannerComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ScannerComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ScannerComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ScannerReportComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ScannerReportComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SelectDateComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SelectDateComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SidebarComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SidebarComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SiteComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SiteComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SuccessComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SuccessComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SuccessDemoComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SuccessDemoComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/TableTrackingProgressComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TableTrackingProgressComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/TermsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TermsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/TrackingDeindexingComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TrackingDeindexingComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/TrackingwordsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TrackingwordsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/TransformSectionComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TransformSectionComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/UnsubscribeComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >UnsubscribeComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/UserConfigComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >UserConfigComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/UsersComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >UsersComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/VerifiedEmailComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >VerifiedEmailComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ViewDeindexingComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ViewDeindexingComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#directives-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' : 'data-target="#xs-directives-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                        <span class="icon ion-md-code-working"></span>
                                        <span>Directivas</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="directives-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' :
                                        'id="xs-directives-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                        <li class="link">
                                            <a href="directives/OnlynumberDirective.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >OnlynumberDirective</a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' : 'data-target="#xs-injectables-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Inyectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' :
                                        'id="xs-injectables-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                        <li class="link">
                                            <a href="injectables/UrlService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >UrlService</a>
                                        </li>
                                    </ul>
                                </li>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#pipes-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' : 'data-target="#xs-pipes-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                            <span class="icon ion-md-add"></span>
                                            <span>Tuberías</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="pipes-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' :
                                            'id="xs-pipes-links-module-AppModule-eb4f98a0a05d4d27764c1b47222be4b38198ba2d804a7a5182f867088a52fe2fed7af5d25feb00f630255e0c8c9196ab57b35b2b43436224d549cb249f268e7f"' }>
                                            <li class="link">
                                                <a href="pipes/CardBrandsPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CardBrandsPipe</a>
                                            </li>
                                            <li class="link">
                                                <a href="pipes/CustomizedPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CustomizedPipe</a>
                                            </li>
                                            <li class="link">
                                                <a href="pipes/FeelingColorsPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FeelingColorsPipe</a>
                                            </li>
                                            <li class="link">
                                                <a href="pipes/HighlightPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HighlightPipe</a>
                                            </li>
                                            <li class="link">
                                                <a href="pipes/PaymentsMethodsPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PaymentsMethodsPipe</a>
                                            </li>
                                            <li class="link">
                                                <a href="pipes/TableFilterPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TableFilterPipe</a>
                                            </li>
                                            <li class="link">
                                                <a href="pipes/TrackingPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TrackingPipe</a>
                                            </li>
                                            <li class="link">
                                                <a href="pipes/VisibilityPipe.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >VisibilityPipe</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#components-links"' :
                            'data-target="#xs-components-links"' }>
                            <span class="icon ion-md-cog"></span>
                            <span>Componentes</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="components-links"' : 'id="xs-components-links"' }>
                            <li class="link">
                                <a href="components/ModalBillingComponent.html" data-type="entity-link" >ModalBillingComponent</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#classes-links"' :
                            'data-target="#xs-classes-links"' }>
                            <span class="icon ion-ios-paper"></span>
                            <span>Clases</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="classes-links"' : 'id="xs-classes-links"' }>
                            <li class="link">
                                <a href="classes/AccessLevel.html" data-type="entity-link" >AccessLevel</a>
                            </li>
                            <li class="link">
                                <a href="classes/Clients.html" data-type="entity-link" >Clients</a>
                            </li>
                            <li class="link">
                                <a href="classes/DialogData.html" data-type="entity-link" >DialogData</a>
                            </li>
                            <li class="link">
                                <a href="classes/ModalConfig.html" data-type="entity-link" >ModalConfig</a>
                            </li>
                            <li class="link">
                                <a href="classes/ResponseSD.html" data-type="entity-link" >ResponseSD</a>
                            </li>
                            <li class="link">
                                <a href="classes/Scanner.html" data-type="entity-link" >Scanner</a>
                            </li>
                            <li class="link">
                                <a href="classes/ScannerDashboard.html" data-type="entity-link" >ScannerDashboard</a>
                            </li>
                            <li class="link">
                                <a href="classes/ScannerDashboardV2.html" data-type="entity-link" >ScannerDashboardV2</a>
                            </li>
                            <li class="link">
                                <a href="classes/ScannerFilter.html" data-type="entity-link" >ScannerFilter</a>
                            </li>
                            <li class="link">
                                <a href="classes/TrackingWords.html" data-type="entity-link" >TrackingWords</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#injectables-links"' :
                                'data-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Inyectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AccountConfigService.html" data-type="entity-link" >AccountConfigService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/AlertService.html" data-type="entity-link" >AlertService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ApiRestService.html" data-type="entity-link" >ApiRestService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ClientService.html" data-type="entity-link" >ClientService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/CommonService.html" data-type="entity-link" >CommonService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/DashboardBuilderService.html" data-type="entity-link" >DashboardBuilderService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/DashboardService.html" data-type="entity-link" >DashboardService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/DeindexationService.html" data-type="entity-link" >DeindexationService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/DynamicFormHelperService.html" data-type="entity-link" >DynamicFormHelperService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ForbiddenWordService.html" data-type="entity-link" >ForbiddenWordService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/GoogleApiService.html" data-type="entity-link" >GoogleApiService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ImpulseService.html" data-type="entity-link" >ImpulseService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ImpulseVariableService.html" data-type="entity-link" >ImpulseVariableService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ModalService.html" data-type="entity-link" >ModalService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/PaymentStripeService.html" data-type="entity-link" >PaymentStripeService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/PlanService.html" data-type="entity-link" >PlanService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ReportService.html" data-type="entity-link" >ReportService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ResetPassService.html" data-type="entity-link" >ResetPassService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ScannerCommentsService.html" data-type="entity-link" >ScannerCommentsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ScannerService.html" data-type="entity-link" >ScannerService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SessionLogService.html" data-type="entity-link" >SessionLogService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SessionService.html" data-type="entity-link" >SessionService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SuscriptionService.html" data-type="entity-link" >SuscriptionService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/TrackingWordService.html" data-type="entity-link" >TrackingWordService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/TranslateHelperService.html" data-type="entity-link" >TranslateHelperService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UploaderFileService.html" data-type="entity-link" >UploaderFileService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UserService.html" data-type="entity-link" >UserService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UtilService.html" data-type="entity-link" >UtilService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/WordpressApiService.html" data-type="entity-link" >WordpressApiService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/XternalCommonService.html" data-type="entity-link" >XternalCommonService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#guards-links"' :
                            'data-target="#xs-guards-links"' }>
                            <span class="icon ion-ios-lock"></span>
                            <span>Guardias</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="guards-links"' : 'id="xs-guards-links"' }>
                            <li class="link">
                                <a href="guards/PaymentGuard.html" data-type="entity-link" >PaymentGuard</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#interfaces-links"' :
                            'data-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/ApiConfig.html" data-type="entity-link" >ApiConfig</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Columns.html" data-type="entity-link" >Columns</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Days.html" data-type="entity-link" >Days</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IAccessLevel.html" data-type="entity-link" >IAccessLevel</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IClients.html" data-type="entity-link" >IClients</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicForm.html" data-type="entity-link" >IDynamicForm</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormConditionResult.html" data-type="entity-link" >IDynamicFormConditionResult</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormConditionResultLabel.html" data-type="entity-link" >IDynamicFormConditionResultLabel</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormInput.html" data-type="entity-link" >IDynamicFormInput</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormInputLabel.html" data-type="entity-link" >IDynamicFormInputLabel</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormInputOption.html" data-type="entity-link" >IDynamicFormInputOption</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormInputOptionsLabel.html" data-type="entity-link" >IDynamicFormInputOptionsLabel</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormSection.html" data-type="entity-link" >IDynamicFormSection</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IDynamicFormSectionLabel.html" data-type="entity-link" >IDynamicFormSectionLabel</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IFormLabel.html" data-type="entity-link" >IFormLabel</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IMenu.html" data-type="entity-link" >IMenu</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IPermissionService.html" data-type="entity-link" >IPermissionService</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IPlans.html" data-type="entity-link" >IPlans</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScanner.html" data-type="entity-link" >IScanner</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerDashboard.html" data-type="entity-link" >IScannerDashboard</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerDashboardItem.html" data-type="entity-link" >IScannerDashboardItem</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerDashboardItem-1.html" data-type="entity-link" >IScannerDashboardItem</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerDashboardResult.html" data-type="entity-link" >IScannerDashboardResult</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerDashboardV2.html" data-type="entity-link" >IScannerDashboardV2</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerFilter.html" data-type="entity-link" >IScannerFilter</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerGraph.html" data-type="entity-link" >IScannerGraph</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerGraph-1.html" data-type="entity-link" >IScannerGraph</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerKeywordsElementList.html" data-type="entity-link" >IScannerKeywordsElementList</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IScannerTransformData.html" data-type="entity-link" >IScannerTransformData</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/ITrackingWords.html" data-type="entity-link" >ITrackingWords</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IURLTimeLineal.html" data-type="entity-link" >IURLTimeLineal</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IURLTimeLineal-1.html" data-type="entity-link" >IURLTimeLineal</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IUsers.html" data-type="entity-link" >IUsers</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#miscellaneous-links"'
                            : 'data-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscelánea</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/functions.html" data-type="entity-link">Funciones</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentación generada utilizando <a href="https://compodoc.app/" target="_blank">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});