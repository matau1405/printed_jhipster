import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPanier } from 'app/shared/model/panier.model';
import { AccountService } from 'app/core/auth/account.service';
import { PanierService } from './panier.service';

@Component({
  selector: 'jhi-panier',
  templateUrl: './panier.component.html'
})
export class PanierComponent implements OnInit, OnDestroy {
  paniers: IPanier[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected panierService: PanierService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.panierService
      .query()
      .pipe(
        filter((res: HttpResponse<IPanier[]>) => res.ok),
        map((res: HttpResponse<IPanier[]>) => res.body)
      )
      .subscribe((res: IPanier[]) => {
        this.paniers = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPaniers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPanier) {
    return item.id;
  }

  registerChangeInPaniers() {
    this.eventSubscriber = this.eventManager.subscribe('panierListModification', response => this.loadAll());
  }
}
