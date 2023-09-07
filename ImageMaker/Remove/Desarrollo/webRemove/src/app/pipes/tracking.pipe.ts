import {Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({
  name: 'tracking'
})
export class TrackingPipe implements PipeTransform {
  ratingSymmbol: any;

  constructor(private _sanitizer: DomSanitizer) {
  }

  /**
   * recive a string and return an icon about the evolution, up, down or same
   * @param item
   */
  transform(item: any): any {
    if (item) {
      if (item === 'UP') {
        this.ratingSymmbol = '<i class="las la-long-arrow-alt-up colorGris espDer5 la-2x" ></i> ';
      } else if (item === 'DOWN') {
        this.ratingSymmbol = '<i class="las la-long-arrow-alt-down colorGris espDer5 la-2x" ></i> ';
      } else if (item === 'SAME') {
        this.ratingSymmbol = '<i class="las la-equals colorGris espDer5 la-2x" ></i> ';
      } else {
        this.ratingSymmbol = '<i class="las la-question-circle espDer5 la-2x"></i> ' + item.tag;
      }
    }
    return this._sanitizer.bypassSecurityTrustHtml(this.ratingSymmbol);
  }
}
