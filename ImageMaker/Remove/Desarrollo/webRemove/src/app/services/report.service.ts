import { Injectable } from '@angular/core';
import { ApiRestService, ApiConfig } from './api-rest.service';
import { API_ROUTES } from '../helpers/api.rest.routes';
import { SessionService } from '../helpers/session.service';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private ROUTES_ADMIN = API_ROUTES.ADMIN.REPORTS;
  private ROUTES_CLIENT = API_ROUTES.CLIENT.REPORTS;

  /**
   * execute the call to the backend to generate the PDF report
   * @param apiRest
   * @param session
   */
  constructor(private apiRest: ApiRestService, private session: SessionService) { }

  /**
   * Assign the parameters to the functionality of generating the report
   * @param parameters
   * @param successFuncion
   * @param config
   */
  public generateScannerReport(parameters: any, successFuncion?: Function, config?: ApiConfig) {
    let reportParams = {
      code: 'escaner_dashboard',
      email: parameters.email,
      toEmail: true,
      toNotification: false,
      parameters: parameters
    } 

    this.generate(reportParams, successFuncion, config);
  }

  /**
   * run the service that generates the report
   * @param parameters
   * @param successFuncion
   * @param config
   * @private
   */
  private generate(parameters: any, successFuncion?: Function, config?: ApiConfig) {
    let accessConfig = this.session.getAccessConfig();
    config = config ? config : {};
    config.successMessageCode = 'message.success.generating.report';

    if (accessConfig.ADMIN) {
      this.apiRest.save(this.ROUTES_ADMIN, parameters, successFuncion, config);
    } else if (accessConfig.CLIENT) {
      this.apiRest.save(this.ROUTES_CLIENT, parameters, successFuncion, config);
    }
  }
}
