import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';

@Injectable({
  providedIn: 'root'
})
export class TrackingWordService {
  public permissions: IPermissionService;
  private ROUTES_ADMIN = API_ROUTES.ADMIN.TRACKING_WORDS;

  /**
   * Manage all the functionalities of the keywords module (administrator)
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("TRACKING_WORDS");
  }

  /**
   * Gets a list of all the records registered in the system
   * @param successFuncion
   * @param config
   */
  list(successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.list) {
      this.apiRest.list(this.ROUTES_ADMIN, {}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Stores the data of a record
   * @param trackingWord
   * @param successFuncion
   * @param config
   */
  save(trackingWord: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES_ADMIN, trackingWord, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param trackingWord
   * @param successFuncion
   * @param config
   */
  update(trackingWord: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES_ADMIN, trackingWord, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Upload a keyword file
   * @param twList
   * @param successFuncion
   * @param config
   */
  loadFile(twList: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.file) {
      this.apiRest.save(this.ROUTES_ADMIN+"/file", {list:twList}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }
}
