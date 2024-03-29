import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaiement } from 'app/shared/model/paiement.model';

@Component({
  selector: 'jhi-paiement-detail',
  templateUrl: './paiement-detail.component.html'
})
export class PaiementDetailComponent implements OnInit {
  paiement: IPaiement;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.paiement = paiement;
    });
  }

  previousState() {
    window.history.back();
  }
}
