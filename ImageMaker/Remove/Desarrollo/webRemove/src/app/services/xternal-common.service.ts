import { Injectable } from '@angular/core';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { ApiConfig, ApiRestService } from './api-rest.service';

@Injectable({
  providedIn: 'root'
})
export class XternalCommonService {
  private ROUTES = API_ROUTES.XTERNAL.COMMON;

  /**
   * Allows consuming internal system services, for unregistered visitors (external)
   * @param apiRest
   */
  constructor(private apiRest: ApiRestService) { }

  /**
   *
   *
   * @param successFuncion
   * @param _config
   */
  listPlans(successFuncion?: Function, _config?: ApiConfig) {
    let config = _config ? _config : {};
    config.ignoraToken = true;
    this.apiRest.get(this.ROUTES.PLANS, {}, successFuncion, config);
  }

  /**
   * Bring all the information of a specific plan based on an ID
   * @param idPlan
   * @param successFuncion
   * @param _config
   */
  getPlan(idPlan: number, successFuncion: Function, _config?: ApiConfig) {
    let config = _config ? _config : {};
    config.ignoraToken = true;
    this.apiRest.get(this.ROUTES.PLAN, {id: idPlan}, successFuncion, config);
  }

  /**
   *
   * Send emails from all sections of the landing page
   * @param params
   * @param successFuncion
   * @param _config
   */
  contact(params: any, successFuncion?: Function, _config?: ApiConfig) {
    let config = _config ? _config : {};
    config.successMessageCode = 'message.email.success';
    config.ignoraToken = true;
    this.apiRest.save(this.ROUTES.CONTACTS, params, successFuncion, config);
  }

  /**
   *
   * @param path
   */
  certificate(path: string) {
    // let path = _path.replace('http://', '').replace('https://', '');
    let config = {
      errorFunction: error => {
        // TODO: verificar si es por error de certificado...
        window.location.replace(this.ROUTES.CERTIFICATE+"?path="+path);
      },
      ignoraToken: true
    };

    this.apiRest.get(this.ROUTES.CERTIFICATE, {path: path}, null, config);
  }
}
