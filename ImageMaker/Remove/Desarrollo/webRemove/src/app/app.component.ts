import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {MediaMatcher} from "@angular/cdk/layout";
import {MatSidenav} from "@angular/material/sidenav";
import { XternalCommonService } from './services/xternal-common.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  isOutside = true;
  title = 'remove-app';
  options = {
    bottom: 0,
    fixed: true,
    top: 0
  };
  mobileQuery: MediaQueryList;

  private _mobileQueryListener: () => void;
  constructor(private router: Router, private activatedRoute: ActivatedRoute, changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
      private commonsService: XternalCommonService){
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit() {
    /*if (environment.ssl) { // Deprecated
      this.commonsService.certificate(window.location.href);
    }*/
    
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isOutside = this.activatedRoute.firstChild.snapshot.data.isOutside;
      }
    });
  }

  close(sidenav: MatSidenav) {
    sidenav.close();
  }
}
