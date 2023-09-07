import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'visibility'
})
export class VisibilityPipe implements PipeTransform {

  vis: any;

  /** Visibilidad en resultados independientes: del 1 al 3: muy alto - 4 a 7:alto - 7 a 10: medio 11 a 13: baja - de 14 al infinito: nula
   *
   * @param item
   */
  transform(item: number): any {
    if (item) {
      if (item >= 1 && item <= 3) {
        this.vis = 'label.very_high';
      }
      if (item >= 4 && item <= 7) {
        this.vis = 'label.high';
      }
      if (item >= 8 && item <= 10) {
        this.vis = 'label.medium';
      }
      if (item >= 11 && item <= 13) {
        this.vis = 'label.low';
      }
      if (item >= 14) {
        this.vis = 'label.null';
      }
    }
    return this.vis;
  }
}
