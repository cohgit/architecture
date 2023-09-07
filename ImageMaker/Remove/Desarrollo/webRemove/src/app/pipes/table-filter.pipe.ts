import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tableFilter'
})
export class TableFilterPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
