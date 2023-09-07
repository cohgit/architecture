import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UtilService } from '../../helpers/util.service';

import { TranslateHelperService } from '../../helpers/translate-helper.service';
import { DeindexationService } from '../../services/deindexation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TrackingDeindexingComponent } from './tracking-deindexing/tracking-deindexing.component';
import { SessionService } from 'src/app/helpers/session.service';
import { CommonService } from '../../services/common.service';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

@Component({
  selector: 'app-deindexing',
  templateUrl: './deindexing.component.html',
  styleUrls: ['./deindexing.component.css'],
})
export class DeindexingComponent implements OnInit {
  private uuid_client;
  allStates = [];
  separatorKeysCodes: number[] = [ENTER, COMMA];
  /* objeto a enviar al filtro */
  filter = {
    personal: '',
    status: '',
  };
  ICONS = {
    created: 'las la-user-edit',
    sent: 'lab la-telegram-plane',
    processing: 'las la-redo-alt',
    approved: 'las la-thumbs-up',
    rejected: 'las la-thumbs-down',
    deleted: 'las la-trash',
    ESTADO_DEFAULT: 'fa fa-info-circle',
  };
  copyArray: Array<any>;
  table: any;
  sortDirection = false;
  isAdmin = false;
  paginatorClass = 'paginator';

  /**
   * manage desindexing component
   * @param utilService
   * @param translateHelper
   * @param deindexationService
   * @param commonService
   * @param route
   * @param dialog
   * @param session
   */
  constructor(
    public utilService: UtilService,
    public translateHelper: TranslateHelperService,
    private router: Router,
    private deindexationService: DeindexationService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private session: SessionService
  ) {
    this.uuid_client = this.route.snapshot.paramMap.get('uuid_client');
  }

  ngOnInit(): void {
    this.loadTable();
    this.loadCommons();
    if (this.session.getUser().permissions.profile.ADMIN) {
      this.isAdmin = true;
    }
  }

  /*
    Carga la tabla con los registros iniciales
  **/
  loadTable(): void {
    this.deindexationService.list(this.uuid_client, (response) => {
      this.table = response;
      this.table.list.sort(
        (a, b) =>
          new Date(b.creation_date).getTime() -
          new Date(a.creation_date).getTime()
      );
    });
  }

  /**
   * split keywords by /
   * @param word
   */
  splitKeywords(word) {
    let usingSplit = [];
    if (word.length > 0) {
      usingSplit = word.split('/');
      return usingSplit;
    }
  }

  /**
   * load commons services to fill select inputs
   */
  loadCommons(): void {
    this.commonService.listDeindexationStatus(
      (response) => (this.allStates = response)
    );
  }

  /*
    Filtra el arreglo principal según la búsqueda
  **/
  searchResult(value: any) {
    if (value.status !== '') {
      this.copyArray = this.table.list.filter(
        (u) =>
          (u.person_name.toLowerCase().includes(value.personal) ||
            u.person_lastname.toLowerCase().includes(value.personal)) &&
          u.status === value.status
      );
    } else {
      this.copyArray = this.table.list.filter(
        (u) =>
          u.person_name.toLowerCase().includes(value.personal) ||
          u.person_lastname.toLowerCase().includes(value.personal)
      );
    }
  }

  /**
   * Limpia el filtro de búsqueda
   */
  clean() {
    this.filter = {
      personal: '',
      status: '',
    };
    this.copyArray = this.table.list;
  }

  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(
      colName,
      true,
      this.table.list,
      this.sortDirection
    );
    this.table.list = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }

  /*
 función para controlar al pagindor
 * */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   * check historic of the deindexation
   * @param uuid
   */
  checkTracking(uuid: string) {
    this.deindexationService.getHistoric(uuid, (response) => {
      this.openModal(response, TrackingDeindexingComponent);
    });
  }

  /**
   *   function to open modal
   * @param isNuevo
   * @param isEditable
   * @param client
   * @param componente
   */
  openModal(data: any, componente): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        info: data,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.loadTable();
    });
  }
  /**
   * Go to client view
   */
  goClient() {
    if (this.session.getAccessConfig().ADMIN) {
      this.router.navigate(['admin/module/clients']);
    }
  }
}
