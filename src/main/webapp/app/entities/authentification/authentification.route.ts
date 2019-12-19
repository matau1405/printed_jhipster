import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Authentification } from 'app/shared/model/authentification.model';
import { AuthentificationService } from './authentification.service';
import { AuthentificationComponent } from './authentification.component';
import { AuthentificationDetailComponent } from './authentification-detail.component';
import { AuthentificationUpdateComponent } from './authentification-update.component';
import { AuthentificationDeletePopupComponent } from './authentification-delete-dialog.component';
import { IAuthentification } from 'app/shared/model/authentification.model';

@Injectable({ providedIn: 'root' })
export class AuthentificationResolve implements Resolve<IAuthentification> {
  constructor(private service: AuthentificationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAuthentification> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Authentification>) => response.ok),
        map((authentification: HttpResponse<Authentification>) => authentification.body)
      );
    }
    return of(new Authentification());
  }
}

export const authentificationRoute: Routes = [
  {
    path: '',
    component: AuthentificationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Authentifications'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AuthentificationDetailComponent,
    resolve: {
      authentification: AuthentificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Authentifications'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AuthentificationUpdateComponent,
    resolve: {
      authentification: AuthentificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Authentifications'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AuthentificationUpdateComponent,
    resolve: {
      authentification: AuthentificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Authentifications'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const authentificationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AuthentificationDeletePopupComponent,
    resolve: {
      authentification: AuthentificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Authentifications'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
