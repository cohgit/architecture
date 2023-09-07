import {Injectable} from '@angular/core';
import {ApiConfig, ApiRestService} from './api-rest.service';
import {SessionService} from '../helpers/session.service';
import {UtilService} from "../helpers/util.service";

@Injectable({
  providedIn: 'root'
})
export class SessionLogService {
  /**
   * Allows login and logout for client and user
   * @param apiRest
   * @param sessionService
   * @param utilservice
   */
  constructor(private apiRest: ApiRestService, private sessionService: SessionService, private utilservice: UtilService) {
  }

  /**
   * Allows user login
   * @param login
   * @param password
   * @param successFunction
   * @param config
   */
  loginForAdministrator(login: string, password: string, successFunction?: Function, config?: ApiConfig) {
    config.ignoreSuccessMessage = true;

    this.apiRest.loginAdmin({login: login, password: password}, data => {
      this.sessionService.createAdminSession(data);

      if (successFunction) {
        successFunction(data);
      }

      this.sessionService.goAdminInitPage();
    }, config);
  }

  /**
   * Allows client login
   * @param login
   * @param password
   * @param successFunction
   * @param config
   */
  loginForClient(login: string, password: string, successFunction?: Function, config?: ApiConfig) {
    config.ignoreSuccessMessage = true;

    this.apiRest.loginClient({login: login, password: password}, data => {
      if (successFunction) {
        successFunction(data);
      }

      if (data.message !== 'warning.client.must.verify.email') {
        this.sessionService.createClientSession(data);
        this.sessionService.goClientInitPage();
      }
    }, config);
  }

  /**
   * Logout for user and clients
   * @param successFunction
   * @param config
   */
  logout(successFunction?: Function, config?: ApiConfig) {
    config = Object.assign({ignoreSuccessMessage: true}, config);
    this.apiRest.logout(successFunction, config);
  }
}
