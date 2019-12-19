import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Produits } from 'app/shared/model/produits.model';
import { ProduitsService } from './produits.service';
import { ProduitsComponent } from './produits.component';
import { ProduitsDetailComponent } from './produits-detail.component';
import { ProduitsUpdateComponent } from './produits-update.component';
import { ProduitsDeletePopupComponent } from './produits-delete-dialog.component';
import { IProduits } from 'app/shared/model/produits.model';

@Injectable({ providedIn: 'root' })
export class ProduitsResolve implements Resolve<IProduits> {
  constructor(private service: ProduitsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProduits> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Produits>) => response.ok),
        map((produits: HttpResponse<Produits>) => produits.body)
      );
    }
    return of(new Produits());
  }
}

export const produitsRoute: Routes = [
  {
    path: '',
    component: ProduitsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produits'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProduitsDetailComponent,
    resolve: {
      produits: ProduitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produits'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProduitsUpdateComponent,
    resolve: {
      produits: ProduitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produits'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProduitsUpdateComponent,
    resolve: {
      produits: ProduitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produits'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const produitsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProduitsDeletePopupComponent,
    resolve: {
      produits: ProduitsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produits'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
