import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";

@Component({
  selector: 'app-politics-modal',
  templateUrl: './politics-modal.component.html',
  styleUrls: ['./politics-modal.component.css']
})
export class PoliticsModalComponent implements OnInit {
  info: any = {};

  /**
   * show politics information in a modal
   * @param data
   * @param router
   * @param dialogRef
   */
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private router: Router,
              private dialogRef: MatDialogRef<PoliticsModalComponent>) {
    this.info = data.info;
  }

  ngOnInit(): void {
  }

  /**
   * navega hasta el formulario de desestimiento
   */
  goUnsuscribe(): void {
    this.router.navigate(['/client/site/unsuscribe']);
    this.dialogRef.close(null);
  }
}
