import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewEditUserComponent} from './new-edit-user/new-edit-user.component';
import {IUsers} from '../../interfaces/IUsers';
import {ModalService} from '../../helpers/modal.service';
import {UtilService} from '../../helpers/util.service';
import {UserService} from '../../services/user.service';
import {TranslateHelperService} from '../../helpers/translate-helper.service';
import {CommonService} from '../../services/common.service';
import {HistoryAuditUserComponent} from "./history-audit-user/history-audit-user.component";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  allUsers: IUsers[];
  filteredUser: IUsers[];
  copyArray: Array<any>;
  allProfiles: any;
  /* objeto a enviar al filtro */
  filter = {
    name: '',
    profile: '',
    active: true,
  };
  sortDirection = false;
  paginatorClass = 'paginator';

  /**
   * manage user information
   * @param dialog
   * @param modalService
   * @param utilService
   * @param userService
   * @param translateHelper
   * @param commonService
   */
  constructor(private dialog: MatDialog, private modalService: ModalService, private utilService: UtilService,
              public userService: UserService, public translateHelper: TranslateHelperService, private commonService: CommonService) {
  }

  ngOnInit(): void {
    this.loadTable();
    this.loadCommons();
  }

  /*
load commons services to fill select inputs
* */
  loadCommons(): void {
    this.commonService.listProfiles(response => this.allProfiles = response);
  }

  /*
  load principal table
   * */
  loadTable(): void {
    this.userService.list(response => {
      this.allUsers = response;
      this.allUsers.sort((a, b) => a.name.localeCompare(b.name));
      this.filteredUser = this.allUsers;
    });
  }

  /**
   *   function to open modal
   * @param isNuevo
   * @param isEditable
   * @param client
   * @param componente
   */
  openModal(isNuevo: boolean, isEditable: boolean, user: IUsers, componente): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        nuevo: isNuevo,
        editable: isEditable,
        info: user
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadTable();
    });
  }

  /**
   * Open a model to edit an Item
   * @param uuid
   */
  editItem(uuid: string): void {
    this.userService.get(uuid, response => {
      this.openModal(false, this.userService.permissions.update, response, NewEditUserComponent);
    });
  }

  /*
Open a model to create a new Item
 * */
  newItem(): void {
    this.openModal(true, this.userService.permissions.save, {clients: []} = <IUsers>{clients: []}, NewEditUserComponent);
  }

  /**
   * change item status (active or deactive)
   * @param row
   */
  toggleActive(row: any): void {
    this.utilService.toggleActivo(row, 'active', 'name', this.userService);
  }

  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   *   search items in table with the filters values
   * @param value
   */
  searchResult(): void {
    if (this.filter.profile !== '') {
      this.filteredUser = this.allUsers.filter(u =>
        (u.name.toLowerCase().includes(this.filter.name.toLowerCase()) ||
          u.lastname.toLowerCase().includes(this.filter.name.toLowerCase())) &&
        u.profile === this.filter.profile &&
        u.active === this.filter.active
      );
    } else {
      this.filteredUser = this.allUsers.filter(u =>
        (u.name.toLowerCase().includes(this.filter.name.toLowerCase()) ||
          u.lastname.toLowerCase().includes(this.filter.name.toLowerCase())) &&
        u.active === this.filter.active
      );
    }
  }

  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(colName, true, this.filteredUser, this.sortDirection);
    this.filteredUser = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }

  /**
   * clean Filters
   */
  clean(): void {
    this.filter = {
      name: '',
      profile: '',
      active: true,
    };
    this.filteredUser = this.allUsers;
  }

  /**
   * shows user activity
   * @param uuid
   */
  historyUser(uuid: any) {
    this.userService.listAudit(uuid, response => {
      this.openModal(false, this.userService.permissions.read, response, HistoryAuditUserComponent);
    });
  }

}
