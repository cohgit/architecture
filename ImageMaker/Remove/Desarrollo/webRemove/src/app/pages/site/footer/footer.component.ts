import {Component, OnInit} from '@angular/core';
import {UtilService} from '../../../helpers/util.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  /**
   * footer for landing page
   * @param utilService
   */
  constructor(public utilService: UtilService) {

  }

  ngOnInit(): void {
  }

}
