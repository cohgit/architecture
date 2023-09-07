import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'highlight'
})
export class HighlightPipe implements PipeTransform {
  /**
   * Recives a snippet or tittle and highlight a match word
   * g modifier: global. All matches (don't return on first match)
   *
   * i modifier: insensitive. Case insensitive match (ignores case of [a-zA-Z])
   * @param value
   * @param args
   */
  transform(value: any, args: any): any {
    if (!args) {
      return value;
    }
    for (const text of args) {
      const reText = new RegExp(text.word, 'gi');
      value = value.replace(reText, '<span class=\'highlight-search-text\'>' + text.word + '</span>');


    }
    return value;
  }

}
