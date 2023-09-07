import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {UtilService} from '../../helpers/util.service';
import {TranslateHelperService} from '../../helpers/translate-helper.service';
import {PlanService} from '../../services/plan.service';
import {IPlans} from '../../interfaces/IPlans';
import {NewEditPlanComponent} from './new-edit-plan/new-edit-plan.component';

@Component({
  selector: 'app-plans',
  templateUrl: './plans.component.html',
  styleUrls: ['./plans.component.css']
})
export class PlansComponent implements OnInit {
  allPlans: IPlans[];
  filteredPlans: IPlans[] = [];
  copyArray: Array<any>;
  /* objeto a enviar al filtro */
  filter = {
    name: '',
    active: true,
    customized: false
  };
  sortDirection = false;
  paginatorClass = 'paginator';

  /**
   * manages information about plans
   * @param dialog
   * @param utilService
   * @param planService
   * @param translateHelper
   */
  constructor(private dialog: MatDialog, private utilService: UtilService,
              public planService: PlanService, public translateHelper: TranslateHelperService) {
  }

  ngOnInit(): void {
    this.loadTable();
  }

  /*
  load principal table
   * */
  loadTable(): void {
    this.planService.list(response => {
      this.allPlans = response;
      this.allPlans.sort((a, b) => a.name.localeCompare(b.name));
      this.filteredPlans = this.allPlans;
    });
  }

  /**
   *  function to open modal
   * @param isNuevo
   * @param isEditable
   * @param plan
   * @param componente
   */
  openModal(isNuevo: boolean, isEditable: boolean, plan: IPlans, componente): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      width: '90%',
      data: {
        nuevo: isNuevo,
        editable: isEditable,
        info: plan
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadTable();
    });
  }

  /**
   * Open a model to edit an Item
   * @param id
   */
  editItem(id: number): void {
    this.planService.get(id, response => {
      this.openModal(false, this.planService.permissions.update, response, NewEditPlanComponent);
    });
  }

  /*
Open a model to create a new Item
 * */
  newItem(): void {
    this.openModal(true, this.planService.permissions.save, {clientSuggestions: []} = <IPlans>{clientSuggestions: []}, NewEditPlanComponent);
  }

  /**
   * change item status (active or deactive)
   * @param row
   */
  toggleActive(row: any): void {
    this.utilService.toggleActivo(row, 'active', 'name', this.planService);
  }

  /**
   *  change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   * search items in table
   * @param value
   */
  searchResult(): void {

   // console.log('this.filter', this.filter);
    if (this.filter.name !== ''){
      this.filteredPlans = this.allPlans.filter(plan =>
        plan.name.toLowerCase().includes(this.filter.name.toLowerCase()) &&
        plan.active === this.filter.active && plan.customized === this.filter.customized
      );
    } else {
      this.filteredPlans = this.allPlans.filter(plan =>
        plan.active === this.filter.active && plan.customized === this.filter.customized
      );
    }

  }



  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(colName, true, this.filteredPlans, this.sortDirection);
    this.filteredPlans = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }

  /**
   * clean Filters
   */
  clean(): void {
    this.filter = {
      name: '',
      active: true,
      customized: false
    };
    this.filteredPlans = this.allPlans;
  }
}
