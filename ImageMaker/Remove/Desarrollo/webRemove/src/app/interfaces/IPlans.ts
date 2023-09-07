export interface IPlans {
  id: number;
  name: string;
  description: string;
  duration_months: number;
  cost: number;
  automatic_renewal: boolean;
  limit_credits: number;
  max_keywords: number;
  max_countries: number;
  max_search_page: number;
  sections_to_search: number;
  max_url_to_remove: number;
  max_url_to_impulse: number;
  customized: boolean;
  access_scanner: boolean;
  access_monitor: boolean;
  access_transforma: boolean;
  active: boolean;
  user_with_plan: number;
  max_scanner: number;
  clientSuggestions: any[];
  total_monitor_licenses: number;
  total_transforma_licenses: number;
  access_deindexation: boolean;
  max_url_to_deindexate: number;
  target_page: number;
}

