import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPanier } from 'app/shared/model/panier.model';

@Component({
  selector: 'jhi-panier-detail',
  templateUrl: './panier-detail.component.html'
})
export class PanierDetailComponent implements OnInit {
  panier: IPanier;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ panier }) => {
      this.panier = panier;
    });
  }

  previousState() {
    window.history.back();
  }
}
