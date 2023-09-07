import {Component, OnInit, Renderer2} from '@angular/core';
import {TranslateHelperService} from "../../../helpers/translate-helper.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {UtilService} from "../../../helpers/util.service";
import {WordpressApiService} from "../../../services/wordpress-api.service";
import {XternalCommonService} from "../../../services/xternal-common.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-unsubscribe',
  templateUrl: './unsubscribe.component.html',
  styleUrls: ['./unsubscribe.component.css']
})
export class UnsubscribeComponent implements OnInit {
  language: string;
  checkParams: any = {};
  contact: any = {};
  allPlans: any = [];

  /**
   * manage withdrawal form
   * @param translateHelper
   * @param renderer
   * @param activatedRoute
   * @param router
   * @param dialog
   * @param utilService
   * @param worpressService
   * @param externalServices
   */
  constructor(
    public translateHelper: TranslateHelperService,
    private renderer: Renderer2,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    public utilService: UtilService,
    private worpressService: WordpressApiService,
    private externalServices: XternalCommonService
  ) {
    this.language = translateHelper.getTransLanguage();
    this.activatedRoute.data.subscribe((data) => {
      this.checkParams = data;
    });

    this.renderer.addClass(document.body, this.checkParams.class);
  }

  ngOnInit(): void {

  }


  /**
   *
   * @param form Envía el formulario de contacto
   */
  sendContact(form: NgForm, tittle: string): void {
    let sendForm: any;
    if (form.valid) {
      sendForm = {
        "title": tittle,
        "atts": [
          {"key": "Nombre del Contacto", "value": form.value.name},
          {"key": "Email", "value": form.value.email},
          {"key": "Mensaje", "value": form.value.message}
        ]
      };

      this.contact = {};
      this.externalServices.contact(sendForm);
    }
  }


}
