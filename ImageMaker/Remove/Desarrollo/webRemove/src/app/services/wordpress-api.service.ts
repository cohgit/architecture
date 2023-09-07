import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class WordpressApiService {
  private SUGGESTED_API = 'https://www.removegroup.com/wp-json/wp/v2/posts/?categories=6&_embed';

  /**
   * Service to connect to the removegroup website to synchronize the Wordpress news with the landing page
   * @param http
   */
  constructor(private http: HttpClient) {}

  /**
   *
   * @param successFuncion
   */
  getNews(successFuncion?: Function) {
    this.http.get(this.SUGGESTED_API).subscribe(
      (result) => {
        if (successFuncion) {
          successFuncion(result);
        }
      },
      (error) => {
        console.error('Error at google suggest service:', error);
      }
    );
  }
}

/**
 *
 * @param result
 */
function cleanResult(result: any): string[] {
  let news: string[] = [];

  if (Array.isArray(result)) {
    result.forEach((firstLevel) => {
      if (Array.isArray(firstLevel)) {
        firstLevel.forEach((trySuggestion) => {
          if (
            trySuggestion &&
            typeof trySuggestion === 'string' &&
            trySuggestion.length > 0 &&
            !news.includes(trySuggestion)
          ) {
            news.push(trySuggestion);
          }
        });
      }
    });
  }

  return news;
}
