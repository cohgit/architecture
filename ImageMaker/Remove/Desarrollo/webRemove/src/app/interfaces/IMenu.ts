// TEMPLATE OF MAIN SYSTEM MENU
export const MAIN_MENU = {
  HOME: {
    description: 'menu.home',
    link: '/module/home',
    icon: 'las la-home',
    id: 'home',
    class: 'nav-link ',
    child: [],
  },
  SCANNER: {
    description: 'menu.services',
    link: '',
    icon: 'las la-briefcase',
    id: 'submenu1',
    class: 'nav-link  dropdown-toggle',
    child: [],
  },
  ONE_SHOT: {
    description: 'menu.one.shot',
    link: '/module/scanner/global_view/one_shot',
    icon: 'las la-camera',
  },
  MONITOR: {
    description: 'menu.recurrent',
    link: '/module/scanner/global_view/monitor',
    icon: 'las la-layer-group',
  },
  TRANSFORM: {
    description: 'menu.transform',
    link: '/module/scanner/global_view/transform',
    icon: 'las la-sync',
  },
  USERS: {
    description: 'menu.users',
    link: '/module/users',
    icon: 'las la-users',
    id: 'users',
    class: 'nav-link ',
    child: [],
  },
  PLANS: {
    description: 'menu.plans',
    link: '/module/plans',
    icon: 'las la-briefcase',
    id: 'plans',
    class: 'nav-link ',
    child: [],
  },
  CLIENTS: {
    description: 'menu.clients',
    link: '/module/clients',
    icon: 'las la-id-card',
    id: 'clients',
    class: 'nav-link ',
    child: [],
  },
  SYSTEM: {
    description: 'menu.system',
    link: '',
    icon: 'las la-globe',
    id: 'submenu1',
    class: 'nav-link  dropdown-toggle',
    child: [
      {
        key: 'TRACKING_WORDS',
        description: 'menu.system.trackingword',
        link: '/module/trackingword',
        icon: 'las la-list',
      },
      {
        key: 'FORBIDDEN_WORDS',
        description: 'menu.system.forbiddenwords',
        link: '/module/forbiddenword',
        icon: 'las la-skull-crossbones',
      },
    ],
  },
  DASHBOARD: {
    description: 'menu.statistics',
    link: '/module/dashboard',
    icon: 'las la-calculator',
    id: 'dashboard',
    class: 'nav-link ',
    child: [],
  },
  DEINDEXING: {
    description: 'menu.deindexing',
    link: '/module/deindexing',
    icon: 'las la-low-vision',
    id: 'deindexing',
    class: 'nav-link ',
    child: [],
  },
};

// TEMPLATE OF ACCOUNT MENU
export const ACCOUNT_MENU = {
  CONFIG: {
    description: 'menu.userconfig',
    link: 'openConfigModal',
    icon: 'las la-user-cog',
    id: 'userconfig',
    class: 'nav-link ',
    child: [],
  },
  BILLS: {
    description: 'menu.bills',
    link: 'openBills',
    icon: 'las la-money-bill-wave-alt',
    id: 'bills',
    class: 'nav-link ',
    child: [],
  },
  HELP: {
    description: 'menu.help',
    link: 'goHelp',
    icon: 'las la-info-circle',
    id: 'help',
    class: 'nav-link ',
    child: [],
  },
  PLANS: {
    description: 'menu.plans',
    link: 'openSidebar',
    icon: 'las la-briefcase',
    id: 'userPlan',
    class: 'nav-link ',
    child: [],
  },
};

export interface IMenu {
  description: string;
  icon: string;
  link?: string;
  id?: string;
  class?: string;
  key?: string;
  child?: IMenu[];
}
