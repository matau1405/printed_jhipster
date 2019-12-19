import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPaiement } from 'app/shared/model/paiement.model';
import { AccountService } from 'app/core/auth/account.service';
import { PaiementService } from './paiement.service';

@Component({
  selector: 'jhi-paiement',
  templateUrl: './paiement.component.html'
})
export class PaiementComponent implements OnInit, OnDestroy {
  paiements: IPaiement[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected paiementService: PaiementService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.paiementService
      .query()
      .pipe(
        filter((res: HttpResponse<IPaiement[]>) => res.ok),
        map((res: HttpResponse<IPaiement[]>) => res.body)
      )
      .subscribe((res: IPaiement[]) => {
        this.paiements = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPaiements();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaiement) {
    return item.id;
  }

  registerChangeInPaiements() {
    this.eventSubscriber = this.eventManager.subscribe('paiementListModification', response => this.loadAll());
  }
}
