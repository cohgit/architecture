import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { CommonService } from '../../../services/common.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ClientService } from '../../../services/client.service';
import { TranslateHelperService } from '../../../helpers/translate-helper.service';
import { UtilService } from '../../../helpers/util.service';
import { ScannerService } from '../../../services/scanner.service';
import { SessionService } from '../../../helpers/session.service';
import { ScannerCommentsService } from '../../../services/scanner-comments.service';
import { newArray } from '@angular/compiler/src/util';

@Component({
  selector: 'app-comment-scanner',
  templateUrl: './comment-scanner.component.html',
  styleUrls: ['./comment-scanner.component.css'],
})
export class CommentScannerComponent implements OnInit {
  info: any;
  lastComment: any;
  comment = '';
  constructor(
    private formBuilder: FormBuilder,
    private commonService: CommonService,
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<CommentScannerComponent>,
    public translate: TranslateService,
    private scannerService: ScannerService,
    private session: SessionService,
    private commentService: ScannerCommentsService,
    public translateHelper: TranslateHelperService,
    public utilService: UtilService
  ) {
    this.info = data.info;
  }

  ngOnInit(): void {
    this.session.getUser();
    this.loadComments();
  }

  onConfirmClick(comment: any) {
    console.log('como llega', comment);
    if (comment !== '') {
      const newComment = {
        id_scanner: this.info.id,
        comment: this.utilService.cleanHTML(this.comment),
      };
      console.log('como se va', newComment);
      this.commentService.save(newComment, (response) => {
        this.dialogRef.close(true);
      });
    } else {
      this.utilService.showNotification('error.incomplete.form', 'danger');
    }
  }
  /**
   * load  comment from scanner
   */
  loadComments() {
    let allComment = [];
    this.commentService.listComments(this.info.id, (response) => {
      allComment = response;
      if (allComment.length !== 0) {
        this.lastComment = allComment[0];
      }
    });
  }
}
