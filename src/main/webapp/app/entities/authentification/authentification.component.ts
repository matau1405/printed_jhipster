import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAuthentification } from 'app/shared/model/authentification.model';
import { AccountService } from 'app/core/auth/account.service';
import { AuthentificationService } from './authentification.service';

@Component({
  selector: 'jhi-authentification',
  templateUrl: './authentification.component.html'
})
export class AuthentificationComponent implements OnInit, OnDestroy {
  authentifications: IAuthentification[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected authentificationService: AuthentificationService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.authentificationService
      .query()
      .pipe(
        filter((res: HttpResponse<IAuthentification[]>) => res.ok),
        map((res: HttpResponse<IAuthentification[]>) => res.body)
      )
      .subscribe((res: IAuthentification[]) => {
        this.authentifications = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAuthentifications();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAuthentification) {
    return item.id;
  }

  registerChangeInAuthentifications() {
    this.eventSubscriber = this.eventManager.subscribe('authentificationListModification', response => this.loadAll());
  }
}
