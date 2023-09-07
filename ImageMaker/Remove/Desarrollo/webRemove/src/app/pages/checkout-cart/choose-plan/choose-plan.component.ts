import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';
import {MatDialog} from "@angular/material/dialog";
import {CustomCheckoutComponent} from '../custom-checkout/custom-checkout.component';
import {UtilService} from "../../../helpers/util.service";
import {SessionService} from 'src/app/helpers/session.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-choose-plan',
  templateUrl: './choose-plan.component.html',
  styleUrls: ['./choose-plan.component.css']
})
export class ChoosePlanComponent implements OnInit {
  @Input() allPlans: any[];
  @Output() selectedPlan = new EventEmitter<any>();
  selected: any = {};
  filterPlan = [];

  category = [
    {
      tittle: 'detect',
      icon: 'las la-camera',
      access: ['one_shot', 'monitor']
    },
    {
      tittle: 'potency',
      icon: 'las la-sort-numeric-up',
      access: ['transform']
    },
    {
      tittle: 'delete',
      icon: 'las la-sync',
      access: ['transform', 'deindex']
    },

  ];
  typeOfPlan = ['one_shot', 'monitor', 'transform', 'deindex'];
  subCategory = [];

  isLinear = false;
  tittleSelected = '';
  typeSelected = '';

  /**
   * external page to select a plan
   * @param translateHelper
   * @param dialog
   * @param router
   * @param utilService
   * @param session
   */
  constructor(public translateHelper: TranslateHelperService, private dialog: MatDialog, private router: Router,
              public utilService: UtilService, private session: SessionService) {
  }

  ngOnInit(): void {
  }

  /**
   * return a css class based of a type of plan
   * DEPRECATED customized plan are not showing in this screen
   * @param plan
   */
  checkActive(plan: any) {
    if (plan.customized) {
      return 'generic_content active clearfix';
    } else {
      return 'generic_content clearfix';
    }
  }

  /**
   * go to the next screen
   * @param plan
   */
  planSelected(plan: any) {
    this.router.navigate(['/client/checkout/' + plan.id]);
    // this.session.goTo('/client/checkout/'+plan.id); // Fix redirect to success before this
    // This was changed so it can be added plan ID in url path
    // this.selectedPlan.emit(plan);
  }

  /**
   * open a model if a client want a customized plan
   */
  openCustomized() {
    this.dialog.open(CustomCheckoutComponent, {
      width: '70%',
      closeOnNavigation: true,
    });
  }

  /**
   * default value
   * @param type
   */
  firstLevel(type: any) {
    this.selected = {};
    this.selected = type;
    this.filterPlan = [];

  }

  /**
   * show plans based on a type of scanner
   * @param sltd
   */
  fillPlan(sltd: string) {
    this.tittleSelected = this.translateHelper.instant('label.plan.landing.' + sltd);
    this.filterPlan = [];


    if (sltd === 'transform') {
      this.filterPlan = this.allPlans.filter(a =>
        a.access_transforma);
    }
    if (sltd === 'one_shot') {
      this.filterPlan = this.allPlans.filter(a =>
        a.access_scanner);
    }
    if (sltd === 'monitor') {
      this.filterPlan = this.allPlans.filter(a =>
        a.access_monitor);
    }
    if (sltd === 'deindex') {
      this.filterPlan = this.allPlans.filter(a =>
        a.access_deindexation);
    }
  }
}
