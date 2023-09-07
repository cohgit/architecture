import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {UtilService} from 'src/app/helpers/util.service';
import {IUsers} from 'src/app/interfaces/IUsers';
import {CommonService} from 'src/app/services/common.service';
import {UserService} from 'src/app/services/user.service';

@Component({
  selector: 'app-new-edit-user',
  templateUrl: './new-edit-user.component.html',
  styleUrls: ['./new-edit-user.component.css']
})
export class NewEditUserComponent implements OnInit {
  isNuevo = false;
  isEditable = false;
  user: IUsers = {name: '', profileDef: {}, clients: []};
  endingButton = false;
  allProfiles: any;
  allClients: any;
  fltClient: any;
  placeholderBuscar: '';
  noResults: '';
  checkmail: any;
  addClient: any;

  /**
   * update or save user information
   * @param formBuilder
   * @param commonService
   * @param userService
   * @param data
   * @param dialogRef
   * @param translate
   */
  constructor(private formBuilder: FormBuilder,
              private commonService: CommonService,
              private userService: UserService,
              public util: UtilService,
              @Inject(MAT_DIALOG_DATA) private data: any,
              private dialogRef: MatDialogRef<NewEditUserComponent>,
              public translate: TranslateService) {
    this.isNuevo = data.nuevo;
    this.isEditable = data.editable;
    this.user = data.info;
  }

  /**
   *
   */
  ngOnInit(): void {
    this.placeholderBuscar = this.translate.instant('placeholder.search');
    this.noResults = this.translate.instant('placeholder.no_result');
    this.loadCommons();
  }

  /*
load commons services to fill all select inputs
* */
  loadCommons(): void {
    this.commonService.listProfiles(response => this.allProfiles = response);
    this.commonService.listClients(response => {
      this.allClients = response;
      this.cleanAllClientList();
      this.fltClient = this.allClients.slice();
    });
  }

  /***
   * Clean client list
   */
  cleanAllClientList(): void {
    if (this.user?.clients) {
      this.user.clients.forEach(client => {
        this.util.removefromArrayByKey(this.allClients, client, 'id');
      });
    }
  }

  /**
   * @param form
   *
   call services update or save as appropriate
   * */
  onConfirmClick(form: NgForm): void {
    this.endingButton = true;
    if (form.valid) {
      const data = Object.assign(this.user, form.value);

      if (data.profileDef.ADMIN || data.profileDef.FACTURATION) {
        data.clients = [];
      }

      if (this.isNuevo) {
        this.userService.save(data, response => {
          this.user = response;
          this.isNuevo = false;
          this.dialogRef.close(true);
        });
      } else {
        this.userService.update(data, response => {
          this.user = response;
          this.dialogRef.close(true);
        });
      }
    }
  }

  /**
   * Add an Item to 'user.clients' array and remove it from 'allClients' array.
   * @param adItem
   */
  addItem(client: any): void {
    if (client) {
      if (!this.user.clients.includes(client)) {
        this.user.clients.push(client);
        this.util.removefromArrayByKey(this.allClients, client, 'id');
      }
    }
  }

  /**
   * Remove Item from 'user.clients' list and add it to 'allClients' array
   * @param data
   */
  removeItem(client: any): void {
    if (client) {
      this.allClients.push(client);
      this.util.removefromArrayByKey(this.user.clients, client, 'id');
    }
  }
}
