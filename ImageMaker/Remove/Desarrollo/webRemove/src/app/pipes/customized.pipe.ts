import {Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({
  name: 'customized'
})
export class CustomizedPipe implements PipeTransform {
  customizedSymmbol: any;

  constructor(private _sanitizer: DomSanitizer) {
  }

  transform(item: any): any {
    if (item) {
      this.customizedSymmbol = '<i class="las la-check-circle colorVerde la-2x"></i> ';
    } else {
      this.customizedSymmbol = '';
    }
    return this._sanitizer.bypassSecurityTrustHtml(this.customizedSymmbol);
  }


}
