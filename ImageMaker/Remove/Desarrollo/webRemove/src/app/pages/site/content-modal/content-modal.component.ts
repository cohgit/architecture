import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-content-modal',
  templateUrl: './content-modal.component.html',
  styleUrls: ['./content-modal.component.css']
})
export class ContentModalComponent implements OnInit {
  info: any = {};

  /**
   * information in wordpress (news)
   * @param data
   */
  constructor( @Inject(MAT_DIALOG_DATA) private data: any,) {
    this.info = data.info;
   }

  ngOnInit(): void {
  }

}
