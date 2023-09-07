import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';

@Injectable({
  providedIn: 'root'
})
export class DeindexationService {
  public permissions: IPermissionService;
  public permissionsUrl: IPermissionService;
  public permissionsStatus: IPermissionService;
  public permissionsHistoric: IPermissionService;

  private ROUTES_ADMIN = API_ROUTES.ADMIN.DEINDEXATION;
  private ROUTES_CLIENT = API_ROUTES.CLIENT.DEINDEXATION;

  /**
   * Manage all the functionalities of the deindexation service
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("DEINDEXATION");
    this.permissionsUrl = this.session.accountPermissions("DEINDEXATION_URL");
    this.permissionsStatus = this.session.accountPermissions("DEINDEXATION_STATUS");
    this.permissionsHistoric = this.session.accountPermissions("DEINDEXATION_HISTORIC");
  }

  /**
   *
   * Gets all the data of a record based on their uuid
   * @param uuidDeindexation
   * @param uuidClient
   * @param successFuncion
   * @param config
   */
  get(uuidDeindexation?: string, uuidClient?: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.read) {
      this.apiRest.get(this.ROUTES_ADMIN.BASE, uuidDeindexation ? {"uuid": uuidDeindexation, "uuid_client": uuidClient} : {"uuid_client": uuidClient}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES_CLIENT.BASE, uuidDeindexation ? {"uuid": uuidDeindexation} : {}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Gets a list of all the records registered in the system
   * @param uuid_client
   * @param successFuncion
   * @param config
   */
  list(uuid_client?: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.list) {
      this.apiRest.list(this.ROUTES_ADMIN.BASE, {"uuid_client": uuid_client}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.list(this.ROUTES_CLIENT.BASE, {}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Stores the data of a record
   * @param deindexation
   * @param successFuncion
   * @param config
   */
  save(deindexation: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES_ADMIN.BASE, deindexation, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT.BASE, deindexation, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param deindexation
   * @param successFuncion
   * @param config
   */
  update(deindexation: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES_ADMIN.BASE, deindexation, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.update(this.ROUTES_CLIENT.BASE, deindexation, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }
  /*loadFile(file: any, data: any, successFuncion?: Function, config?: ApiConfig) {
    if (this.accessConfig.ADMIN && this.permissions.file) {
      this.apiRest.loadFile(this.ROUTES_ADMIN.BASE, file, data, successFuncion, config);
    } else if (this.accessConfig.CLIENT) {
      this.apiRest.loadFile(this.ROUTES_CLIENT.BASE, file, data, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.accessConfig, this.permissions);
    }
  }
  downloadFile(id: number, successFuncion?: Function, config?: ApiConfig) {
    if (this.accessConfig.ADMIN && this.permissions.file) {
      this.apiRest.downloadFile(this.ROUTES_ADMIN.BASE, {id: id}, successFuncion, config);
    } else if (this.accessConfig.CLIENT) {
      this.apiRest.downloadFile(this.ROUTES_CLIENT.BASE, {id: id}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.accessConfig, this.permissions);
    }
  }
  deleteFile(data: any, successFuncion?: Function, _config?: ApiConfig) {
    let config = _config ? _config : {};
    config.ignoreSuccessMessage = true;

    if (this.accessConfig.ADMIN && this.permissions.file) {
      this.apiRest.loadFile(this.ROUTES_ADMIN.BASE, null, data, successFuncion, config);
    } else if (this.accessConfig.CLIENT) {
      this.apiRest.loadFile(this.ROUTES_CLIENT.BASE, null, data, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", this.accessConfig, this.permissions);
    }
  }*/

  /**
   * brings all the historical information of a deindexation
   * @param uuidDeindexation
   * @param successFuncion
   * @param config
   */
  getHistoric(uuidDeindexation?: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsHistoric.read) {
      this.apiRest.get(this.ROUTES_ADMIN.HISTORIC, {"uuid": uuidDeindexation}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES_CLIENT.HISTORIC, {"uuid": uuidDeindexation}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the status from unindexing to processing
   * @param id
   * @param successFuncion
   * @param config
   */
  markAsProcessing(id: number, successFuncion?: Function, config?: ApiConfig) {
    this.updateStatus(id, 'processing', '', '', successFuncion, config);
  }

  /**
   * Update the status of a URL
   * @param url
   * @param successFuncion
   * @param config
   */
  refreshUrlStatus(url: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsUrl.update) {
      this.apiRest.update(this.ROUTES_ADMIN.URL, url, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update deindex status to approved
   * @param id
   * @param successFuncion
   * @param config
   */
  markAsApproved(id: number, successFuncion?: Function, config?: ApiConfig) {
    this.updateStatus(id, 'approved', '', '', successFuncion, config);
  }

  /**
   * Update deindex status to rejected
   * @param id
   * @param successFuncion
   * @param config
   */
  markAsRejected(id: number, successFuncion?: Function, config?: ApiConfig) {
    this.updateStatus(id, 'rejected', '', '', successFuncion, config);
  }

  /**
   * Update deindex status to deleted
   * @param id
   * @param successFuncion
   * @param config
   */
  markAsDeleted(id: number, successFuncion?: Function, config?: ApiConfig) {
    this.updateStatus(id, 'deleted', '', '', successFuncion, config);
  }

  /**
   * Functionality to execute the status change
   * @param id
   * @param status
   * @param comment
   * @param trackingCode
   * @param successFuncion
   * @param config
   * @private
   */
  private updateStatus(id: number, status: string, comment: string, trackingCode: string, successFuncion?: Function, config?: ApiConfig) {
    let body = {
      idDeindexation: id,
      status: status,
      comment: comment,
      trackingCode: trackingCode
    };
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissionsStatus.save) {
      this.apiRest.save(this.ROUTES_ADMIN.STATUS, body, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT.STATUS, body, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }
}
