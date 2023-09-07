import {Component, OnInit} from '@angular/core';
import {ClientService} from '../../services/client.service';
import {IClients} from '../../interfaces/IClients';
import {MatDialog} from '@angular/material/dialog';
import {NewEditClientComponent} from './new-edit-client/new-edit-client.component';
import {Router} from '@angular/router';
import {UtilService} from '../../helpers/util.service';
import {ScannerService} from 'src/app/services/scanner.service';
import {ImpulseService} from 'src/app/services/impulse.service';
import {CommonService} from '../../services/common.service';
import {DatePipe} from '@angular/common';
import {InfoClientsComponent} from './info-clients/info-clients.component';
import {SessionService} from 'src/app/helpers/session.service';
import {DeindexationService} from 'src/app/services/deindexation.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css'],
})
export class ClientsComponent implements OnInit {
  allClients: any;
  filteredClients = [];
  copyArray: Array<any>;
  /* objeto a enviar al filtro */
  filter = {
    name: '',
    suscribe_date_init: null,
    suscribe_date_end: null,
    expiration_date_init: null,
    expiration_date_end: null,
    active: true,
    plan: '',
    payment: '',
  };
  allPlans = [];

  sortDirection = false;
  paymentsMethods = [
    {
      id: 1,
      name: 'transfer',
    },
    {
      id: 2,
      name: 'stripe',
    },
  ];

  // Deprecated (Variable temporal enduro para limitar el acceso de Formula a scanners 'transform')
  isFormula = true;
  paginatorClass = 'paginator';

  /**
   * manage client information
   * @param clientService
   * @param scannerService
   * @param impulseService
   * @param dialog
   * @param router
   * @param utilService
   * @param commonService
   * @param session
   * @param deindexationService
   * @param datepipe
   */
  constructor(
    public clientService: ClientService,
    public scannerService: ScannerService,
    public impulseService: ImpulseService,
    private dialog: MatDialog,
    private router: Router,
    private utilService: UtilService,
    private commonService: CommonService,
    private session: SessionService,
    public deindexationService: DeindexationService,
    private datepipe: DatePipe
  ) {
  }

  ngOnInit(): void {
    this.isFormula = this.session.getUser()?.permissions?.profile?.FORMULE;
    this.loadTable();
    this.loadCommons();
  }

  /*
load commons services to fill select inputs
* */
  loadCommons(): void {
    this.commonService.listPlans((resp) => {
      this.allPlans = resp.plans;
    });
  }

  /*
   load principal table
    * */
  loadTable(): void {
    this.clientService.list((response) => {
      this.allClients = response;
      this.allClients.sort((a, b) => a.name.localeCompare(b.name));
      this.filteredClients = this.allClients;
    });

  }

  /**
   *   function to open modal
   * @param isNuevo
   * @param isEditable
   * @param client
   * @param componente
   */
  openModal(
    isNuevo: boolean,
    isEditable: boolean,
    client: IClients,
    componente
  ): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      width: '90%',
      data: {
        nuevo: isNuevo,
        editable: isEditable,
        info: client,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
    //this.loadTable();
    });

  }

  /*
 Open a model to create a new Item
 * */
  newItem(): void {
    this.openModal(
      true,
      this.clientService.permissions.save,
      ({} = <IClients>{}),
      NewEditClientComponent
    );
  }

  /**
   * Open a model to check Info client
   * @param id
   */
  infoClient(id: number): void {
    this.clientService.get(id, (response) => {
      //console.log('response client',response)
      this.openModal(
        false,
        this.clientService.permissions.update,
        response,
        InfoClientsComponent
      );
    });

  }

  /**
   *  re direct to scanner module of a client
   * @param uuid
   * @param type
   */
  checkScanner(uuid: string, type: string, id: number): void {

    this.router.navigate([
      'admin/module/scanner/global_view/' + type + '/' + uuid +  '/'  + id,
    ]);
  }

  /**
   *  re direct to impulse module of a client (DEPRECADO)
   * @param uuid
   */

  /*goImpulse(uuid: string): void {
    this.router.navigate([ 'admin/module/impulse'], {queryParams: {uuid}});
  }*/

  /**
   * re direct to deindex module of a client
   * @param uuid
   */
  goDeindex(uuid: any): void {
    this.router.navigate(['admin/module/deindexing/' + uuid]);
  }

  /**
   * change item status (active or deactive)
   * @param row
   */
  toggleActive(row: any): void {
    this.utilService.toggleActivo(row, 'active', 'name', this.clientService);
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
    const tempFilter = {
      name: this.filter.name,
      suscribe_date: {
        init: this.filter.suscribe_date_init ? new Date(this.filter.suscribe_date_init).toLocaleDateString() : null,
        end: this.filter.suscribe_date_end ? new Date(this.filter.suscribe_date_end).toLocaleDateString() : null,
      },
      expiration_date: {
        init: this.filter.expiration_date_init ? new Date(this.filter.expiration_date_init).toLocaleDateString() : null,
        end: this.filter.expiration_date_end ? new Date(this.filter.expiration_date_end).toLocaleDateString() : null,
      },
      active: this.filter.active,
      plan: this.filter.plan,
      payment: this.filter.payment,
    };

    this.filteredClients = this.allClients.filter(
      (client) => {
        // Filter name
        let matchs = (client.name.toLowerCase().includes(tempFilter.name.toLowerCase()) ||
          client.lastname.toLowerCase().includes(tempFilter.name.toLowerCase()));

        // Filter Active
        if (matchs) {
          matchs = client.active === tempFilter.active;
        }

        // Filter Plan
        if (matchs && tempFilter.plan) {
          matchs = client.planActives[0].detail.name === tempFilter.plan;
        }

        // Filter Payment
        if (matchs && tempFilter.payment) {
          matchs = client.planActives[0].external_subscription_platform === tempFilter.payment;
        }

        // Filter susctiptionDate
        if (matchs && tempFilter.suscribe_date.init && tempFilter.suscribe_date.end) {
          matchs = new Date(client.planActives[0].suscribe_date).toLocaleDateString() <=
            tempFilter.suscribe_date.end &&
            new Date(client.planActives[0].suscribe_date).toLocaleDateString() >=
            tempFilter.suscribe_date.init
        }

        // Filter expirationDate
        if (matchs && tempFilter.expiration_date.init && tempFilter.expiration_date.end) {
          // TODO: ??
        }

        return matchs;
      }
    );
  }

  /**
   * clean Filters
   */
  clean(): void {
    this.filter = {
      name: '',
      suscribe_date_init: null,
      suscribe_date_end: null,
      expiration_date_init: null,
      expiration_date_end: null,
      active: true,
      plan: '',
      payment: '',
    };
    this.filteredClients = this.allClients;
  }

  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(colName, true, this.filteredClients, this.sortDirection);
    this.filteredClients = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }

  /**
   * confirm payment
   * @param paybill
   */
  confirmPaybill(paybill: any): void {
    this.clientService.confirmPayment(paybill);
  }
}
