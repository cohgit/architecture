import { Pipe, PipeTransform } from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({
  name: 'cardBrands'
})
export class CardBrandsPipe implements PipeTransform {
  tagLogo: any;

  constructor(private sanitizer: DomSanitizer) {
  }

  /**
   *  recive a string and return a type of credit crad
   * @param tagLogo
   */
  transform(tagLogo: string): any {
    if (tagLogo !== null){
      switch (tagLogo) {
        case 'MASTER':
          this.tagLogo = '<img src="../../assets/img/mastercard.png">';
          break;
        case 'VISA':
          this.tagLogo = '<img src="../../assets/img/visa.png">';
          break;
        default:
          this.tagLogo = tagLogo;
          break;
      }
    }
    else{
      this.tagLogo = '-';
    }


    return this.sanitizer.bypassSecurityTrustHtml(this.tagLogo);
  }

}
