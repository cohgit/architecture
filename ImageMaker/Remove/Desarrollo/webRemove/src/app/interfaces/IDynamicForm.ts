/**
 * Interfaces for Dynamic Forms
 */
export interface IDynamicForm {
    id: number,
    version: string,
    type: string,
    creation_date: string,
    activation_date?: string,
    deactivation_date?: string,
    template?: string,
    active: boolean,
    sections: IDynamicFormSection[],
    conditionResults: IDynamicFormConditionResult[]
};

export interface IDynamicFormSection {
    id_form: number,
    position: number,
    visible: boolean,
    visible_condition?: string,
    active: boolean,
    label: string,
    labels: IDynamicFormSectionLabel[],
    inputs: IDynamicFormInput[]
};

export interface IDynamicFormInput {
    id_section: number,
    name: string,
    type: string,
    width: number,
    required: boolean,
    required_condition?: string,
    visible: boolean, 
    visible_condition?: string,
    enabled: boolean,
    enabled_condition: string,
    errorMessage: string;
    value: any,
    value_condition?: string,
    active: boolean,
    position: number,
    label: string,
    selectedOption: IDynamicFormInputOption,
    selectedOptions: IDynamicFormInputOption[],
    labels: IDynamicFormInputLabel[],
    options?: IDynamicFormInputOption[]
}

export interface IDynamicFormInputOption {
    id_input: number,
    name: string,
    active: boolean,
    checked?: boolean,
    label: string,
    labels: IDynamicFormInputOptionsLabel[]
}

export interface IDynamicFormConditionResult {
    id_form: number,
    position: number,
    condition: string,
    active: boolean,
    label: string,
    labels: IDynamicFormConditionResultLabel[]
};

export interface IDynamicFormInputOptionsLabel extends IFormLabel {
    id_dynamic_forms_input_options: number
}
export interface IDynamicFormInputLabel extends IFormLabel {
    id_dynamic_forms_input: number
}
export interface IDynamicFormSectionLabel extends IFormLabel {
    id_dynamic_forms_section: number
}
export interface IDynamicFormConditionResultLabel extends IFormLabel {
    id_dynamic_forms_conditions_results: number
};

interface IFormLabel {
    label: string,
    language: string
}