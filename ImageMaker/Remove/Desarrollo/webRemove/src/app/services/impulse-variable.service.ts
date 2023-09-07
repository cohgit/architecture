import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';

@Injectable({
  providedIn: 'root'
})
export class ImpulseVariableService {
  public permissions: IPermissionService;
  private ROUTES_ADMIN = API_ROUTES.ADMIN.IMPULSE.VARIABLE;

  /**
   *
   * Manages all the functionalities of the news module of a published impulse (transforms)
   *
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("IMPULSE_VARIABLE");
  }

  /**
   * Gets the data of a record based of an ID
   * @param idImpulse
   * @param successFuncion
   * @param config
   */
  get(idImpulse: number, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(this.ROUTES_ADMIN, {"id_impulse": idImpulse}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Stores the data of a record
   * @param variable
   * @param successFuncion
   * @param config
   */
  save(variable: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES_ADMIN, variable, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param variable
   * @param successFuncion
   * @param config
   */
  update(variable: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES_ADMIN, variable, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }
}
