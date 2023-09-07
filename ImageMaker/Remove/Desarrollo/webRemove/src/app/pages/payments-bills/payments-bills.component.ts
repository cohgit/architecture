import {Component, OnInit} from '@angular/core';
import {SessionService} from 'src/app/helpers/session.service';
import {UtilService} from 'src/app/helpers/util.service';
import {AccountConfigService} from 'src/app/services/account-config.service';

@Component({
  selector: 'app-payments-bills',
  templateUrl: './payments-bills.component.html',
  styleUrls: ['./payments-bills.component.css']
})
export class PaymentsBillsComponent implements OnInit {
  clientData: any;
  sortDirection = false;
  copyArray: Array<any>;

  /**
   * table to shows clients payments
   * @param accountConfigService
   * @param session
   * @param utilService
   */
  constructor(private accountConfigService: AccountConfigService, public session: SessionService, public utilService: UtilService) {
  }

  ngOnInit(): void {
    this.loadTable();
  }

  /*
  load principal table
   * */
  loadTable(): void {
    this.accountConfigService.getPaymentPortal(resp => this.clientData = resp);
    //TODO: llamar a servicio de pagos
    /*
    this.allPayments = [{
      payment_date: new Date(),
      paymentMethods: 'transfer',
      amount: 99,
      plan: {
        id: 1,
        name: 'nombre del plan'
      }
    },
    {
      payment_date: new Date(),
      paymentMethods: 'stripe',
      amount: 100,
      plan: {
        id: 1,
        name: 'nombre del plan'
      }
    }]*/
  }

  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   * Opens a stripe payment portal
   */
  openStripe() {
    this.session.goToNewTab(this.clientData.paymentPortal);
  }
}
