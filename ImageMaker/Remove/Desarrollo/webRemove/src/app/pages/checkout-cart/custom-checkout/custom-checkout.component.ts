import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {XternalCommonService} from '../../../services/xternal-common.service';
import {UtilService} from "../../../helpers/util.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-custom-checkout',
  templateUrl: './custom-checkout.component.html',
  styleUrls: ['./custom-checkout.component.css']
})
export class CustomCheckoutComponent implements OnInit {
  info: any = {};
  contact: any = {};
  typeScan = [
    'delete', 'detect', 'transform'];

  /**
   * modal to ask for a customized plan
   * @param externalServices
   * @param utilService
   * @param dialog
   * @param dialogRef
   */
  constructor(private externalServices: XternalCommonService, public utilService: UtilService,
              private dialog: MatDialog, private dialogRef: MatDialogRef<CustomCheckoutComponent>) {
  }

  ngOnInit(): void {

  }

  /**
   * send an email with the information
   * @param form
   * @param tittle
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
      this.externalServices.contact(sendForm);
      this.dialogRef.close(true);
    }
  }

  /**
   * open a dialog modal
   * @param data
   * @param componente
   */
  openModal(data: any, componente: any): void {
    const dialogRef = this.dialog.open(componente, {
      disableClose: true,
      closeOnNavigation: true,
      width: '90%',
      data: {
        info: data,
      },
    });
  }

}
