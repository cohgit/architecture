import { Injectable } from '@angular/core';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';
import { ApiRestService, ApiConfig } from './api-rest.service';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  public permissions: IPermissionService;
  public permissionsSuggestions: IPermissionService;
  private ROUTES_ADMIN = API_ROUTES.ADMIN.SCANNER.ALERT;
  private ROUTES_CLIENT = API_ROUTES.CLIENT.SCANNER.ALERT;

  /**
   * Manage all the functionalities of the plans module (administrator)
   * @param apiRest
   * @param session
   */
  constructor(
    private apiRest: ApiRestService,
    private session: SessionService
  ) {
    // TODO agregar los permisos
    this.permissions = this.session.accountPermissions('SCANNER_ALERTS');
  }

  /**
   * Bring all the data of a record based on their id
   * @param id
   * @param successFuncion
   * @param config
   */
  get(id: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(
        this.ROUTES_ADMIN,
        { id_scanner: id },
        successFuncion,
        config
      );
    } else if (accessConfig.CLIENT) {
      this.apiRest.get(
        this.ROUTES_CLIENT,
        { id_scanner: id },
        successFuncion,
        config
      );
    } else {
      console.error('INVALID ACCESS CONFIG: ', accessConfig, this.permissions);
    }
  }
  /**
   * Store the data of a record
   * @param scanner
   * @param successFuncion
   * @param config
   */
  save(alert: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES_ADMIN, alert, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT, alert, successFuncion, config);
    } else {
      console.error('INVALID ACCESS CONFIG: ', accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param scanner
   * @param successFuncion
   * @param config
   */
  update(alert: any, successFuncion?: Function, config?: ApiConfig) {
    console.log('update', alert);
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES_ADMIN, alert, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.update(this.ROUTES_CLIENT, alert, successFuncion, config);
    } else {
      console.error('INVALID ACCESS CONFIG: ', accessConfig, this.permissions);
    }
  }
}
