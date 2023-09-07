import {Injectable} from '@angular/core';
import {ApiConfig, ApiRestService} from './api-rest.service';
import {API_ROUTES} from '../helpers/api.rest.routes';
import {UtilService} from '../helpers/util.service';
import {SessionService} from '../helpers/session.service';
import {TranslateHelperService} from "../helpers/translate-helper.service";

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  private ROUTES = API_ROUTES.COMMON;

  /**
   * Common services for the entire system (they are invoked in various parts of the system,
   * so they have a standardization in this document, they are mainly used to fill all the system filters):
   * @param apiRest
   * @param util
   * @param session
   * @param translateHelper
   */
  constructor(private apiRest: ApiRestService, private util: UtilService,
              private session: SessionService,
              public translateHelper: TranslateHelperService) {
  }

  /**
   * List all search types for a scanner.
   * @param successFuncion
   * @param config
   */
  listSearchTypes(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.SEARCH_TYPES, {}, successFuncion, config);
  }

  /**
   * Get email from an account.
   * @param successFuncion
   * @param config
   */
   email(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.EMAIL, {}, successFuncion, config);
  }

  /**
   * List all devices for a scanner.
   * @param successFuncion
   * @param config
   */
  listDevices(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.DEVICES, {}, successFuncion, config);
  }

  /**
   * List all countries availables in the platform.
   * @param successFuncion
   * @param config
   */
  listCountries(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.COUNTRIES, {}, response => {
      if (successFuncion) {
        response = this.util.translateAndSort(response, 'list.country.', 'tag', 'name');
        successFuncion(response);
      }
    }, config);
  }

  /**
   * List all languages availables in the platform.
   * @param successFuncion
   * @param config
   */
  listLanguages(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.LANGUAGES, {}, response => {
      if (successFuncion) {
        // Descomentar si se hace internacionalizacion de idiomas para los 145 lenguajes.
        // response = this.util.translateAndSort(response, 'lang.', 'code', 'language');
        successFuncion(response);
      }
    }, config);
  }

  /**
   * List active tracking words for scanners.
   * @param successFuncion
   * @param config
   */
  listTrackingWords(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.TRACKING_WORDS, {}, response => {
      this.util.toTitleCaseList(response, 'word');

      if (successFuncion) successFuncion(response);
    }, config);
  }

  /**
   * List forbidden words for scanners.
   * @param successFuncion
   * @param config
   */
  listForbiddenWords(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.FORBIDDEN_WORDS, {}, successFuncion, config);
  }

  /**
   * List all feelings for tracking words or snippets
   * @param successFuncion
   * @param config
   */
  listFeelings(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.FEELINGS, {}, successFuncion, config);
  }

  /**
   * List all feelings for tracking words or snippets (But without NOT_APPLIED)
   * @param successFuncion
   * @param config
   */
  listFeelingsIgnoreNotApply(successFuncion?: Function, config?: ApiConfig) {
    this.listFeelings(response => {
      if (response) {
        response.forEach((value, index) => {
          if (value.NOT_APPLIED === true) response.splice(index, 1);
        })

        if (successFuncion) {
          successFuncion(response);
        }
      }
    }, config);
  }

  /**
   * List news types possible parameters for scanner.
   * @param successFuncion
   * @param config
   */
  listNewsTypes(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.NEWS_TYPES, {}, successFuncion, config);
  }

  /**
   * List image colours possible parameters for scanner.
   * @param successFuncion
   * @param config
   */
  listImagesColours(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.IMAGES_COLOURS, {}, successFuncion, config);
  }

  /**
   * List images types possible parameters for scanner.
   * @param successFuncion
   * @param config
   */
  listImagesTypes(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.IMAGES_TYPES, {}, successFuncion, config);
  }

  /**
   * List images size possible parameters for scanner.
   * @param successFuncion
   * @param config
   */
  listImagesSizes(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.IMAGES_SIZES, {}, successFuncion, config);
  }

  /**
   * List image usage rights possible parameters for scanner.
   * @param successFuncion
   * @param config
   */
  listImagesUsageRights(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.IMAGES_USAGE_RIGHTS, {}, successFuncion, config);
  }

  /**
   * List all notifications for user/client logged in.
   * @param total
   * @param successFuncion
   * @param config
   */
  listNotifications(total?: number, successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.NOTIFICATIONS, total ? {total: total} : {}, successFuncion, config);
  }

  /**
   * Switch 'readed' attribute from a list of notifications
   * @param notifications
   * @param readed true => 'readed' or false => 'not readed'
   * @param successFuncion
   * @param config
   */
  markNotificationsAsReaded(notifications: any[], readed: boolean, successFuncion?: Function, config?: ApiConfig) {
    const body = this.buildMarkNotificationBody(notifications, 'readed', readed);
    config = config ? config : {};
    config.ignoreSuccessMessage = true;

    if (body != null) {
      this.apiRest.update(this.ROUTES.NOTIFICATIONS, body, successFuncion, config);
    } else {
      console.error('Error: access config invalid...');
    }
  }

  /**
   * Switch 'hidden' attribute from a list of notifications
   * @param notifications
   * @param hidden true => 'hidden' or false => 'not hidden'
   * @param successFuncion
   * @param config
   */
  markNotificationsAsHidden(notifications: any[], hidden: boolean, successFuncion?: Function, config?: ApiConfig) {
    const body = this.buildMarkNotificationBody(notifications, 'hidden', hidden);
    config = config ? config : {};
    config.ignoreSuccessMessage = true;

    if (body != null) {
      this.apiRest.update(this.ROUTES.NOTIFICATIONS, body, successFuncion, config);
    } else {
      console.error('Error: access config invalid...');
    }
  }

  /**
   * Prepare body object for switch 'readed' or 'hidden' service.
   * @param notifications
   * @param field
   * @param value
   * @returns
   */
  private buildMarkNotificationBody(notifications: any[], field: string, value: boolean): any {
    const accessConfig = this.session.getAccessConfig();

    if (accessConfig.CLIENT) {
      return {
        field: field,
        value: value,
        clientsAlerts: notifications
      };
    } else if (accessConfig.ADMIN) {
      return {
        field: field,
        value: value,
        userAlerts: notifications
      }
    }
    return null;
  }

  /**
   * List the clients to fill the plan suggestion select
   * @param successFuncion
   * @param config
   */
  listClients(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.CLIENTS, {}, successFuncion, config);
  }

  /**
   * Recives an email and send a email to verify cliente
   * @param email
   * @param successFuncion
   * @param config
   */
  verfyEmailClient(email: string, successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.CLIENTS_EMAIL, {email: email}, successFuncion, config);
  }

  /**
   * List the plans available for a client in the sidebar
   * @param successFuncion
   * @param config
   */
  listClientsPlans(successFuncion?: Function, config?: ApiConfig) {
    const accessConfig = this.session.getAccessConfig();
    if (accessConfig.CLIENT) {
      this.apiRest.get(this.ROUTES.CLIENTS_PLANS, {}, successFuncion, config);
    }
  }

  /**
   * List the available plans in a select
   * @param successFuncion
   * @param _config
   */
  listPlans(successFuncion?: Function, _config?: ApiConfig) {
    let config = _config ? _config : {};
    config.ignoraToken = true;
    this.apiRest.get(this.ROUTES.PLANS, {}, successFuncion, config);
  }

  /**
   * List the available profiles in a select
   * @param successFuncion
   * @param config
   */
  listProfiles(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.PROFILES, {}, successFuncion, config);
  }

  /**
   * List the types of impulses available in a select
   * @param successFuncion
   * @param config
   */
  listImpulseTypes(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.IMPULSE_TYPES, {}, successFuncion, config);
  }

  /**
   * List the types of pulse states available in a select
   * @param successFuncion
   * @param config
   */
  listImpulseStatus(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.IMPULSE_STATUS, {}, successFuncion, config);
  }

  /**
   * List the types of impulse concepts available in a select
   * @param successFuncion
   * @param config
   */
  listImpulseConcepts(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.IMPULSE_CONCEPTS, {}, successFuncion, config);
  }

  /**
   *
   * Search Google suggestions and with a keyword, country and language, return a list of suggestions
   * @param q
   * @param hl
   * @param gl
   * @param successFuncion
   * @param config
   */
  googleSuggest(q: string, hl: string, gl: string, successFuncion?: Function, config?: ApiConfig) {
    const noSpaces = q.replace(/ /g, '%20');
    this.apiRest.get(this.ROUTES.GOOGLE_SUGGEST, {q: noSpaces, hl: hl, gl: gl}, successFuncion, config);
  }

  /**
   * List the types of deindexation states available in a select
   * @param successFuncion
   * @param config
   */
  listDeindexationStatus(successFuncion?: Function, config?: ApiConfig) {
    this.apiRest.get(this.ROUTES.DEINDEXATION_STATUS, {}, successFuncion, config);
  }

  /*validateCertificate(errorFunction: Function) {
    this.apiRest.validateCertificate(errorFunction);
  }*/
}
