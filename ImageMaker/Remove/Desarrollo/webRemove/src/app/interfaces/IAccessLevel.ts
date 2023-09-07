export interface IAccessLevel {
  service_key: string;
  class: string;
  ADMIN?: boolean;
  CLIENT?: boolean;
}

export class AccessLevel implements IAccessLevel {
  service_key: string;
  class: string;
  ADMIN?: boolean;
  CLIENT?: boolean;

  constructor(service_key: string, styleClass: string, isClient?: boolean, isAdmin?: boolean) {
    this.service_key = service_key;
    this.class = styleClass;

    if (isClient) {
      this.CLIENT = true;
    }
    if (isAdmin) {
      this.ADMIN = true;
    }
  }

  static instanceAdmin(): AccessLevel {
    return new AccessLevel('admin', 'login-body-admin', null, true);
  }
  static instanceClient(): AccessLevel {
    return new AccessLevel('client', 'login-body', true);
  }
}


