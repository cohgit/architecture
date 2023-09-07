import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';

@Injectable({
  providedIn: 'root'
})
export class SuscriptionService {
  private ROUTES_SUSCRIPTION = API_ROUTES.XTERNAL.SUSCRIPTION;

  /**
   * They are used for the services of new clients or referrals in the initial part of the web (landing page)
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
  }

  /**
   * Check if the mail that the client is writing a new user has been registered in the database
   * @param email
   * @param successFuncion
   * @param config
   */
  verifyNewEmailClient(email: string, successFuncion?: Function, config?: ApiConfig) {
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES_SUSCRIPTION, {"email": email}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Check if a plan suggestion to a client is active
   * @param uuid
   * @param successFuncion
   * @param config
   */

  verifySuggestion(uuid: string, successFuncion?: Function, config?: ApiConfig) {
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES_SUSCRIPTION, {"uuid": uuid}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   *  In case of successful payment with stripe register the subscription
   * @param newClient
   * @param successFuncion
   * @param config
   */
  suscribeAsStripe(newClient: any, successFuncion?: Function, config?: ApiConfig) {
    newClient.attempt = false;
    newClient.paymentMethod = 'stripe';
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_SUSCRIPTION, newClient, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * In case of successful payment with transfer register the subscription
   * @param newClient
   * @param successFuncion
   * @param config
   */
  suscribeAsTransfer(newClient: any, successFuncion?: Function, config?: ApiConfig) {
    newClient.attempt = false;
    newClient.paymentMethod = 'transfer';
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_SUSCRIPTION, newClient, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Register a customer who bought a license cost 0
   * @param newClient
   * @param successFuncion
   * @param config
   */
  suscribeAsDemo(newClient: any, successFuncion?: Function, config?: ApiConfig) {
    newClient.attempt = false;
    newClient.paymentMethod = 'transfer';
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_SUSCRIPTION, newClient, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Log an unfinished subscription attempt
   * @param newClient
   * @param successFuncion
   * @param config
   */
  attemptSuscribe(newClient: any, successFuncion?: Function, config?: ApiConfig) {
    newClient.attempt = true;
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_SUSCRIPTION, newClient, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Run email verification.
   * @param verificationKey
   * @param successFuncion
   * @param config
   */
  verifyEmail(verificationKey: string, successFuncion?: Function, config?: ApiConfig) {
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.ADMIN) {
      this.apiRest.update(this.ROUTES_SUSCRIPTION, {"uuid": verificationKey, "type": 'user'}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.update(this.ROUTES_SUSCRIPTION, {"uuid": verificationKey, "type": 'client'}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Set the parameter settings
   * @param config
   * @private
   */
  private prepareConfig(config: ApiConfig): any {
    config = config ? config : {};
    config.ignoraToken = true;
    config.ignoreSuccessMessage = true;
    return config;
  }
}
