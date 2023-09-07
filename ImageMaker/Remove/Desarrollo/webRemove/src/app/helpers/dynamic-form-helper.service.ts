import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { IDynamicForm, IDynamicFormConditionResult, IDynamicFormInput } from '../interfaces/IDynamicForm';

@Injectable({
  providedIn: 'root'
})
export class DynamicFormHelperService {
  public TEXT_TYPE = 'TEXT';
  public TEXT_AREA_TYPE = 'TEXT_AREA';
  public NUMBER_TYPE = 'NUMBER';
  public DATE_TYPE = 'DATE';
  public RADIO_TYPE = 'RADIO';
  public SELECT_TYPE = 'SELECT';
  public CHECKBOX_TYPE = 'CHECKBOX';
  public SWITCH_TYPE = 'SWITCH';

  /**
   * Service to build dynamic forms for deindexing
   */
  constructor() { }

  /**
   * Allows you to initialize a base form.
   * @param dynamicForm
   */
  initializeForm(dynamicForm: IDynamicForm) {
    if (dynamicForm) {
      dynamicForm?.sections?.forEach(section => {
        section?.inputs?.forEach(input => {
          if (input.type === this.CHECKBOX_TYPE) {
            let values = input?.value?.split(",");
            input?.options?.forEach(option => {
              option.checked = values?.includes(option.name);
            })
          }
          if (input.type === this.SWITCH_TYPE) {
            if (input.value != null) {
              input.value = input.value === 'true' ? true : input.value === 'false' ? false : null;
            }
          }
        })
      })

      this.findSelectedOptions(dynamicForm);
      this.evaluateFullForm(dynamicForm);
    }
  }

  /**
   * Evaluate all form conditions. Usefull in onload method.
   * @param dynamicForm
   */
  public evaluateFullForm(dynamicForm: IDynamicForm): void {
    dynamicForm.sections.forEach(section => {
      if (section.visible_condition) {
        this.calculate(dynamicForm, section, section.visible_condition, 'visible');
      }

      section.inputs.forEach(input => {
        if (input.required_condition) {
          this.calculate(dynamicForm, input, input.required_condition, 'required');
        }
        if (input.visible_condition) {
          this.calculate(dynamicForm, input, input.visible_condition, 'visible');
        }
        if (input.value_condition) {
          this.calculate(dynamicForm, input, input.value_condition, 'value');
        }
      })
    })
  }

  /**
   * Check conditions when a field changed.
   * @param inputName
   * @param inputValue
   */
  public evaluateConditions(dynamicForm: IDynamicForm, inputName: string): void {
    this.evaluate(dynamicForm, inputName);
  }
  /**
   * Assing check input values and Check conditions.
   * @param input
   */
  public evaluateCheckConditions(dynamicForm: IDynamicForm, input: IDynamicFormInput): void {
    input.value = "";
    input.options.forEach(option => {
      input.value += option.checked ? option.name + ',' : '';
    });

    this.evaluate(dynamicForm, input.name);
  }

  /**
   * Evaluate the result of the form
   * @param dynamicForm
   */
  public evaluateResult(dynamicForm: IDynamicForm): IDynamicFormConditionResult {
    let resultFound = null;

    if (dynamicForm?.conditionResults?.length > 0) {
      dynamicForm.conditionResults.forEach(result => {
        if (resultFound == null && this.recurrentCalculate(result.condition, this.refreshMapValues(dynamicForm))) {
          resultFound = result;
        }
      })
    }

    return resultFound;
  }

  /**
   * Evaluate if the form needs to calculate any condition.
   * @param inputToEvaluate
   */
  private evaluate(dynamicForm: IDynamicForm, inputToEvaluate: string): void {
    dynamicForm.sections.forEach(section => {
      if (section.visible_condition && section.visible_condition.includes(inputToEvaluate)) {
        this.calculate(dynamicForm, section, section.visible_condition, 'visible');
      }

      section.inputs.forEach(input => {
        if (input.required_condition && input.required_condition.includes(inputToEvaluate)) {
          this.calculate(dynamicForm, input, input.required_condition, 'required');
        }
        if (input.visible_condition && input.visible_condition.includes(inputToEvaluate)) {
          this.calculate(dynamicForm, input, input.visible_condition, 'visible');
        }
        if (input.value_condition && input.value_condition.includes(inputToEvaluate)) {
          this.calculate(dynamicForm, input, input.value_condition, 'value');
        }
      })
    })
  }

  /**
   * Calculate a condition and assign correct value to the attribute to refresh.
   * @param elementToCalulate
   * @param condition
   * @param attToUpdate
   */
  private calculate(dynamicForm: IDynamicForm, elementToCalulate: any, condition: string, attToUpdate: string): void {
    elementToCalulate[attToUpdate] = this.recurrentCalculate(condition, this.refreshMapValues(dynamicForm));
  }

  /**
   * Create a json object with all form inputs for easy validation.
   * @returns
   */
  public refreshMapValues(dynamicForm: IDynamicForm): any {
    let map: any = {};

    dynamicForm.sections.forEach(section => {
      section.inputs.forEach(input => {
        map[input.name] = input;
      })
    })

    return map;
  }

  /**
   * Recurrent function that calculates a condition string from a json value objects.
   * @param condition
   * @param mapValues
   * @returns
   */
  private recurrentCalculate(condition: string, mapValues: any): boolean {
    if (condition.trim().toUpperCase() === TRUE) {
      return true;
    }
    if (condition.trim().toUpperCase() === FALSE) {
      return false;
    }
    if (condition.includes("(")) {          // substring first '(' and last ')' (Recurrence)
      let indexOpen = condition.lastIndexOf('(');
      let indexClose = condition.substring(indexOpen).indexOf(')') + 1;
      let subcondition = condition.substring(indexOpen, indexClose);
      condition = condition.replace(subcondition, this.recurrentCalculate(subcondition.replace("(","").replace(")",""), mapValues)+'');
      return this.recurrentCalculate(condition, mapValues);
    } else if (condition.includes(CONNECTOR_AND)) {
      let split = condition.split(CONNECTOR_AND);
      let result = true;

      split.forEach(innerCondition => {
        if (result && !this.recurrentCalculate(innerCondition, mapValues)) {
          result = false;
        }
      })

      return result;
    } else if (condition.includes(CONNECTOR_OR)) {
      let split = condition.split(CONNECTOR_OR);
      let result = false;

      split.forEach(innerCondition => {
        if (!result && this.recurrentCalculate(innerCondition, mapValues)) {
          result = true;
        }
      })

      return result;
    } else {                                                      // Conditions at minimal expresion
      if (condition.includes(CONDITION_DF)) {                     // Evaluate Different
        return this.compareDifferent(condition, mapValues);
      } else if (condition.includes(CONDITION_GE)) {              // Evaluate Greater or Equals
        return this.compareGreaterOrEquals(condition, mapValues);
      } else if (condition.includes(CONDITION_G)) {               // Evaluate Greater
        return this.compareGreater(condition, mapValues);
      } else if (condition.includes(CONDITION_LE)) {              // Evaluate Less or Equals
        return this.compareLessOrEqual(condition, mapValues);
      } else if (condition.includes(CONDITION_L)) {               // Evaluate Less
        return this.compareLess(condition, mapValues);
      } else if (condition.includes(CONDITION_E)) {               // Evaluate Equals
        return this.compareEquals(condition, mapValues);
      }
    }

    return this.checkSimpleValue(condition, mapValues);
  }

  /**
   * Compare brute value of an input (Usefull for switch cases)
   * @param condition
   * @param mapValues
   */
  checkSimpleValue(condition: string, mapValues: any): boolean {
    let sign = !condition.trim().startsWith("!");
    let key = condition.trim().replace("!","");

    if (mapValues[key] != null) {
      return sign ? mapValues[key].value : !mapValues[key].value;
    }

    return true;
  }

  /**
   * Compare a simple condition with DIFFERENT comparator (!=)
   * @param condition
   * @param mapValues
   * @returns
   */
  private compareDifferent(condition: string, mapValues: any): boolean {
    let splited = condition.split(CONDITION_DF);
    let fieldName = splited[0].trim().toUpperCase();

    if (mapValues[fieldName]?.value != null) {
      let typeField = mapValues[fieldName].type.trim();
      let fieldSelected;
      let compareValue = this.getComparableValue(splited[1]);

      switch (typeField) {
        case this.TEXT_TYPE:
        case this.TEXT_AREA_TYPE:
        case this.SELECT_TYPE:
        case this.RADIO_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return fieldSelected !== compareValue;
        case this.NUMBER_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return Number(fieldSelected) !== Number(compareValue);
        case this.CHECKBOX_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return !fieldSelected.includes(compareValue);
        case this.DATE_TYPE:
          let dateToCompare = moment(compareValue, FORMAT_DATE);
          let dateValue = moment(mapValues[splited[0]].value);
          return !dateValue.isSame(dateToCompare);
      }
    }

    return false;
  }

  /**
   * Compare a simple condition with GREATER or EQUALS comparator (>=)
   * @param condition
   * @param mapValues
   * @returns
   */
  private compareGreaterOrEquals(condition: string, mapValues: any): boolean {
    let splited = condition.split(CONDITION_GE);
    let fieldName = splited[0].trim().toUpperCase();

    if (mapValues[fieldName]?.value != null) {
      let typeField = mapValues[fieldName].type.trim();
      let fieldSelected;
      let compareValue = this.getComparableValue(splited[1]);

      switch (typeField) {
        case this.TEXT_TYPE:
        case this.TEXT_AREA_TYPE:
        case this.SELECT_TYPE:
        case this.RADIO_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return fieldSelected >= compareValue;
        case this.NUMBER_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return Number(fieldSelected) >= Number(compareValue);
        case this.CHECKBOX_TYPE:
          // N/A
          break;
        case this.DATE_TYPE:
          let dateToCompare = moment(compareValue, FORMAT_DATE);
          let dateValue = moment(mapValues[splited[0]].value);
          return dateValue.isSameOrAfter(dateToCompare);
      }
    }

    return false;
  }

  /**
   * Compare a simple condition with GREATER comparator (>)
   */
  private compareGreater(condition: string, mapValues: any): boolean {
    let splited = condition.split(CONDITION_G);
    let fieldName = splited[0].trim().toUpperCase();

    if (mapValues[fieldName]?.value != null) {
      let typeField = mapValues[fieldName].type.trim();
      let fieldSelected;
      let compareValue = this.getComparableValue(splited[1]);

      switch (typeField) {
        case this.TEXT_TYPE:
        case this.TEXT_AREA_TYPE:
        case this.SELECT_TYPE:
        case this.RADIO_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return fieldSelected > compareValue;
        case this.NUMBER_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return Number(fieldSelected) > Number(compareValue);
        case this.CHECKBOX_TYPE:
          // N/A
          break;
        case this.DATE_TYPE:
          let dateToCompare = moment(compareValue, FORMAT_DATE);
          let dateValue = moment(mapValues[splited[0]].value);
          return dateValue.isAfter(dateToCompare);
      }
    }

    return false;
  }

  /**
   * Compare a simple condition with LESS or EQUAL comparator (<=)
   * @param condition
   * @param mapValues
   * @returns
   */
  private compareLessOrEqual(condition: string, mapValues: any): boolean {
    let splited = condition.split(CONDITION_LE);
    let fieldName = splited[0].trim().toUpperCase();

    if (mapValues[fieldName]?.value != null) {
      let typeField = mapValues[fieldName].type.trim();
      let fieldSelected;
      let compareValue = this.getComparableValue(splited[1]);

      switch (typeField) {
        case this.TEXT_TYPE:
        case this.TEXT_AREA_TYPE:
        case this.SELECT_TYPE:
        case this.RADIO_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return fieldSelected <= compareValue;
        case this.NUMBER_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return Number(fieldSelected) <= Number(compareValue);
        case this.CHECKBOX_TYPE:
          // N/A
          break;
        case this.DATE_TYPE:
          let dateToCompare = moment(compareValue, FORMAT_DATE);
          let dateValue = moment(mapValues[splited[0]].value);
          return dateValue.isSameOrBefore(dateToCompare);
      }
    }

    return false;
  }

  /**
   * Compare a simple condition with LESS comparator (<)
   * @param condition
   * @param mapValues
   * @returns
   */
  private compareLess(condition: string, mapValues: any): boolean {
    let splited = condition.split(CONDITION_L);
    let fieldName = splited[0].trim().toUpperCase();

    if (mapValues[fieldName]?.value != null) {
      let typeField = mapValues[fieldName].type.trim();
      let fieldSelected;
      let compareValue = this.getComparableValue(splited[1]);

      switch (typeField) {
        case this.TEXT_TYPE:
        case this.TEXT_AREA_TYPE:
        case this.SELECT_TYPE:
        case this.RADIO_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return fieldSelected < compareValue;
        case this.NUMBER_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return Number(fieldSelected) < Number(compareValue);
        case this.CHECKBOX_TYPE:
          // N/A
          break;
        case this.DATE_TYPE:
          let dateToCompare = moment(compareValue, FORMAT_DATE);
          let dateValue = moment(mapValues[splited[0]].value);
          return dateValue.isBefore(dateToCompare);
      }
    }

    return false;
  }

  /**
   * Compare a simple condition with EQUALS comparator (=)
   * @param condition
   * @param mapValues
   * @returns
   */
  private compareEquals(condition: string, mapValues: any): boolean {
    let splited = condition.split(CONDITION_E);
    let fieldName = splited[0].trim().toUpperCase();

    if (mapValues[fieldName]?.value != null) {
      let typeField = mapValues[fieldName].type.trim();
      let fieldSelected;
      let compareValue = this.getComparableValue(splited[1]);

      switch (typeField) {
        case this.TEXT_TYPE:
        case this.TEXT_AREA_TYPE:
        case this.SELECT_TYPE:
        case this.RADIO_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return fieldSelected === compareValue;
        case this.NUMBER_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return Number(fieldSelected) === Number(compareValue);
        case this.CHECKBOX_TYPE:
          fieldSelected = mapValues[fieldName].value.trim().toUpperCase()
          return fieldSelected.includes(compareValue);
        case this.DATE_TYPE:
          let dateToCompare = moment(compareValue, FORMAT_DATE);
          let dateValue = moment(mapValues[splited[0]].value);
          return dateValue.isSame(dateToCompare);
      }
    }

    return false;
  }

  /**
   * Extract a comparable value from formula.
   * @param value
   * @returns
   */
  private getComparableValue(value: string): string {
    if (value.includes("'")) {
      return value.replace("'","").trim().toUpperCase();
    }

    return value.trim().toUpperCase();
  }

  /**
   * Replace tokens in deindex view
   * @param mapValues
   * @param conclusion
   */
  replaceTokens(mapValues: any, conclusion: any): any {
    let replaced = Object.assign({}, conclusion);

    if (mapValues) {
      Object.keys(mapValues).forEach(key => {
        switch (mapValues[key].type) {
          case this.TEXT_TYPE:
          case this.TEXT_AREA_TYPE:
          case this.NUMBER_TYPE:
            let textSimple = "";
            if (mapValues[key].value != null) {
              textSimple = mapValues[key].value;
            }
            replaced.text = replaced.text.replace("{{"+key+"}}", textSimple);
            break;
          case this.SELECT_TYPE:
          case this.RADIO_TYPE:
            let textOption = "";
            if (mapValues[key].selectedOption != null) {
              textOption = mapValues[key].selectedOption.label;
            }
            replaced.text = replaced.text.replace("{{"+key+"}}", textOption);
            break;
          case this.CHECKBOX_TYPE:
            let textOptions = "";

            if (mapValues[key].selectedOptions != null) {
              textOptions = this.mergeOptionsLabels(mapValues[key].selectedOptions);
            }

            replaced.text = replaced.text.replace("{{"+key+"}}", textOptions);
            break;
          case this.DATE_TYPE:
            if (mapValues[key]?.value) {
              replaced.text = replaced.text.replace("{{"+key+"}}",  moment(mapValues[key].value).format('D/MM/yyyy'));
            }

            break;
        }
      })
    }

    return replaced;
  }

  /**
   *  Assign a label to the options of the selects
   * @param selectedOptions
   */
  mergeOptionsLabels(selectedOptions: any[]): string {
    let text = "";

    selectedOptions.forEach(option => {
      if (option != null) {
        text += option.label + ", ";
      }
    });

    return text.length > 2 ? text.substring(0, text.length - 2) : "";
  }

  /**
   * Check the selected option in the selects
   * @param dynamicForm
   */
  findSelectedOptions(dynamicForm: any): void {
    if (dynamicForm) {
      dynamicForm.sections.forEach(section => {
        section.inputs.forEach(input => {
          if (input.value != null) {
            if (input.type === this.SELECT_TYPE || input.type === this.RADIO_TYPE) {
              input.options.forEach(opt => {
                if (opt.name === input.value) {
                  input.selectedOption = opt;
                }
              })
            } else if (input.type === this.CHECKBOX_TYPE) {
              let values = input.value.split(",");
              input.selectedOptions = [];

              input.options.forEach(opt => {
                if (values.includes(opt.name)) {
                  input.selectedOptions.push(opt);
                }
              })
            }
          }
        })
      })
    }
  }

  /**
   * Check if a form is valid
   * @param dynamicForm
   */
  isValidForm(dynamicForm: IDynamicForm): boolean {
    let isValid = true;

    dynamicForm.sections.forEach(section => {
      section.inputs.forEach(input => {
        // Add another validations as developing (email format, max or min values, etc)
        if (input.required && input.value == null) {
          isValid = false;
          input.errorMessage = 'error.required';
        } else {
          input.errorMessage = null;
        }
      })
    })

    return isValid;
  }
}

const CONNECTOR_AND = " AND ";
const CONNECTOR_OR = " OR ";
const CONDITION_DF = "!=";
const CONDITION_GE = ">=";
const CONDITION_G = ">";
const CONDITION_LE = "<=";
const CONDITION_L = "<";
const CONDITION_E = "=";
const FORMAT_DATE = "DD-MM-YYYY";
const TRUE = "TRUE";
const FALSE = "FALSE";
