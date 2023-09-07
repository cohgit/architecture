import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';

@Injectable({
  providedIn: 'root'
})
export class AccountConfigService {
  private ROUTES_ADMIN = API_ROUTES.ADMIN.CONFIG;
  private ROUTES_CLIENT = API_ROUTES.CLIENT.CONFIG;

  /**
   * It allows managing actions on a user / client account, it includes the following functionalities:
   * In case the client has the permission:
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
  }

  /**
   * In case the user has the permission, obtains the account information of a user or client
   * @param successFuncion
   * @param config
   */
  getInfo(successFuncion?: Function, config?: ApiConfig) {
    if (this.session.getAccessConfig().ADMIN) {
      this.apiRest.get(this.ROUTES_ADMIN, {typeRequest: 'configuration'}, successFuncion, config);
    } else if (this.session.getAccessConfig().CLIENT) {
      this.apiRest.get(this.ROUTES_CLIENT, {typeRequest: 'configuration'}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.session.getAccessConfig());
    }
  }

  /**
   * In case the client has the permission, get the payment portal from Stripe
   * @param successFuncion
   * @param config
   */
  getPaymentPortal(successFuncion?: Function, config?: ApiConfig) {
    if (this.session.getAccessConfig().CLIENT) {
      this.apiRest.get(this.ROUTES_CLIENT, {typeRequest: 'paymentPortal', url: window.location.href}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.session.getAccessConfig());
    }
  }

  /**
   * Update the personal information of a user or client
   * @param configAccount
   * @param successFuncion
   * @param config
   */
  update(configAccount: any, successFuncion?: Function, config?: ApiConfig) {
    if (this.session.getAccessConfig().ADMIN) {
      this.apiRest.update(this.ROUTES_ADMIN, configAccount, successFuncion, config);
    } else if (this.session.getAccessConfig().CLIENT) {
      let _config = config ? config : {};
      _config.headers = {transaction: 'update_info'};
      this.apiRest.update(this.ROUTES_CLIENT, configAccount, successFuncion, _config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.session.getAccessConfig());
    }
  }

  /**
   *
   * Invokes the service that confirms the change of a client's plan
   * @param newPlan
   * @param successFuncion
   * @param config
   */
  updatePlan(newPlan: any, successFuncion?: Function, config?: ApiConfig) {
    if (this.session.getAccessConfig().CLIENT) {
      let _config = config ? config : {};
      _config.headers = {transaction: 'change_plan'};
      this.apiRest.update(this.ROUTES_CLIENT, newPlan, successFuncion, _config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.session.getAccessConfig());
    }
  }

  /**
   * Submit a request for a custom plan from a client
   * @param quote
   * @param successFuncion
   * @param config
   */
  requestQuote(quote: any, successFuncion?: Function, config?: ApiConfig) {
    if (this.session.getAccessConfig().CLIENT) {
      let _config = config ? config : {};
      _config.headers = {transaction: 'request_quote'};
      this.apiRest.update(this.ROUTES_CLIENT, quote, successFuncion, _config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.session.getAccessConfig());
    }
  }

  /**
   * Allows you to send another validation email so that the user can validate their email
   * @param successFuncion
   * @param config
   */
  requestVerificationEmail(successFuncion?: Function, config?: ApiConfig) {
    if (this.session.getAccessConfig().CLIENT) {
      let _config = config ? config : {};
      _config.headers = {transaction: 'request_verification_email'};
      this.apiRest.update(this.ROUTES_CLIENT, {}, successFuncion, _config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.session.getAccessConfig());
    }
  }

  /**
   * Update the password of the user and the client
   * @param newPassword
   * @param successFuncion
   * @param config
   */
  updatePassword(newPassword: any, successFuncion?: Function, config?: ApiConfig) {
    if (this.session.getAccessConfig().ADMIN) {
      this.apiRest.save(this.ROUTES_ADMIN, newPassword, successFuncion, config);
    } else if (this.session.getAccessConfig().CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT, newPassword, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.session.getAccessConfig());
    }
  }
}
