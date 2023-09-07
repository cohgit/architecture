import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SessionService } from '../../helpers/session.service';
import { SessionLogService } from 'src/app/services/session-log.service';
import { UserConfigComponent } from './user-config/user-config.component';
import { MatSidenav } from '@angular/material/sidenav';
import { AccountConfigService } from '../../services/account-config.service';
import { CommonService } from 'src/app/services/common.service';
import { TranslateHelperService } from 'src/app/helpers/translate-helper.service';
import { AccessLevel } from 'src/app/interfaces/IAccessLevel';
import { ACCOUNT_MENU, IMenu, MAIN_MENU } from 'src/app/interfaces/IMenu';
import { Router } from '@angular/router';
import { PaymentsBillsComponent } from 'src/app/pages/payments-bills/payments-bills.component';
import { UtilService } from 'src/app/helpers/util.service';
import { InfoClientsComponent } from '../../pages/clients/info-clients/info-clients.component';
import { ClientService } from '../../services/client.service';
import { UploaderFileService } from '../../helpers/uploader-file.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  private MAX_BELL_NOTIFICATIONS = 5;
  @Input() userType: any = {};
  @Input() sidenav: MatSidenav;
  @ViewChild('menuTrigger') trigger;
  userInfo: any;
  firstLetter: string;
  mainMenu: IMenu[] = [];
  accountMenu: IMenu[] = [];
  module: any;
  notificationNotReaded = [];
  restrictedAccess: boolean;
  profile: any;
  userData: any;
  main_menu_template: any;
  account_menu_template: any;
  logoUrl = '';
  /**
   * Manage the navbar (top menu of the application)
   * @param session
   * @param dialog
   * @param logoutService
   * @param router
   * @param configService
   * @param commonService
   * @param util
   * @param translateHelper
   */
  constructor(
    public session: SessionService,
    private dialog: MatDialog,
    private logoutService: SessionLogService,
    private router: Router,
    private configService: AccountConfigService,
    private commonService: CommonService,
    private util: UtilService,
    public translateHelper: TranslateHelperService,
    public ufs: UploaderFileService
  ) {}

  ngOnInit(): void {
    this.main_menu_template = this.util.cloneObject(MAIN_MENU);
    this.account_menu_template = this.util.cloneObject(ACCOUNT_MENU);

    this.userInfo = this.session?.getUser();
    this.firstLetter = this.userInfo?.name[0];
    this.buildMenu();

    setInterval(() => {
      this.callNotifications();
    }, 60 * 1000);

    // this.callNotifications();
    //in 10 seconds check notifications
    /* interval(10000).subscribe(x => {
       this.callNotifications();
     });*/

    this.restrictedAccess = this.userInfo?.readOnly;

    this.profile =
      this.session?.getUser()?.permissions?.profile?.code?.toLowerCase() ==
      'client'
        ? 'client'
        : 'admin';
    this.checkClientLogo();
  }
  checkUserData() {

  }
  checkClientLogo() {
    if (this.userInfo.permissions.profile.CLIENT) {
      this.configService.getInfo((response) => {
        if (
          response.reference_link_logo &&
          response.reference_link_logo !== ''
        ) {
          this.getImagen(response.reference_link_logo);
        } else {
          this.logoUrl = '../assets/img/logo.png';
        }
      });
    } else {
      this.logoUrl = '../assets/img/logo.png';
    }
  }

  /**
   *
   * image preview
   * @param route
   */
  getImagen(route) {
    this.ufs.getData(route, (data) => {
      this.logoUrl = data;
      /*console.log('route', route);
      console.log('logoUrl', this.logoUrl);*/
    });
  }
  /**
   * Build Main Menu
   */
  buildMenu() {
    this.mainMenu = [];
    this.accountMenu = [];
    this.module = this.session.getAccessConfig();
    this.checkUserData();
    if (this.module) {
      if (this.module.ADMIN) {
        this.buildMainMenuAsAdmin(this.module);
      } else if (this.module.CLIENT) {
        this.buildMainMenuAsClient(this.module);
      }
    } else {
      // Ignore this: must be logout
    }
  }

  /**
   * Build Main Menu as Client
   * @param module
   */
  buildMainMenuAsClient(module: AccessLevel) {

    this.mainMenu.push(this.fixMenu(module, this.main_menu_template.HOME));

    if (this.session.accountPermissions('SCANNER').list) {
      let scannerMenu = this.main_menu_template.SCANNER;
      scannerMenu.child = [];

      if (this.session.accountPermissions('ONE_SHOT').list) {
        scannerMenu.child.push(this.main_menu_template.ONE_SHOT);
      }

      if (this.session.accountPermissions('RECURRENT').list) {
        scannerMenu.child.push(this.main_menu_template.MONITOR);
      }
      if (this.session.accountPermissions('TRANSFORM').list) {
        scannerMenu.child.push(this.main_menu_template.TRANSFORM);
      }
      if (this.session.accountPermissions('DEINDEXATION').list) {
        scannerMenu.child.push(this.main_menu_template.DEINDEXING);
      }

      if (scannerMenu.child.length > 0) {
        this.mainMenu.push(this.fixMenu(module, scannerMenu));
      }
    }
    this.configService.getInfo((response) => {
      this.userData = response;
      this.accountMenu.push(this.account_menu_template.CONFIG);
    this.accountMenu.push(this.account_menu_template.PLANS);
    if (this.userData?.view_payments) {
      this.accountMenu.push(this.account_menu_template.BILLS);
    }
    this.accountMenu.push(this.account_menu_template.HELP);
    });

  }

  /**
   * Build Main Menu as Admin
   * @param module
   */
  buildMainMenuAsAdmin(module: AccessLevel) {
    this.mainMenu.push(this.fixMenu(module, this.main_menu_template.HOME));
    this.validateMenuOnRead(module, 'DASHBOARD');
    this.validateMenu(module, 'USERS');
    this.validateMenu(module, 'PLANS');
    this.validateMenu(module, 'CLIENTS');
    this.validateSystemMenu(module, 'TRACKING_WORDS');

    this.accountMenu.push(this.account_menu_template.CONFIG);
    this.accountMenu.push(this.account_menu_template.HELP);
  }

  /**
   * Validate access to a module and push it in menu's list
   * @param module
   * @param keyAccess
   */
  private validateMenu(module: AccessLevel, keyAccess: string): void {
    if (this.session.accountPermissions(keyAccess).list) {
      this.mainMenu.push(
        this.fixMenu(module, this.main_menu_template[keyAccess])
      );
    }
  }

  /**
   * Validate access to a module and push it in menu's list
   * @param module
   * @param keyAccess
   */
  private validateMenuOnRead(module: AccessLevel, keyAccess: string): void {
    if (this.session.accountPermissions(keyAccess).read) {
      this.mainMenu.push(
        this.fixMenu(module, this.main_menu_template[keyAccess])
      );
    }
  }

  /**
   *  Validate access to a module and push it in system's menu list
   * (Needs to be redone when 'system' have more than 1 child)
   * @param module
   * @param keyAccess
   */
  private validateSystemMenu(module: AccessLevel, keyAccess: string): void {
    if (this.session.accountPermissions(keyAccess).list) {
      this.mainMenu.push(this.fixMenu(module, this.main_menu_template.SYSTEM));
    }
  }

  /**
   * Change menu parameters (if needed) by module
   * @param module
   * @param menu
   * @returns
   */
  fixMenu(module: AccessLevel, menu: IMenu): IMenu {
    menu.link = menu.link ? module.service_key + menu.link : '';

    if (menu.child) {
      menu.child.forEach((child) => (child = this.fixMenu(module, child)));
    }

    return menu;
  }

  /**
   * Redirect page to a new site location
   * @param item
   */
  reroute(item: IMenu): void {
    if (item.link !== '') {
      this.session.goTo(item.link);
    }
  }

  /**
   * Call alert notification service for navbar bell
   * will show in bell only notifications not readed or urgents
   */
  private callNotifications(): void {
    this.commonService.listNotifications(
      this.MAX_BELL_NOTIFICATIONS,
      (result) => {
        this.notificationNotReaded = [];

        result.forEach((not) => {
          if (!not.readed) {
            this.notificationNotReaded.push(not);
          }
        });
      }
    );
  }

  /**
   * Logout service
   */
  logout(): void {
    this.logoutService.logout(() => this.session.destroySession(), {
      errorFunction: () => this.session.destroySession(),
    });
  }

  /**
   * Open configuration account modal.
   */
  openConfigModal(): void {
    this.trigger.closeMenu();
    this.configService.getInfo((response) => {
      this.dialog.open(UserConfigComponent, {
        width: '70%',
        height: '400px',
        closeOnNavigation: true,
        disableClose: true,
        data: response,
      });
    });
  }

  /**
   * Toggle Sidebar
   */
  openSidebar() {
    this.trigger.closeMenu();
    if (!this.restrictedAccess) {
      this.sidenav.open();
    } else {
      this.router.navigate(['client/module/redirect']);
    }
  }

  /**
   * open help module
   */
  goHelp() {
    if (this.module.ADMIN) {
      this.router.navigate(['admin/module/help']);
    } else {
      this.router.navigate(['client/module/help']);
    }
  }

  /**
   * Open Stripe customer portal
   */
  openBills() {
    this.trigger.closeMenu();
    this.configService.getInfo((response) => {
      this.dialog.open(PaymentsBillsComponent, {
        closeOnNavigation: true,
        disableClose: true,
        width: '60%',
        data: response,
      });
    });
    /* this.accountConfigService.getPaymentPortal(resp => {
       this.session.goToNewTab(resp.paymentPortal);
     });*/
  }
}
