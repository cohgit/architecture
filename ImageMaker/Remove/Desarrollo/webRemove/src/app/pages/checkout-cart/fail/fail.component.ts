import {Component, OnInit, Renderer2} from '@angular/core';
import {TranslateHelperService} from "../../../helpers/translate-helper.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-fail',
  templateUrl: './fail.component.html',
  styleUrls: ['./fail.component.css']
})
export class FailComponent implements OnInit {
  language: string;
  checkParams: any = {};

  /**
   * if something goes wrong in the payment goes to the screen
   * @param translateHelper
   * @param renderer
   * @param activatedRoute
   * @param router
   */
  constructor(public translateHelper: TranslateHelperService, private renderer: Renderer2,
              private activatedRoute: ActivatedRoute, private router: Router) {
    this.language = translateHelper.getTransLanguage();
    this.activatedRoute.data.subscribe(data => {
      this.checkParams = data;
    });

    this.renderer.addClass(document.body,  this.checkParams.class);
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.router.navigate(['/client/checkout']);
    }, 10000);
  }

  /**
   * change language
   */
  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }
}
