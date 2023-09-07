import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';

@Injectable({
  providedIn: 'root'
})
export class ScannerService {
  public permissions: IPermissionService;
  public permissionsSnippet: IPermissionService;
  private ROUTES_ADMIN = API_ROUTES.ADMIN.SCANNER;
  private ROUTES_CLIENT = API_ROUTES.CLIENT.SCANNER;

  /**
   * Manages the main actions of a scanner
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("SCANNER");
    this.permissionsSnippet = this.session.accountPermissions("SCANNER_SNIPPET");
  }

  /**
   * Gets a list of all the records registered in the system
   * @param type
   * @param uuid_client
   * @param successFuncion
   * @param config
   */
  list(type: string, uuid_client?: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.list) {
      this.apiRest.list(this.ROUTES_ADMIN.BASE, {"uuid_client": uuid_client, "scanner_type": type}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.list(this.ROUTES_CLIENT.BASE, {"scanner_type": type}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Bring all the data of a record based on their uuid
   * @param uuid
   * @param type
   * @param successFuncion
   * @param config
   */
  get(uuid: string, type?: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(this.ROUTES_ADMIN.BASE, {"uuid": uuid, "scanner_type": type}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES_CLIENT.BASE, {"uuid": uuid, "scanner_type": type}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Bring the base configuration of the scanner
   * @param type
   * @param uuid_client
   * @param successFuncion
   * @param config
   */
  getDefault(type: string, uuid_client?: string, successFuncion?: Function, config?: ApiConfig) {
    config = config ? config : {};
    config.ignoreSuccessMessage = true;
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(this.ROUTES_ADMIN.BASE, {"uuid_client": uuid_client, "scanner_type": type}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES_CLIENT.BASE, {"scanner_type": type}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Store the data of a record
   * @param scanner
   * @param successFuncion
   * @param config
   */
  save(scanner: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES_ADMIN.BASE, scanner, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT.BASE, scanner, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param scanner
   * @param successFuncion
   * @param config
   */
  update(scanner: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES_ADMIN.BASE, scanner, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.update(this.ROUTES_CLIENT.BASE, scanner, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Delete a record logically from the database, key param: uuid
   * @param uuid
   * @param successFuncion
   * @param config
   */
  delete(uuid: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.delete) {
      this.apiRest.delete(this.ROUTES_ADMIN.BASE, {"uuid": uuid, "operation": "delete"}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Pause or run a specific paused scanner
   * @param uuid
   * @param successFuncion
   * @param config
   */
  pauseResume(uuid: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.delete) {
      this.apiRest.delete(this.ROUTES_ADMIN.BASE, {"uuid": uuid, "operation": "pause"}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the sentiments of the snippets in results.
   * @param snippets
   * @param successFuncion
   * @param config
   */
  updateSnippet(snippets: any[], successFuncion?: Function, config?: ApiConfig) {
    let request = {};

    if (snippets) {
      snippets.forEach(snip => {
        if (request[snip.type] === undefined) {
          request[snip.type] = [];
        }

        request[snip.type].push(snip);
      })
    }
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsSnippet.update) {
      this.apiRest.update(this.ROUTES_ADMIN.SNIPPET, request, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.update(this.ROUTES_CLIENT.SNIPPET, request, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissionsSnippet);
    }
  }
}
