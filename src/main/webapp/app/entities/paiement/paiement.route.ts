import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Paiement } from 'app/shared/model/paiement.model';
import { PaiementService } from './paiement.service';
import { PaiementComponent } from './paiement.component';
import { PaiementDetailComponent } from './paiement-detail.component';
import { PaiementUpdateComponent } from './paiement-update.component';
import { PaiementDeletePopupComponent } from './paiement-delete-dialog.component';
import { IPaiement } from 'app/shared/model/paiement.model';

@Injectable({ providedIn: 'root' })
export class PaiementResolve implements Resolve<IPaiement> {
  constructor(private service: PaiementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaiement> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Paiement>) => response.ok),
        map((paiement: HttpResponse<Paiement>) => paiement.body)
      );
    }
    return of(new Paiement());
  }
}

export const paiementRoute: Routes = [
  {
    path: '',
    component: PaiementComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paiements'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaiementDetailComponent,
    resolve: {
      paiement: PaiementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paiements'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaiementUpdateComponent,
    resolve: {
      paiement: PaiementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paiements'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaiementUpdateComponent,
    resolve: {
      paiement: PaiementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paiements'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paiementPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PaiementDeletePopupComponent,
    resolve: {
      paiement: PaiementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paiements'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
