import { Injectable } from '@angular/core';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';
import { ApiRestService, ApiConfig } from './api-rest.service';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  public permissions: IPermissionService;
  public permissionsAudit: IPermissionService;
  public permissionsPayment: IPermissionService;
  public permissionsAttempt: IPermissionService;

  private ROUTES = API_ROUTES.ADMIN.CLIENTS;
  private ROUTES_PAYMENT = API_ROUTES.ADMIN.CLIENTS_PAYMENT;
  private ROUTE_AUDIT = API_ROUTES.ADMIN.CLIENTS_AUDIT;
  private ROUTE_ATTEMPT = API_ROUTES.ADMIN.CLIENTS_ATTEMPT;

  /**
   * It manages all the data of the client of the system, under the profile of the admin.
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("CLIENTS");
    this.permissionsAudit = this.session.accountPermissions("CLIENTS_AUDIT");
    this.permissionsAttempt = this.session.accountPermissions("CLIENTS_ATTEMPT");
    this.permissionsPayment = this.session.accountPermissions("CLIENTS_PAYMENT");
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
   * Brings the list of all the clients registered in the system, only accessible from the admin panel
   * @param successFuncion
   * @param config
   */
  list(successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.list) {
      this.apiRest.list(this.ROUTES, {}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Stores the data of a client, receives the entire client object
   * @param plan
   * @param successFuncion
   * @param config
   */
  save(plan: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES, plan, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Update the data of a client, receives the entire client object
   * @param plan
   * @param successFuncion
   * @param config
   */
  update(plan: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES, plan, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
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
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Admin validates the payment of a client.
   * @param payment
   * @param successFuncion
   * @param config
   */

  confirmPayment(payment: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsPayment.update) {
      this.apiRest.update(this.ROUTES_PAYMENT, payment, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Get the log list of actions performed by a client, receive its uuid.
   * @param uuid_client
   * @param successFuncion
   * @param config
   */
  listAudit(uuid_client: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsAudit.list) {
      this.apiRest.list(this.ROUTE_AUDIT, {uuid_client: uuid_client}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  listSuscriptionAttempts(successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsAttempt.list) {
      this.apiRest.list(this.ROUTE_ATTEMPT, {}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }
}
