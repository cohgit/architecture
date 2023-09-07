 import {Component, OnInit, Renderer2} from '@angular/core';
import {TranslateHelperService} from "../helpers/translate-helper.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SuscriptionService} from '../services/suscription.service';

@Component({
  selector: 'app-verified-email',
  templateUrl: './verified-email.component.html',
  styleUrls: ['./verified-email.component.css']
})
export class VerifiedEmailComponent implements OnInit {
  language: string;
  checkParams: any = {};
  error = false;
  success = false;
  message = '';

  constructor(public translateHelper: TranslateHelperService, private renderer: Renderer2,
              private activatedRoute: ActivatedRoute, private router: Router, private subscriptionService: SuscriptionService) {
    this.language = translateHelper.getTransLanguage();
    this.activatedRoute.data.subscribe(data => {
      this.checkParams = data;
    });

    this.renderer.addClass(document.body, this.checkParams.class);
  }

  ngOnInit(): void {
    this.subscriptionService.verifyEmail(this.activatedRoute.snapshot.paramMap.get('uuid'), response => {
      this.success = true;
      this.message = 'message.verified_email';
      setTimeout(() => {
        this.router.navigate(['/' + this.checkParams.service_key + '/login']);
      }, 10000);
    }, {
      errorFunction: error => {
        this.message = error.tag;
        this.error = true;
      }
    });
  }

  changeLanguage(): void {
    this.translateHelper.translate.use(this.language);
  }
}
