import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';

@Injectable({
  providedIn: 'root'
})
export class ResetPassService {
  private ROUTES_XTERNAL = API_ROUTES.XTERNAL;

  /**
   * Manage password recovery requests
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
  }

  /**
   * Request a new password for user or client
   * @param email
   * @param successFuncion
   * @param config
   */
  requestNewPassword(email: string, successFuncion?: Function, config?: ApiConfig) {
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.ADMIN) {
      this.apiRest.delete(this.ROUTES_XTERNAL.ADMIN_PASSWORD, {"email": email}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.delete(this.ROUTES_XTERNAL.CLIENT_PASSWORD, {"email": email}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Check whether the password recovery URL is valid or not
   * @param uuid
   * @param successFuncion
   * @param config
   */
  checkRequestValid(uuid: string, successFuncion?: Function, config?: ApiConfig) {
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.ADMIN) {
      this.apiRest.get(this.ROUTES_XTERNAL.ADMIN_PASSWORD, {"uuid": uuid}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES_XTERNAL.CLIENT_PASSWORD, {"uuid": uuid}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Update the password of the user or client
   * @param newPassword
   * @param uuidRequest
   * @param successFuncion
   * @param config
   */
  updatePassword(newPassword: string, uuidRequest: string, successFuncion?: Function, config?: ApiConfig) {
    config = this.prepareConfig(config);
    let accessConfig = this.session.getExternalAccessConfig();

    if (accessConfig.ADMIN) {
      this.apiRest.update(this.ROUTES_XTERNAL.ADMIN_PASSWORD, {newPassword: newPassword, uuidRequest:uuidRequest}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.update(this.ROUTES_XTERNAL.CLIENT_PASSWORD, {newPassword: newPassword, uuidRequest:uuidRequest}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Check service settings
   * @param config
   * @private
   */
  private prepareConfig(config: ApiConfig): any {
    config = config ? config : {};
    config.ignoraToken = true;
    return config;
  }
}
