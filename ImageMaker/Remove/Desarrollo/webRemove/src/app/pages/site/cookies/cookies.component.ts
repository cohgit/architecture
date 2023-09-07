import {Component, OnInit} from '@angular/core';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';
import {UtilService} from 'src/app/helpers/util.service';
import {NgcCookieConsentService, NgcStatusChangeEvent} from "ngx-cookieconsent";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-cookies',
  templateUrl: './cookies.component.html',
  styleUrls: ['./cookies.component.css'],
})
export class CookiesComponent implements OnInit {
  language: string;
  private popupOpenSubscription: Subscription;
  private popupCloseSubscription: Subscription;
  private initializeSubscription: Subscription;
  private statusChangeSubscription: Subscription;
  private revokeChoiceSubscription: Subscription;
  private noCookieLawSubscription: Subscription;

  /**
   * maange cookies in lading page
   * @param translateHelper
   * @param utilService
   * @param ccService
   */
  constructor(
    public translateHelper: TranslateHelperService,
    public utilService: UtilService,
    private ccService: NgcCookieConsentService
  ) {
  }

  ngOnInit(): void {
  }

  /**
   * delete consent cookie
   */
  deleteConsentCookie() {
    this.statusChangeSubscription = this.ccService.statusChange$.subscribe(
      (event: NgcStatusChangeEvent) => {
        event.status = 'deny';
      }
    );
  }
}
