import { Injectable } from '@angular/core';

import {ActivatedRoute, Router} from '@angular/router';
import * as moment from 'moment';
import {AccessLevel} from 'src/app/interfaces/IAccessLevel'
import {IPermissionService} from 'src/app/interfaces/IPermissionsService'

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private USER_ADMIN_KEY = 'remove_admin_user';
  private USER_CLIENT_KEY = 'remove_client_user';
  private USER_ACCESS_CONFIG = 'remove_user_access';

  private PREFIX_SUSC_DATA = 'SUSC_INFO_';
  private PREFIX_SUSC_DATE = 'SUSC_DATE_';

  checkParams: any = {};

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
    this.activatedRoute.data.subscribe(data => {
      this.checkParams = data;
    });
  }

  /**
   * Create an admin session object
   * @param user
   */
  createAdminSession(user: any): void {
    localStorage.setItem(this.USER_ADMIN_KEY, JSON.stringify(user));
  }
  /**
   * Create a client session object
   * @param user
   */
  createClientSession(user: any): void {
    localStorage.setItem(this.USER_CLIENT_KEY, JSON.stringify(user));
  }

  /**
   * Destroy a session, if is user or client by module
   */
  destroySession(): void {
    let accessConfig = this.getAccessConfig();

    if (accessConfig.ADMIN) {
      localStorage.removeItem(this.USER_ADMIN_KEY);
      localStorage.removeItem(this.USER_ACCESS_CONFIG);
      this.goTo('admin/login');
    } else if (accessConfig.CLIENT) {
      localStorage.removeItem(this.USER_CLIENT_KEY);
      localStorage.removeItem(this.USER_ACCESS_CONFIG);
      this.goTo('client/login');
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Go to login page, depends on module
   */
  goLogin(): void {
    this.executionForModule(() => {
      this.goTo('admin/login');
    }, () => {
      this.goTo('client/login');
    })
  }
  /**
   * Go to user login page
   */
  goAdminInitPage(): void {
    this.goTo('/admin/module/home');
  }
  /**
   * Go to client login page
   */
  goClientInitPage(): void {
    this.goTo('/client/module/home');
  }

  /**
   *
   * @param path Redirect to a specific site path
   * @param params
   */
  goTo(path: string, params?: string[]): void {
    let pathWithParams = [];
    pathWithParams.push(path);

    /*if (params) {
      params.forEach(param =>{ pathWithParams.push(param)});
    }*/
    this.router.navigateByUrl( path );
  }
  /**
   * Open a new tab with a specific site path
   * @param path
   * @param params
   */
  goToNewTab(path: string, params?: string[]): void {
    window.open(params ? path + params.filter(Boolean).join("/") : path, '_blank');
  }

  /**
   * Obtain account info, depends on module
   * @returns
   */
  getUser(): any {
    let userData = {};

    if (this.getClientUser() || this.getAdminUser()) {
      this.executionForModule(() => {
        userData = this.getAdminUser();
      }, () => {
        userData = this.getClientUser();
      });
      return userData;
    } else {
      this.goLogin();
    }
  }

  isRestrictedAccess(): boolean {
    return this.getAccessConfig().CLIENT ? this.getUser().readOnly : false;
  }
  /**
   * Obtain client's plan detail
   * @returns
   */
  getUserPlanDetail(): any {
    return this.getUser()?.plan[0]?.detail;
  }

  /**
   * Obtain user's session info
   * @returns
   */
  public getAdminUser(): any {
    return JSON.parse(localStorage.getItem(this.USER_ADMIN_KEY));
  }
  /**
   *
   * @returns Obtains client's session info
   */
  public getClientUser(): any {
    return JSON.parse(localStorage.getItem(this.USER_CLIENT_KEY));
  }

  /**
   * Obtain account's session language
   * @returns
   */
  getUserLanguage(): string {
    return this.getUser().config.lang;
  }

  /**
   * Obatins account's token
   * @returns
   */
  getToken(): string {
    const user = this.getUser();

    if (user && user['token']) {
      return user['token'];
    }

    return '';
  }

  /**
   * Validate if account's token exist
   * @returns
   */
  isValidSession(): boolean {
    return this.getToken() !== '';
  }

  /**
   * Save in session an object for future reference
   * @param prefix
   * @param id
   * @param object
   * @returns
   */
  saveBackup(prefix: string, id: number, object: any): string {
    const namePath = prefix+"_"+id;
    localStorage.setItem(namePath, JSON.stringify(object));
    return namePath;
  }
  /**
   * Restores an object in session
   * @param prefix
   * @param id
   * @returns
   */
  getBackup(prefix: string, id: number): any {
    return JSON.parse(localStorage.getItem(prefix+"_"+id));
  }
  /**
   * Destroy an object in session
   * @param namePath
   */
  destroyBackup(namePath: string): void {
    if (namePath) {
      localStorage.removeItem(namePath);
    }
  }

  /**
   * Check access from url location (Downside: don´t change 'module' path prefix)
   * @returns
   */
  getAccessConfig(): AccessLevel {
    if (window.location.href.includes('/client/module')) {
      return Object.assign({}, AccessLevel.instanceClient());
    } else if (window.location.href.includes('/admin/module')) {
      return Object.assign({}, AccessLevel.instanceAdmin());
    }

    return null;
  }

  /**
   * Execute a function, depends on module
   * @param adminFunction
   * @param clientFunction
   */
  private executionForModule(adminFunction?: Function, clientFunction?: Function): void {
    let accessConfig = this.getAccessConfig();

    if (accessConfig) {
      if (accessConfig.ADMIN) {
        if (adminFunction) adminFunction();
      } else if (accessConfig.CLIENT) {
        if (clientFunction) clientFunction();
      } else {
        console.error("INVALID ACCESS CONFIG: ", accessConfig);
      }
    } else {
      // Ignore this, must be logout
    }
  }

  /**
   * Obtains an object reference that represents actual module
   * @returns
   */
  getExternalAccessConfig(): AccessLevel {
    if (window.location.href.includes('/client/')) {
      return AccessLevel.instanceClient();
    } else if (window.location.href.includes('/admin/')) {
      return AccessLevel.instanceAdmin();
    }

    return null;
  }

  /**
   * Update client session object if modified.
   * @param _client
   */
  refreshClientSession(_client: any): void {
    let client = this.getClientUser();

    client.name = _client.name + ' ' + _client.lastname;
    client.config.country = _client.country.tag;
    client.config.lang = _client.language;

    this.createClientSession(client);
  }

  accountPermissions(sectionKey: string): IPermissionService {
    let permissions = null;
    this.executionForModule(() => {
      permissions = this.getAdminUser().permissions.lstAccess[sectionKey];
    }, () => {
      permissions = this.getClientUser().permissions.lstAccess[sectionKey];
    });
    return permissions != null ? permissions : {key: ''};
  }

  saveTempSuscription(suscription: any): void {
    if (suscription?.client?.email) {
      localStorage.setItem(this.PREFIX_SUSC_DATA, JSON.stringify(suscription));
      localStorage.setItem(this.PREFIX_SUSC_DATE, JSON.stringify(moment.now()));
    }
  }
  rescueTempSuscription(): any {
    let suscription = JSON.parse(localStorage.getItem(this.PREFIX_SUSC_DATA));
    localStorage.removeItem(this.PREFIX_SUSC_DATA);
    localStorage.removeItem(this.PREFIX_SUSC_DATE);
    return suscription;
  }
}
