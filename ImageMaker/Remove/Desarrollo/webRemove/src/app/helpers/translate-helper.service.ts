import { Injectable } from '@angular/core';

import { TranslateService } from '@ngx-translate/core';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class TranslateHelperService {
  public languages = ['es', 'en'];
  private selectedLanguage = this.languages[0];

  /**
   * Configure the language change in the system
   * @param translate
   * @param session
   */
  constructor(public translate: TranslateService, private session: SessionService) {
    let lang = null;

    try {
      lang = this.session.getUserLanguage();
    } catch (e) {}

    translate.setDefaultLang(this.selectedLanguage);
    translate.addLangs(this.languages);
    translate.use(lang ? lang : this.selectedLanguage);
  }

  /**
   * Assigns the host language of the system
   * @param language
   */
  setTransLanguage(language: string){
    this.selectedLanguage = language;
    this.translate.use(this.selectedLanguage);
  }

  /**
   * Receives the language to use in the system.
   */
  getTransLanguage(): string{
    return this.selectedLanguage;
  }

  /**
   * Translate a text string, via a tag in the language configuration JSON
   * @param messageKey
   */
  instant(messageKey: string): string {
    return this.translate.instant(messageKey);
  }
}
