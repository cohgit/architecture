import {IPlans} from './IPlans';

export interface IClients {
  id?: number;
  id_country?: number;
  uuid?: number;
  name?: string;
  lastname?: string;
  email?: string;
  language?: string;
  phone?: string;
  project_name?: string;
  active?: boolean;
  corporative?: boolean;
  bussiness_name?: boolean;
  dni?: string;
  fiscal_address?: string;
  postal?: string;
  plan?: IPlans[];
  goal?: number;
  expiredDate?: string;
  initDate?: string;
  failed_attempts?: number;​​
  first_time?: boolean;
  planSuggestions: [];
}
export class Clients {
  id?: null;
  id_country?: null;
  uuid?: null;
  name?: '';
  lastname?: '';
  email?: '';
  language?: '';
  phone?: '';
  project_name?: '';
  active?: false;
  corporative?: false;
  bussiness_name?: false;
  dni?: '';
  fiscal_address?: '';
  postal?: '';
  plan?: IPlans[];
  goal?: number;
  expiredDate?: '';
  initDate?: '';
  failed_attempts?: null;​​
  first_time?: true;
  planSuggestions: [];
}
