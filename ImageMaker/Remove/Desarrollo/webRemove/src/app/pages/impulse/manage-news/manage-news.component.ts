import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from '@angular/forms';
import {ImpulseVariableService} from '../../../services/impulse-variable.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {CommonService} from '../../../services/common.service';
import {UtilService} from '../../../helpers/util.service';

@Component({
  selector: 'app-manage-news',
  templateUrl: './manage-news.component.html',
  styleUrls: ['./manage-news.component.css']
})
export class ManageNewsComponent implements OnInit {
  allCountries: any;
  fltCountries: any;
  allConcepts: any;
  allSections: any;
  copyArray: Array<any>;
  variable: any = {};
  newAction: any = {};

  /**
   * manages nees after an impulse is published
   * @param formBuilder
   * @param variableService
   * @param data
   * @param commonService
   * @param dialogRef
   * @param utilService
   * @param translate
   */
  constructor(private formBuilder: FormBuilder, private variableService: ImpulseVariableService,
              @Inject(MAT_DIALOG_DATA) public data: any, public commonService: CommonService,
              private dialogRef: MatDialogRef<ManageNewsComponent>, public utilService: UtilService,
              public translate: TranslateService) {
  }

  ngOnInit(): void {
    this.loadCommons();
  }

  /*
load commons services to fill all select inputs
* */
  loadCommons(): void {
    this.commonService.listCountries(response => {
      this.allCountries = response;
      this.fltCountries = this.allCountries.slice();
    });
    this.commonService.listSearchTypes(response => this.allSections = response);
    this.commonService.listImpulseConcepts(response => this.allConcepts = response)
    this.variableService.get(this.data.info.id, response => this.variable = response);
  }

  /**
   * call services update or save as appropriate
   * @param form
   */
  onConfirmClick(form: NgForm): void {
    if (form.valid) {
      if (!this.variable.id) {
        this.variableService.save(this.variable, () => {
          this.dialogRef.close(true);
        });
      } else {
        this.variableService.update(this.variable, () => {
          this.dialogRef.close(true);
        });
      }
    }
  }

  /**
   * add an action to an array
   * @param urlForm
   */
  addAction(urlForm: NgForm): void {
    if (urlForm.valid){
      this.newAction.conceptType = this.newAction.conceptType.concept;
      this.variable.interactions.push(this.newAction);
      this.newAction = {};
    }
  }

  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   * remove an item from an array
   * @param action
   */
  removeItem(action: any): void {
    if (action){
      if (!action.id) {
        this.utilService.removefromArrayByKey(this.variable.interactions, action, 'id');
      } else {
        action.markToDelete = !action.markToDelete;
      }
    }
  }
}
