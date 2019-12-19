import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPanier, Panier } from 'app/shared/model/panier.model';
import { PanierService } from './panier.service';

@Component({
  selector: 'jhi-panier-update',
  templateUrl: './panier-update.component.html'
})
export class PanierUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    idPanier: []
  });

  constructor(protected panierService: PanierService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ panier }) => {
      this.updateForm(panier);
    });
  }

  updateForm(panier: IPanier) {
    this.editForm.patchValue({
      id: panier.id,
      idPanier: panier.idPanier
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const panier = this.createFromForm();
    if (panier.id !== undefined) {
      this.subscribeToSaveResponse(this.panierService.update(panier));
    } else {
      this.subscribeToSaveResponse(this.panierService.create(panier));
    }
  }

  private createFromForm(): IPanier {
    return {
      ...new Panier(),
      id: this.editForm.get(['id']).value,
      idPanier: this.editForm.get(['idPanier']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPanier>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
