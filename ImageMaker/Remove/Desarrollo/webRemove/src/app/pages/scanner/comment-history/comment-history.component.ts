import {Component, Input, OnInit} from '@angular/core';
import {UtilService} from "../../../helpers/util.service";


@Component({
  selector: 'app-comment-history',
  templateUrl: './comment-history.component.html',
  styleUrls: ['./comment-history.component.css']
})
export class CommentHistoryComponent implements OnInit {

  @Input() allComments: any = {};


  comment: any;
  constructor(public utilService: UtilService) { }

  ngOnInit(): void {
    this.comment = this.allComments[0];
  }

}
