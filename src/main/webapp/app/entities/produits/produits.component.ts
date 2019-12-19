import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProduits } from 'app/shared/model/produits.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProduitsService } from './produits.service';

@Component({
  selector: 'jhi-produits',
  templateUrl: './produits.component.html'
})
export class ProduitsComponent implements OnInit, OnDestroy {
  produits: IProduits[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected produitsService: ProduitsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.produitsService
      .query()
      .pipe(
        filter((res: HttpResponse<IProduits[]>) => res.ok),
        map((res: HttpResponse<IProduits[]>) => res.body)
      )
      .subscribe((res: IProduits[]) => {
        this.produits = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProduits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProduits) {
    return item.id;
  }

  registerChangeInProduits() {
    this.eventSubscriber = this.eventManager.subscribe('produitsListModification', response => this.loadAll());
  }
}
