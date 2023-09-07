import {Component, Inject, Input, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {NgForm} from '@angular/forms';
import {CommonService} from '../../../services/common.service';
import {PlanService} from '../../../services/plan.service';
import {UtilService} from '../../../helpers/util.service';

@Component({
  selector: 'app-change-plan',
  templateUrl: './change-plan.component.html',
  styleUrls: ['./change-plan.component.css']
})
export class ChangePlanComponent implements OnInit {
  @Input() infoPlan: any[] = [];

  isNuevo = false;
  isEditable = false;
  allTypes: any[] = [];
  allPlans: any[] = [];
  suggestedPlan: any;
  detailPlan: any;
  showDetail = false;

  /**suggest a plan to a client
   *
   * @param planService
   * @param utilService
   * @param translate
   * @param commonService
   */
  constructor( private planService: PlanService,
               public utilService: UtilService,
               public translate: TranslateService, private commonService: CommonService) {
  }
  ngOnInit(): void {
    this.loadCommons();
  }

  /**
   * load commons services to fill all select inputs
   */
  loadCommons(): void {
    this.commonService.listSearchTypes(response => {
      this.allTypes = response;
    });
    this.commonService.listPlans(response => {
      this.allPlans = response.plans;
    });
  }

  /**
   *
   * @param planForm
   */
  onConfirmClick(planForm: NgForm) {
    if (planForm.valid){
    }
  }

  /**
   * show info about a plan
   * @param suggestedPlan
   */
  planDetail(suggestedPlan: any) {
    this.showDetail = true;
    this.planService.get(suggestedPlan.id, response => {
      this.detailPlan = response;
    });
  }
}
