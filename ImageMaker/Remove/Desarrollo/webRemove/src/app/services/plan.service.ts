import { Injectable } from '@angular/core';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';
import { ApiRestService, ApiConfig } from './api-rest.service';

@Injectable({
  providedIn: 'root'
})
export class PlanService {
  public permissions: IPermissionService;
  public permissionsSuggestions: IPermissionService;
  private ROUTES = API_ROUTES.ADMIN.PLANS;
  private ROUTES_SUGGESTIONS = API_ROUTES.ADMIN.PLANS_SUGGESTIONS;

  /**
   * Manage all the functionalities of the plans module (administrator)
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("PLANS");
    this.permissionsSuggestions = this.session.accountPermissions("PLANS_SUGGESTIONS");
  }

  /**
   * Bring all the data of a record based on their id
   * @param id
   * @param successFuncion
   * @param config
   */
  get(id?: number, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(this.ROUTES, {"id": id}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Gets a list of all the records registered in the system
   * @param successFuncion
   * @param config
   */
  list(successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.list) {
      this.apiRest.list(this.ROUTES, {}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Stores the data of a record
   * @param plan
   * @param successFuncion
   * @param config
   */
  save(plan: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES, plan, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param plan
   * @param successFuncion
   * @param config
   */
  update(plan: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES, plan, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Delete a record logically from the database, key param: id
   * @param id
   * @param successFuncion
   * @param config
   */
  delete(id: number, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.delete) {
      this.apiRest.delete(this.ROUTES, {"id": id}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   *
   * @param plan
   * @param successFuncion
   * @param config
   */
  updateSuggestions(plan: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsSuggestions.update) {
      this.apiRest.update(this.ROUTES_SUGGESTIONS, plan, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   *
   * @param _uuids
   * @param successFuncion
   * @param config
   */
  sendSuggestions(_uuids: string[], successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsSuggestions.update) {
      const uuids = _uuids ? _uuids.join(",") : "";
      this.apiRest.get(this.ROUTES_SUGGESTIONS, {uuid: uuids}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }
}
