import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/helpers/session.service';
import { TranslateHelperService } from 'src/app/helpers/translate-helper.service';
import {MatDialog} from "@angular/material/dialog";
import {FirstTimeComponent} from "./first-time/first-time.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  /**
   * first view after login
   * @param session
   * @param translateHelper
   * @param dialog
   */
  constructor(private session: SessionService, public translateHelper: TranslateHelperService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.translateHelper.translate.use(this.session.getUserLanguage());
    if (this.session.getUser().first_time && !this.session.getUser().permissions.profile.ADMIN){
      this.dialog.open(FirstTimeComponent, {
        width: '70%',
      });
    }
  }
}
