'use strict';
import  {environment} from "../../environments/environment";
export const useSSL = environment.ssl;
const BASE = (useSSL ? 'https://' : 'http://') + environment.url;

// Routes to connect from with backend

export const API_ROUTES = {
  ADMIN: {
    LOGIN: BASE + '/admin/login',
    CONFIG: BASE + '/admin/config',
    SCANNER: {
      BASE: BASE + '/admin/scanner',
      SNIPPET: BASE + '/admin/scanner/snippet',
      COMMENT: BASE + '/admin/scanner/comment',
      ALERT: BASE + '/admin/scanner/alerts',
    },
    DEINDEXATION: {
      BASE: BASE + '/admin/deindexation',
      URL: BASE + '/admin/deindexation/url',
      STATUS: BASE + '/admin/deindexation/status',
      HISTORIC: BASE + '/admin/deindexation/historic'
    },
    DASHBOARD: BASE + '/admin/dashboard',
    IMPULSE: {
      BASE: BASE + '/admin/impulse',
      PUBLISH: BASE + '/admin/impulse/publish',
      APPROVE: BASE + '/admin/impulse/approve',
      VARIABLE: BASE + '/admin/impulse/variable'
    },
    USERS: BASE + '/admin/users',
    USERS_AUDIT: BASE + '/admin/users/audit',

    CLIENTS: BASE + '/admin/clients',
    CLIENTS_PAYMENT: BASE + '/admin/clients/payment',
    CLIENTS_AUDIT: BASE + '/admin/clients/audit',
    CLIENTS_ATTEMPT: BASE + '/admin/clients/attempt',
    PLANS: BASE + '/admin/plans',
    PLANS_SUGGESTIONS: BASE + '/admin/plans/suggestions',
    TRACKING_WORDS: BASE + '/admin/trackingwords',
    FORBIDDEN_WORDS: BASE + '/admin/forbiddenwords',
    ALERTS: BASE + '/admin/alerts',
    REPORTS: BASE + '/admin/reports'
  },
  CLIENT: {
    LOGIN: BASE + '/client/login',
    CONFIG: BASE + '/client/config',
    SCANNER: {
      BASE: BASE + '/client/scanner',
      SNIPPET: BASE + '/client/scanner/snippet',
      COMMENT: BASE + '/client/scanner/comment',
      ALERT: BASE + '/client/scanner/alerts',
    },
    DEINDEXATION: {
      BASE: BASE + '/client/deindexation',
      STATUS: BASE + '/client/deindexation/status',
      HISTORIC: BASE + '/client/deindexation/historic'
    },
    DASHBOARD: BASE + '/client/dashboard',
    IMPULSE: {
      BASE: BASE + '/client/impulse',
      // PUBLISH: BASE + '/client/impulse/publish',
      APPROVE: BASE + '/client/impulse/approve'
    },
    REPORTS: BASE + '/client/reports'
  },
  COMMON: {
    EMAIL: BASE + '/common/email',
    SEARCH_TYPES: BASE + '/common/searchtypes',
    DEVICES: BASE + '/common/devices',
    COUNTRIES: BASE + '/common/countries',
    LANGUAGES: BASE + '/common/languages',
    FEELINGS: BASE + '/common/feelings',
    TRACKING_WORDS: BASE + '/common/trackingWords',
    FORBIDDEN_WORDS: BASE + '/common/forbiddenWords',
    NEWS_TYPES: BASE + '/common/news/types',
    IMAGES_COLOURS: BASE + '/common/images/colours',
    IMAGES_TYPES: BASE + '/common/images/types',
    IMAGES_SIZES: BASE + '/common/images/sizes',
    IMAGES_USAGE_RIGHTS: BASE + '/common/images/usagerights',
    NOTIFICATIONS: BASE + '/common/notifications',
    CLIENTS: BASE + '/common/clients',
    CLIENTS_EMAIL: BASE + '/common/clients/email',
    CLIENTS_PLANS: BASE + '/common/clients/plans',
    PLANS: BASE + '/common/plans',
    IMPULSE_TYPES: BASE + '/common/impulse/types',
    IMPULSE_STATUS: BASE + '/common/impulse/status',
    IMPULSE_CONCEPTS: BASE + '/common/impulse/concepts',
    PROFILES: BASE + '/common/profiles',
    GOOGLE_SUGGEST: BASE + '/common/google/suggest',
    DEINDEXATION_STATUS: BASE + '/common/deindexation/status',
  },
  XTERNAL: {
    SUSCRIPTION: BASE + '/xternal/suscriptions',
    ADMIN_PASSWORD: BASE + '/xternal/user/password',
    CLIENT_PASSWORD: BASE + '/xternal/client/password',
    COMMON: {
      PLAN: BASE + '/xternal/common/plan',
      PLANS: BASE + '/xternal/common/plans',
      CONTACTS: BASE + '/xternal/common/contacts',
      CERTIFICATE: BASE + '/xternal/common/certificate',
      PING: BASE + '/xternal/common/ping'
    }
  },
  LOGOUT: BASE + '/logout'
};
