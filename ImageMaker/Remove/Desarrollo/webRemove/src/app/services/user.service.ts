import { Injectable } from '@angular/core';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';
import { ApiRestService, ApiConfig } from './api-rest.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public permissions: IPermissionService;
  public permissionsAudit: IPermissionService;
  private ROUTES = API_ROUTES.ADMIN.USERS;
  private ROUTE_AUDIT = API_ROUTES.ADMIN.USERS_AUDIT;

  /**
   * Manages all the functionalities of the user module (administrator)
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("USERS");
    this.permissionsAudit = this.session.accountPermissions("USERS_AUDIT");
  }

  /**
   * Bring all the data of a record based on their uuid
   * @param uuid
   * @param successFuncion
   * @param config
   */
  get(uuid?: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(this.ROUTES, {"uuid": uuid}, successFuncion, config);
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
   * @param user
   * @param successFuncion
   * @param config
   */
  save(user: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES, user, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param user
   * @param successFuncion
   * @param config
   */
  update(user: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES, user, successFuncion, config);
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
   * Get the log list of actions performed by an user, receive its uuid.
   * @param uuid
   * @param successFuncion
   * @param config
   */
  listAudit(uuid: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsAudit.list) {
      this.apiRest.list(this.ROUTE_AUDIT, {uuid: uuid}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }
}
