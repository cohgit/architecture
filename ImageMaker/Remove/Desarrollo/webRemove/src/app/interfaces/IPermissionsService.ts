export interface IPermissionService {
    key: string;
    delete?: boolean;
    file?: boolean;
    list?: boolean;
    read?: boolean;
    save?: boolean;
    update?: boolean;
};