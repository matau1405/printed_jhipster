import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Panier } from 'app/shared/model/panier.model';
import { PanierService } from './panier.service';
import { PanierComponent } from './panier.component';
import { PanierDetailComponent } from './panier-detail.component';
import { PanierUpdateComponent } from './panier-update.component';
import { PanierDeletePopupComponent } from './panier-delete-dialog.component';
import { IPanier } from 'app/shared/model/panier.model';

@Injectable({ providedIn: 'root' })
export class PanierResolve implements Resolve<IPanier> {
  constructor(private service: PanierService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPanier> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Panier>) => response.ok),
        map((panier: HttpResponse<Panier>) => panier.body)
      );
    }
    return of(new Panier());
  }
}

export const panierRoute: Routes = [
  {
    path: '',
    component: PanierComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paniers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PanierDetailComponent,
    resolve: {
      panier: PanierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paniers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PanierUpdateComponent,
    resolve: {
      panier: PanierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paniers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PanierUpdateComponent,
    resolve: {
      panier: PanierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paniers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const panierPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PanierDeletePopupComponent,
    resolve: {
      panier: PanierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Paniers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
