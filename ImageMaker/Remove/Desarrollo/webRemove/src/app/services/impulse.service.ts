import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';
import { IPermissionService } from '../interfaces/IPermissionsService';

@Injectable({
  providedIn: 'root'
})
export class ImpulseService {
  public permissions: IPermissionService;
  public permissions_approve: IPermissionService;
  public permissions_publish: IPermissionService;
  private ROUTES_ADMIN = API_ROUTES.ADMIN.IMPULSE;
  private ROUTES_CLIENT = API_ROUTES.CLIENT.IMPULSE;

  /**
   * Manages all the functionalities of the boost module (transforms)
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) {
    this.permissions = this.session.accountPermissions("IMPULSE");
    this.permissions_approve = this.session.accountPermissions("IMPULSE_APPROVE");
    this.permissions_publish = this.session.accountPermissions("IMPULSE_PUBLISH");
  }

  /**
   * Gets a list of all the records registered in the system
   * @param uuid_scanner
   * @param successFuncion
   * @param config
   */
  list(uuid_scanner: string, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.list) {
      this.apiRest.list(this.ROUTES_ADMIN.BASE, {"uuid_scanner": uuid_scanner}, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.list(this.ROUTES_CLIENT.BASE, {"uuid_scanner": uuid_scanner}, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Stores the data of a record
   * @param uuid_scanner
   * @param impulse
   * @param successFuncion
   * @param config
   */
  save(uuid_scanner: string, impulse: any, successFuncion?: Function, config?: ApiConfig) {
    impulse.uuidScanner = uuid_scanner;
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.save) {
      this.apiRest.save(this.ROUTES_ADMIN.BASE, impulse, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT.BASE, impulse, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * Update the data of a record
   * @param impulse
   * @param successFuncion
   * @param config
   */
  update(impulse: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions.update) {
      this.apiRest.update(this.ROUTES_ADMIN.BASE, impulse, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.update(this.ROUTES_CLIENT.BASE, impulse, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions);
    }
  }

  /**
   * based on the ID of the impulse requests the approval of the impulse
   * @param idImpulseContent
   * @param commentary
   * @param successFuncion
   * @param config
   */
  askApproval(idImpulseContent: number, commentary: string, successFuncion?: Function, config?: ApiConfig) {
    let observation = {
      status: 'to_be_approved',
      id_scanner_impulse_content: idImpulseContent,
      commentary: commentary
    }

    this.sendObservation(observation, successFuncion, config);
  }

  /**
   * Prior to the approval request of an impulse, it approves the request based on an ID
   * @param idImpulseContent
   * @param commentary
   * @param successFuncion
   * @param config
   */
  approve(idImpulseContent: number, commentary: string, successFuncion?: Function, config?: ApiConfig) {
    let observation = {
      status: 'approved',
      id_scanner_impulse_content: idImpulseContent,
      commentary: commentary
    }

    this.sendObservation(observation, successFuncion, config);
  }

  /**
   * Prior to the approval request of an impulse, it reject the request based on an ID
   * @param idImpulseContent
   * @param commentary
   * @param successFuncion
   * @param config
   */
  reject(idImpulseContent: number, commentary: string, successFuncion?: Function, config?: ApiConfig) {
    let observation = {
      status: 'rejected',
      id_scanner_impulse_content: idImpulseContent,
      commentary: commentary
    }

    this.sendObservation(observation, successFuncion, config);
  }

  /**
   * Sends an observation in case of rejection of an approval request
   * @param impulseObservation
   * @param successFuncion
   * @param config
   * @private
   */
  private sendObservation(impulseObservation: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions_approve.save) {
      this.apiRest.save(this.ROUTES_ADMIN.APPROVE, impulseObservation, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT.APPROVE, impulseObservation, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig, this.permissions_approve);
    }
  }

  /**
   * Update the publication date of a boost
   * @param impulse
   * @param successFuncion
   * @param config
   */
  updateEstimatedPublish(impulse: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions_publish.save) {
      this.apiRest.save(this.ROUTES_ADMIN.PUBLISH, impulse, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }

  /**
   * Post a boost
   * @param impulse
   * @param successFuncion
   * @param config
   */
  publish(impulse: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();

    if (accessConfig.ADMIN && this.permissions_publish.update) {
      this.apiRest.update(this.ROUTES_ADMIN.PUBLISH, impulse, successFuncion, config);
    } else {
      console.error("INVALID ACCESS CONFIG: ", accessConfig);
    }
  }
}
