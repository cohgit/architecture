import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';
import {XternalCommonService} from 'src/app/services/xternal-common.service';
import {PoliticsModalComponent} from '../politics-modal/politics-modal.component';

@Component({
  selector: 'app-call-you',
  templateUrl: './call-you.component.html',
  styleUrls: ['./call-you.component.css']
})
export class CallYouComponent implements OnInit {
  contact: any = {};

  /**
   * we call you landing page
   * @param translateHelper
   * @param dialogRef
   * @param dialog
   * @param externalServices
   */
  constructor(public translateHelper: TranslateHelperService,
              private dialogRef: MatDialogRef<CallYouComponent>, private dialog: MatDialog,
              private externalServices: XternalCommonService) {
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
          {"key": "Teléfono", "value": form.value.phone}
        ]
      };
      this.externalServices.contact(sendForm);
    }
  }

  /**
   *  function to open modal
   * @param data
   * @param componente
   */
  openModal(data: any, componente: any): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        info: data,
      },
    });
  }

  /**
   * Abre un modal con las políticas de privacidad
   */
  openPolitics() {
    this.openModal('', PoliticsModalComponent);
  }
}
