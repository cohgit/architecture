import { Injectable } from '@angular/core';
import {
  CanLoad,
  CanActivate,
  Route,
  UrlSegment,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router
} from '@angular/router';
import { Observable } from 'rxjs';
import {SessionService} from './session.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentGuard implements CanActivate  {
  restrictedAccess: boolean;
  constructor(public session: SessionService,  private router: Router) {
     this.restrictedAccess = this.session.isRestrictedAccess();
  }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (!this.restrictedAccess) {
      return true;
    } else {
      this.router.navigate(['client/module/redirect']);
      return false;
    }
  }
}
