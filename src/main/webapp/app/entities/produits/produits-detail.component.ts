import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProduits } from 'app/shared/model/produits.model';

@Component({
  selector: 'jhi-produits-detail',
  templateUrl: './produits-detail.component.html'
})
export class ProduitsDetailComponent implements OnInit {
  produits: IProduits;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produits }) => {
      this.produits = produits;
    });
  }

  previousState() {
    window.history.back();
  }
}
