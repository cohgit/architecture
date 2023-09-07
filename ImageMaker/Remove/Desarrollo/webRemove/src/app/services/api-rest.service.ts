import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SessionService } from '../helpers/session.service';
import { API_ROUTES } from '../helpers/api.rest.routes';

import { NgxUiLoaderService } from 'ngx-ui-loader';
import { UtilService } from "../helpers/util.service";
import {TranslateHelperService} from "../helpers/translate-helper.service";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiRestService {
  private apiRoute = '';

  /**
   * Manages system functionalities in a standard way for all system services
   * @param http
   * @param ngxLoader
   * @param sessionService
   * @param utilService
   * @param translateHelper
   */
  constructor(private http: HttpClient, private ngxLoader: NgxUiLoaderService,
              private sessionService: SessionService, private utilService: UtilService,
              public translateHelper: TranslateHelperService) {}

  /**
   * Gets all the complete data of a request based on a parameter (ID; uuid, etc).
   * @param path
   * @param params
   * @param successFuncion
   * @param config
   */
    get(path: string, params?: {}, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);


      if (config.ignoraToken || this.sessionService.isValidSession()) {
        const req = {method: 'GET', fullPath: this.fillPath(path), paramOptions: this.buildParamOptions(params, config.ignoraToken)};

        this.http.get(req.fullPath, req.paramOptions).subscribe(response => {
            this.handleSuccess(req, response, successFuncion, config);
          }, error => {
            this.handleError(req, error, config.errorFunction, config.taskId);
          }, () => {
            this.handleComplete(config.finalFunction);
          }
        );
      } else {
        this.redirectLogin();
      }
    }

  /**
   * fetch a list of records from a request
   * @param path
   * @param params
   * @param successFuncion
   * @param config
   */
    list(path: string, params?: {}, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      if (config.ignoraToken || this.sessionService.isValidSession()) {
        const req = {method: 'GET', fullPath: this.fillPath(path + '/list'), paramOptions: this.buildParamOptions(params)};

        this.http.get(req.fullPath, req.paramOptions).subscribe(response => {
            this.handleSuccess(req, response, successFuncion, config);
          }, error => {
            this.handleError(req, error, config.errorFunction, config.taskId);
          }, () => {
            this.handleComplete(config.finalFunction);
          }
        );
      } else {
        this.redirectLogin();
      }
    }

  /**
   * Allows you to save a record in the database
   * @param path
   * @param object
   * @param successFuncion
   * @param config
   */
    save(path: string, object: any, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      if (config.ignoraToken || this.sessionService.isValidSession()) {
        const req = {method: 'POST', fullPath: this.fillPath(path), object: object};

        this.http.post<any>(req.fullPath, req.object, this.buildJsonOptions(config)).subscribe(response => {
            this.handleSuccess(req, response, successFuncion, config);
          }, error => {
            this.handleError(req, error, config.errorFunction, config.taskId);
          }, () => {
            this.handleComplete(config.finalFunction);
          }
        )
      } else {
        this.redirectLogin();
      }
    }

  /**
   * Allows you to update a record in the database
   * @param path
   * @param object
   * @param successFuncion
   * @param config
   */
    update(path: string, object: any, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      if (config.ignoraToken || this.sessionService.isValidSession()) {
        const req = {method: 'PUT', fullPath: this.fillPath(path), object: object};

        this.http.put<any>(req.fullPath, req.object, this.buildJsonOptions(config)).subscribe(response => {
            this.handleSuccess(req, response, successFuncion, config);
          }, error => {
            this.handleError(req, error, config.errorFunction, config.taskId);
          }, () => {
            this.handleComplete(config.finalFunction);
          }
        )
      } else {
        this.redirectLogin();
      }
    }

  /**
   * Allows to logically delete a record in the database.
   * @param path
   * @param params
   * @param successFuncion
   * @param config
   */
    delete(path: string, params?: {}, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      if (config.ignoraToken || this.sessionService.isValidSession()) {
        const req = {method: 'DELETE', fullPath: this.fillPath(path), paramOptions: this.buildParamOptions(params, config.ignoraToken)};

        this.http.delete(req.fullPath, req.paramOptions).subscribe(response => {
            this.handleSuccess(req, response, successFuncion, config);
          }, error => {
            this.handleError(req, error, config.errorFunction, config.taskId);
          }, () => {
            this.handleComplete(config.finalFunction);
          }
        );
      } else {
        this.redirectLogin();
      }
    }

    loadFile(path: string, file: any, data: any, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      if (config.ignoraToken || this.sessionService.isValidSession()) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('data', JSON.stringify(data));

        const req = {method: 'POST', fullPath: this.fillFilePath(path), object: formData, paramOptions: this.buildMultipartOptions()};

        this.http.post(req.fullPath, req.object, req.paramOptions).subscribe(response => {
            this.handleSuccess(req, response, successFuncion, config);
          }, error => {
            this.handleError(req, error, config.errorFunction, config.taskId);
          }, () => {
            this.handleComplete(config.finalFunction);
          }
        );
      } else {
        this.redirectLogin();
      }
    }
    downloadFile(path: string, params?: {}, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      if (config.ignoraToken || this.sessionService.isValidSession()) {
        const req = {method: 'GET', fullPath: this.fillFilePath(path), paramOptions: this.buildParamBlobOptions(params)};

        this.http.get(req.fullPath, req.paramOptions).subscribe(response => {
            this.handleSuccessBlob(response, successFuncion, config);
          }, error => {
            this.handleError(req, error, config.errorFunction, config.taskId);
          }, () => {
            this.handleComplete(config.finalFunction);
          }
        );
      } else {
        this.redirectLogin();
      }
    }

  /**
   *  Manage the login of a user, create the session token
   * @param object
   * @param successFuncion
   * @param config
   */
    loginAdmin(object: any, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      const req = {method: 'POST', fullPath: this.fillPath(API_ROUTES.ADMIN.LOGIN), object: object, paramOptions: this.buildJsonOptions({ignoraToken: true})};

      this.http.post<any>(req.fullPath, req.object, req.paramOptions).subscribe(response => {
        this.handleSuccess(req, response, successFuncion, config);
      }, error => {
        this.handleError(req, error, config.errorFunction, config.taskId);
      }, () => {
        this.handleComplete(config.finalFunction);
      });
    }

  /**
   * Manages the login of a client, creates the session token
   * @param object
   * @param successFuncion
   * @param config
   */
    loginClient(object: any, successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      const req = {method: 'POST', fullPath: this.fillPath(API_ROUTES.CLIENT.LOGIN), object: object, paramOptions: this.buildJsonOptions({ignoraToken: true})};

      this.http.post<any>(req.fullPath, req.object, req.paramOptions).subscribe(response => {
        this.handleSuccess(req, response, successFuncion, config);
      }, error => {
        this.handleError(req, error, config.errorFunction, config.taskId);
      }, () => {
        this.handleComplete(config.finalFunction);
      });
    }

  /**
   * Log out of the system for user and client and delete the token created at the beginning of the session
   * @param successFuncion
   * @param config
   */
    logout(successFuncion?: Function, config?: ApiConfig) {
      config = config ? config : {};
      config.taskId = "" + new Date().getTime();
      this.wait(config.taskId);

      const req = {method: 'DELETE', fullPath: this.fillPath(API_ROUTES.LOGOUT), object: {}, paramOptions: this.buildJsonOptions(config)};

      this.http.delete<any>(req.fullPath, req.paramOptions).subscribe(response => {
        this.handleSuccess(req, response, successFuncion, config);
      }, error => {
        this.handleError(req, error, config.errorFunction, config.taskId);
      }, () => {
        this.handleComplete(config.finalFunction);
      });
    }

    /*validateCertificate(errorFunction: Function, config?: ApiConfig) {
      this.wait();
      config = config ? config : {};
      config.errorFunction = errorFunction;

      const req = {method: 'GET', fullPath: API_ROUTES.COMMON.CERTIFICATE, paramOptions: this.buildParamOptions({}, true)};
      req.paramOptions.responseType = 'text';

      this.http.get(req.fullPath, req.paramOptions).subscribe(() => {},
        error => {
          config && config.errorFunction ? config.errorFunction() : '';
        },
        () => {});
    }*/

  /**
   * Allows you to create the service route to manage back and front connections
   * @param path
   */
    fillPath(path: string) {
      return this.apiRoute + path;
    }

  /**
   *
   * @param path
   */
  fillFilePath(path: string): string{
      return this.apiRoute + path + '/file';
    }

  /**
   * Manage the correct answers of the services
   * @param request
   * @param response
   * @param successFuncion
   * @param config
   */
    handleSuccess(request: any, response: any, successFuncion: Function, config?: ApiConfig) {
      const validate = this.validateResponse(request, response as ResponseSD, config);

      if (validate !== null && successFuncion) {
        if (config) {
          this.sessionService.destroyBackup(config.storageParam);
        }

        successFuncion(validate);
      }

      this.unwait(config.taskId);
    }

  /**
   *
   * @param response
   * @param successFuncion
   * @param config
   */
    handleSuccessBlob(response: any, successFuncion: Function, config?: ApiConfig) {
      const url= window.URL.createObjectURL(response);
      window.open(url);

      if (successFuncion) {
        successFuncion(response);
      }

      this.unwait(config.taskId);
    }

  /**
   * Manage the error answers of the services
   * @param request
   * @param error
   * @param errorFunction
   * @param taskId
   */
    handleError(request: any, error: any, errorFunction: Function, taskId: string) {
      console.error('ERROR: ', error, request);

      if (error.error.code === 511) { // Token Inválido o Vencido
        this.utilService.showNotification(error.error.tag, 'warning')
        this.sessionService.destroySession();
      } else if (error.error.code === 403) { // Token Inválido o Vencido
        this.utilService.showNotification(error.error.tag, 'warning')
      } else {
        this.utilService.showNotification('error.server.conection', 'info');

        if (errorFunction) {
          errorFunction(error);
        }
      }

      this.unwait(taskId);
    }

  /**
   * Executes the function defined in finalFunction on the service, once the request has completed.
   * @param finalFunction
   */
    handleComplete(finalFunction: Function) {
      if (finalFunction) {
        finalFunction();
      }
    }

  /**
   * Validate the response received
   * @param req
   * @param response
   * @param config
   */
    validateResponse(req: any, response: ResponseSD, config?: ApiConfig): any {
        if (response.error) {
          return this.handleErrorMessage(req, response, config);
        } else if (response.warning) {
          return this.handleWarningMessage(req, response, config);
        } else if (response.success) {
          return this.handleSuccessMessage(req, response, config);
        }

        return null;
    }

  /**
   * Handle error messages in the browser console
   * @param req
   * @param response
   * @param config
   */
    handleErrorMessage(req: any, response: ResponseSD, config?: ApiConfig): any {
      if (environment.production) {
        console.error('ERROR: ', response);
      } else {
        console.error('ERROR: ', req, response);
      }

      if (config.errorMessageCode) {
        if (config.errorMessageComplex) {
          this.utilService.showComplexNotification(config.errorMessageCode, response.tag, 'danger');
        } else {
          this.utilService.showNotification(config.errorMessageCode, 'danger');
        }
      } else {
        this.utilService.showNotification(response.tag, 'danger');
      }
      config.errorFunction ? config.errorFunction(response) : null;

      return null;
    }

  /**
   * Handle warning messages in the browser console
   * @param req
   * @param response
   * @param config
   */
    handleWarningMessage(req: any, response: ResponseSD, config?: ApiConfig): any {
      console.warn('WARNING: ', req,  response);
      this.utilService.showNotification(response.tag, 'warning');
      return response.data;
    }

  /**
   * Handle success messages in the browser console
   * @param req
   * @param response
   * @param config
   */
    handleSuccessMessage(req: any, response: ResponseSD, config?: ApiConfig): any {
      if (!environment.production) console.log('Success: ', response);

      if (!config.ignoreSuccessMessage) {
        if (config.successMessageCode) {
          this.utilService.showNotification(config.successMessageCode, 'success');
        } else {
          if (req.method === 'POST') {
            this.utilService.showNotification('message.success.record.saved', 'success');
          } else if (req.method === 'PUT') {
            this.utilService.showNotification('message.success.record.updated', 'success');
          } else if (req.method === 'DELETE') {
            this.utilService.showNotification('message.success.record.updated', 'success');
          } else if (req.method === 'FILE') {
            this.utilService.showNotification('message.success.file.loaded', 'success');
          }
        }
      }

      return response.data === null || response.data === undefined ? {} : response.data;
    }

  /**
   *  Build the headers of a request
   * @param ignoreToken
   * @param _headers
   */
    buildHeaders(ignoreToken?: boolean, _headers?: any): HttpHeaders {
      let httpHeader = null;

      if (_headers) {
        httpHeader = new HttpHeaders(_headers);
      } else {
        httpHeader = new HttpHeaders();
      }

      if (!ignoreToken) {
        httpHeader = httpHeader.set('token', this.sessionService.getToken());
      }

      return httpHeader;
    }

  /**
   * Build the parameter options that are sent in the responses of the services, it can be any of the ApiConfig interface
   * @param params
   * @param ignoreToken
   * @private
   */
    private buildParamOptions(params?: {}, ignoreToken?: boolean): any {
      return {
        headers: this.buildHeaders(ignoreToken),
        params: params,
        withCredentials: environment.withCredentials
      }
    }

  /**
   * Build the parameter options that are sent in the responses of the services, it can be any of the ApiConfig interface
   * @param params
   * @param ignoreToken
   * @private
   */
    private buildParamBlobOptions(params?: {}, ignoreToken?: boolean): any {
      return {
        headers: this.buildHeaders(ignoreToken),
        params: params,
        responseType: 'blob'
      }
    }

  /**
   * Build the parameter options that are sent in the responses of the services, it can be any of the ApiConfig interface
   * @param config
   */
  buildJsonOptions(config: ApiConfig): any {
      let options = {
        headers: this.buildHeaders(config.ignoraToken, config.headers)
      };

      if (config.queryParams) options['params'] = config.queryParams;

      return options;
    }

  /**
   *
   * @param ignoreToken
   */
  buildMultipartOptions(ignoreToken?: boolean): any {
      let options = {
        headers: this.buildHeaders(ignoreToken)
      }
      options['Content-Type'] = 'multipart/form-data';
      return options;
    }

    redirectLogin() {
      this.sessionService.goLogin();
    }

    /**
     * Deprecado (Ahora se gestiona a traves del AppModule)
     * @param taskid
     */
    wait(taskid: string) {
      try {
        //this.ngxLoader.startLoader('external-loader', taskid);
      } catch {}
    }
    /**
     * Deprecado (Ahora se gestiona a traves del AppModule)
     * @param taskid
     */
    unwait(taskid: string) {
      try {
        //this.ngxLoader.stopLoader('external-loader', taskid);
      } catch {}
    }
  }

  class ResponseSD {
    code: number;
    tag: string;
    data: any;
    success: boolean;
    warning: boolean;
    error: boolean;
  }

  /**
   * Configuration behavior API Rest services interface.
   */
  export interface ApiConfig {
    headers?: any,
    queryParams?: any,
    errorFunction?: Function,
    finalFunction?: Function,
    successMessageCode?: string,
    ignoreSuccessMessage?: boolean,
    errorMessageCode?: string,
    errorMessageComplex?: false,
    waitingMessage?: string,
    storageParam?: string,
    ignoraToken?: boolean,
    taskId?: string
  }
