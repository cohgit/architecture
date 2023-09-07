import {Component, OnInit, Renderer2} from '@angular/core';
import {UtilService} from "../../helpers/util.service";
import {TranslateHelperService} from "../../helpers/translate-helper.service";

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {
  /**
   * it shows whrn something goes wrong or 404
   * @param renderer
   * @param utilService
   * @param translateHelper
   */
  constructor(private renderer: Renderer2, private utilService: UtilService, public translateHelper: TranslateHelperService) {
    this.renderer.addClass(document.body, 'login-body');
  }

  ngOnInit(): void {
  }

  /**
   * go back in the navigation history
   */
  back(){
    this.utilService.back();
  }
}
