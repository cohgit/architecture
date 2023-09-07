import { Component, OnInit } from '@angular/core';
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-help-page',
  templateUrl: './help-page.component.html',
  styleUrls: ['./help-page.component.css']
})
export class HelpPageComponent implements OnInit {
  images: any;

  /**
   * it will get all the helpers file for the users (tutorials, videos, etc...)
   * @param config
   */
  constructor(config: NgbCarouselConfig) {
    // customize default values of carousels used by this component tree
    config.interval = 10000;
    config.wrap = false;
    config.showNavigationArrows = false;


    this.images = [
      '../../../../assets/img/slider1.jpg',
      '../../../../assets/img/slider2.jpg'
    ];
  }

  ngOnInit(): void {
  }

}
