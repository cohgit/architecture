import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from "@angular/forms";
import {UtilService} from "../../helpers/util.service";
import {CommonService} from "../../services/common.service";
import {TranslateService} from "@ngx-translate/core";
import { AccountConfigService } from 'src/app/services/account-config.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-custom-plan',
  templateUrl: './custom-plan.component.html',
  styleUrls: ['./custom-plan.component.css']
})
export class CustomPlanComponent implements OnInit {
  info: any = {};
  allTypes = [];

  /**
   * Information of a personalized plan for a client
   * @param formBuilder
   * @param utilService
   * @param configService
   * @param commonService
   * @param translate
   * @param dialogRef
   */
  constructor(private formBuilder: FormBuilder, public utilService: UtilService, private configService: AccountConfigService,
              private commonService: CommonService, public translate: TranslateService, private dialogRef: MatDialogRef<CustomPlanComponent>) {
  }

  ngOnInit(): void {
    this.loadCommons();
  }
  /*
load commons services to fill all select inputs
* */
  loadCommons(): void {
    this.commonService.listSearchTypes(response => {
      this.allTypes = response;
    });
  }

  /**
   *
   * @param customForm
   */
  onConfirmClick(customForm: NgForm) {
    this.configService.requestQuote(customForm.value, () => {
    });
    this.dialogRef.close(true);

  }
}
