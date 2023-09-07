import {Component, Inject, OnInit} from '@angular/core';
import {StepperSelectionEvent} from "@angular/cdk/stepper";
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {UtilService} from "../../../helpers/util.service";

@Component({
  selector: 'app-tracking-deindexing',
  templateUrl: './tracking-deindexing.component.html',
  styleUrls: ['./tracking-deindexing.component.css']
})
export class TrackingDeindexingComponent implements OnInit {
  tracks = [];

  /**
   * shows history of a desindexation
   * @param data
   * @param utilService
   */
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, public utilService: UtilService) {
  }

  ICONS = {
    created: 'las la-user-edit',
    sent: 'lab la-telegram-plane',
    processing: 'las la-redo-alt',
    approved: 'las la-thumbs-up',
    rejected: 'las la-thumbs-down',
    deleted: 'las la-trash',
    ESTADO_DEFAULT: 'fa fa-info-circle'
  };
  isFalse = false;
  totalItems = 0;

  ngOnInit(): void {
    this.tracks = this.data.info;
    this.totalItems = this.tracks.length - 1;
  }

  /**
   * disable a preview step
   * @param event
   */
  stepChanged(event: StepperSelectionEvent) {
    if (event.previouslySelectedIndex > event.selectedIndex) {
      event.previouslySelectedStep.interacted = false;
    }
  }
}
