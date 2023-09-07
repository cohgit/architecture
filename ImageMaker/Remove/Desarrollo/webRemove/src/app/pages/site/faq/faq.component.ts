import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.css']
})
export class FaqComponent implements OnInit {

  faqs = [];

  /**
   * FAQ for landing page
   */
  constructor() {
  }

  ngOnInit(): void {
    this.faqs = [{
      name: 'elimination'
    }, {
      name: 'services'
    }, {
      name: 'deindex'
    }, {
      name: 'request'
    }, {
      name: 'european'
    }, {
      name: 'services_company'
    }, {
      name: 'services_person'
    }, {
      name: 'google'
    }, {
      name: 'warranty'
    }, {
      name: 'content'
    }, {
      name: 'duration'
    }, {
      name: 'type'
    }, {
      name: 'sections'
    }, {
      name: 'alert'
    }, {
      name: 'watch'
    }, {
      name: 'new'
    }];
  }


}
