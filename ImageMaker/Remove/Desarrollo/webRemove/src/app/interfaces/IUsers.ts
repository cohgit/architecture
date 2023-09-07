export interface IUsers {
  id?: number;
  name?: string;
  lastname?: string;
  phone?: string;
  email?: string;
  active?: boolean;
  profile?:string,
  profileDef?: {
    code?: string;
    description?: string,
    tag?: string
  };
  clients: any[]
}
