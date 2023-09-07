import {Component, OnInit, Renderer2} from '@angular/core';
import {NgForm} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';
import {UtilService} from 'src/app/helpers/util.service';
import {WordpressApiService} from 'src/app/services/wordpress-api.service';
import {XternalCommonService} from 'src/app/services/xternal-common.service';

@Component({
  selector: 'app-demo',
  templateUrl: './demo.component.html',
  styleUrls: ['./demo.component.css']
})
export class DemoComponent implements OnInit {
  language: string;
  checkParams: any = {};
  contact: any = {};
  company: any = {};
  tabs = {
    first: false,
    second: true,
    third: false
  }
  news: any = [];
  allPlans: any = [];
  isLineal: true;
  typeScan = [
    'delete', 'detect', 'transform'];

  /**
   * demo information in lading page
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
          {"key": "Teléfono", "value": form.value.phone},
          {"key": "Servicios Solicitados", "value": form.value.services.toString()},
          {"key": "Mensaje", "value": form.value.message}
        ]
      };

      this.contact = {};
      this.externalServices.contact(sendForm);
    }
  }

  /**
   *  function to open modal
   * @param isNuevo
   * @param isEditable
   * @param plan
   * @param componente
   */
  openModal(data: any, componente: any): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      width: '90%',
      data: {
        info: data,
      },
    });
  }


}
