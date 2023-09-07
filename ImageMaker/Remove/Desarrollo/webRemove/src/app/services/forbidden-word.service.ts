import {Injectable} from '@angular/core';
import {API_ROUTES} from '../helpers/api.rest.routes';
import {SessionService} from '../helpers/session.service';
import {IPermissionService} from '../interfaces/IPermissionsService';
import {ApiConfig, ApiRestService} from './api-rest.service';

@Injectable({
  providedIn: 'root'
})
export class ForbiddenWordService {
  public permissions: IPermissionService;
  private ROUTES_ADMIN = API_ROUTES.ADMIN.FORBIDDEN_WORDS;

  /**
   * Manage all the functionality of the banned words module in the admin system menu.
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions('FORBIDDEN_WORDS');
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
   * Delete a record logically from the database, key param: id
   * @param id
   * @param successFuncion
   * @param config
   */
  delete(id: number, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.delete) {
      this.apiRest.delete(this.ROUTES_ADMIN, {id: id}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }
}
