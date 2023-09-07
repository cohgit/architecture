import {Component, Input, OnInit} from '@angular/core';
import {IDynamicForm, IDynamicFormInput} from '../../interfaces/IDynamicForm';
import {DynamicFormHelperService} from '../../helpers/dynamic-form-helper.service';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.css']
})

export class DynamicFormComponent implements OnInit {
  @Input() dynamicForm: IDynamicForm;
  @Input() disabled: boolean = false;
  isComplete = true;

  constructor(public dfh: DynamicFormHelperService, public translateHelper: TranslateHelperService) {
  }

  ngOnInit(): void {
  }

  /**
   * Check conditions when a field changed.
   * @param inputName
   */
  evaluateConditions(inputName: string): void {
    this.dfh.evaluateConditions(this.dynamicForm, inputName);
  }

  /**
   * Assing check input values and Check conditions.
   * @param input
   */
  evaluateCheckConditions(input: IDynamicFormInput): void {
    this.dfh.evaluateCheckConditions(this.dynamicForm, input);
  }

  public isValid(): boolean {
    return this.dfh.isValidForm(this.dynamicForm);
  }
}
