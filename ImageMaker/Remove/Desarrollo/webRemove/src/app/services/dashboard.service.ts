import { Injectable } from '@angular/core';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';
import { ApiRestService, ApiConfig } from './api-rest.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  public permissions: IPermissionService;
  private ROUTES = API_ROUTES.ADMIN.DASHBOARD;

  /**
   * Bring the data from the dashboard to the admin panel
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("DASHBOARD");
  }

  /**
   * Bring the data from the dashboard to the admin panel
   * @param filterMonthYear
   * @param successFuncion
   * @param config
   */
  get(filterMonthYear?: number, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(this.ROUTES, {"filterMonthYear": filterMonthYear}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }
}
