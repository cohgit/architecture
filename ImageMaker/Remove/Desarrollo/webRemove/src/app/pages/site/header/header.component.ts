import {Component, OnInit} from '@angular/core';
import {UtilService} from '../../../helpers/util.service';
import {TranslateHelperService} from "../../../helpers/translate-helper.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  language: string;
  navbarOpen = false;

  /**
   * top menu for landing page
   * @param utilService
   * @param translateHelper
   */
  constructor(public utilService: UtilService, public translateHelper: TranslateHelperService) {
    this.language = this.translateHelper.getTransLanguage();
  }

  ngOnInit(): void {
  }

  /**
   * Abre o cierra el menú responsive
   */
  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

  /**
   * Cambia el idioma entre español e inglés
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }
}
