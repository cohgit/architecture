import {Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';

@Pipe({
  name: 'paymentsMethods'
})
export class PaymentsMethodsPipe implements PipeTransform {

  paymentSymbol: any;

  constructor(private _sanitizer: DomSanitizer) {
  }

  /**
   * recive a string and return an icon of payment
   * @param item
   */
  transform(item: any): any {
    if (item) {
      if (item === 'stripe') {
        this.paymentSymbol = '<i class="fab fa-stripe la-2x" title="Stripe"> ';
      } else if (item === 'transfer') {
        this.paymentSymbol = '<i class="las la-money-check-alt la-2x" title="Transferencia"></i> ';
      } else {
        this.paymentSymbol = '<i class="las la-question-circle espDer5"></i> ' + item.tag;
      }
    }
    return this._sanitizer.bypassSecurityTrustHtml(this.paymentSymbol);
  }
}
