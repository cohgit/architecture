import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GoogleApiService {
  private SUGGESTED_API = '/complete/search';

  /**
   * Deprecado
   * @param http
   */
  constructor(private http: HttpClient) {
  }

  //TODO: Deprecado, Eliminar
  suggestKeyword(q: string, lang: string, country: string, successFuncion?: Function) {
    let fullPath = this.SUGGESTED_API + "?client=chrome&q=" + q + "&hl=" + lang + "&max=2&gl=" + country;
    this.http.get(fullPath).subscribe(result => {
      if (successFuncion) {
        successFuncion(cleanResult(result));
      }
    }, error => {
      console.error("Error at google suggest service:", error);
    });
  }
}

function cleanResult(result: any): string[] {
  let suggestions: string[] = [];

  if (Array.isArray(result)) {
    result.forEach(firstLevel => {
      if (Array.isArray(firstLevel)) {
        firstLevel.forEach(trySuggestion => {
          if (trySuggestion && typeof trySuggestion === 'string' && trySuggestion.length > 0 && !suggestions.includes(trySuggestion)) {
            suggestions.push(trySuggestion);
          }
        })
      }
    })
  }

  return suggestions;
}

