import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'feelingColors'
})
export class FeelingColorsPipe implements PipeTransform {
  tagFeel: any;

  constructor(private _sanitizer: DomSanitizer) { }

  /**
   * recive an object and return an icon with colors
   * goog = green
   * bad = red
   * neutral = blue
   * not applied = gray
   * @param feel
   */
  transform(feel: any): any {
    if (feel) {
      if (feel.GOOD) {
        this.tagFeel = '<i class="fas fa-circle colorVerde espDer5" ></i> ';
      } else
      if (feel.BAD) {
        this.tagFeel = '<i class="fas fa-circle colorRojo espDer5" ></i> ';
      } else
      if (feel.NEUTRAL) {
        this.tagFeel = '<i class="fas fa-circle colorAzul espDer5" ></i> ';
      } else
      if (feel.NOT_APPLIED) {
        this.tagFeel = '<i class="fas fa-circle colorGris espDer5" ></i> ';
      }   else {
        this.tagFeel = '<i class="fas fa-question-circle espDer5"></i> ' + feel.tag;
      }
    }
    return this._sanitizer.bypassSecurityTrustHtml(this.tagFeel);
  }

}
