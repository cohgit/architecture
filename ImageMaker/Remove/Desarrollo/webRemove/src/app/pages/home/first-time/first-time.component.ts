import { Component, OnInit } from '@angular/core';
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-first-time',
  templateUrl: './first-time.component.html',
  styleUrls: ['./first-time.component.css']
})
export class FirstTimeComponent implements OnInit {
  images: any;

  /**
   * if a user is first time
   * @param config
   */
  constructor(config: NgbCarouselConfig) {
    // customize default values of carousels used by this component tree
    config.interval = 100000;
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
