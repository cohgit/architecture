import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { DynamicFormHelperService } from 'src/app/helpers/dynamic-form-helper.service';
import { IDynamicForm, IDynamicFormConditionResult } from 'src/app/interfaces/IDynamicForm';

@Component({
  selector: 'app-dynamic-display',
  templateUrl: './dynamic-display.component.html',
  styleUrls: ['./dynamic-display.component.css']
})
export class DynamicDisplayComponent implements OnInit {
  @Input() dynamicForm: IDynamicForm;
  @Input() showResult: boolean;
  result: IDynamicFormConditionResult;

  /**
   * Used for dynamic deindexing form construction
   * @param dfh
   */
  constructor(public dfh: DynamicFormHelperService) { }

  ngOnInit(): void {
    this.result = this.dfh.evaluateResult(this.dynamicForm);
  }
}
