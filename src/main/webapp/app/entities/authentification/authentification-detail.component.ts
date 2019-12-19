import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuthentification } from 'app/shared/model/authentification.model';

@Component({
  selector: 'jhi-authentification-detail',
  templateUrl: './authentification-detail.component.html'
})
export class AuthentificationDetailComponent implements OnInit {
  authentification: IAuthentification;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ authentification }) => {
      this.authentification = authentification;
    });
  }

  previousState() {
    window.history.back();
  }
}
