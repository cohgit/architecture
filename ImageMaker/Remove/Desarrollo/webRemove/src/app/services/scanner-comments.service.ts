import { Injectable } from '@angular/core';
import {IPermissionService} from "../interfaces/IPermissionsService";
import {API_ROUTES} from "../helpers/api.rest.routes";
import {ApiConfig, ApiRestService} from "./api-rest.service";
import {SessionService} from "../helpers/session.service";

@Injectable({
  providedIn: 'root'
})
export class ScannerCommentsService {

  public permissions: IPermissionService;
  public permissionsAudit: IPermissionService;
  private ROUTES = API_ROUTES.ADMIN.SCANNER.COMMENT;
  private ROUTES_CLIENT = API_ROUTES.CLIENT.SCANNER.COMMENT;

  /**
   * Manages all the functionalities of the SCANNER COMMENT module (administrator)
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("SCANNER_COMMENT");

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
    }  else if (accessConfig.CLIENT ) {
      this.apiRest.get(this.ROUTES_CLIENT, {"uuid": uuid}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }


  /**
   * Get the log list of comment performed in a scanner, receive its uuid.
   * @param id
   * @param successFuncion
   * @param config
   */
  listComments(id: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();
    if (accessConfig.ADMIN && this.permissions.list) {
      this.apiRest.list(this.ROUTES, {id_scanner: id}, successFuncion, config);
    } else if (accessConfig.CLIENT ) {
      this.apiRest.list(this.ROUTES_CLIENT, {id_scanner: id}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Stores the data of a record
   * @param comment
   * @param successFuncion
   * @param config
   */
  save(comment: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES, comment, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }



}
