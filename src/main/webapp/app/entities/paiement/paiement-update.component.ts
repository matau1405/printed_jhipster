import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPaiement, Paiement } from 'app/shared/model/paiement.model';
import { PaiementService } from './paiement.service';

@Component({
  selector: 'jhi-paiement-update',
  templateUrl: './paiement-update.component.html'
})
export class PaiementUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    posseseurCarte: [],
    typeCarte: [],
    numeroCarte: [],
    dateExp: [],
    crypotogramme: []
  });

  constructor(protected paiementService: PaiementService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.updateForm(paiement);
    });
  }

  updateForm(paiement: IPaiement) {
    this.editForm.patchValue({
      id: paiement.id,
      posseseurCarte: paiement.posseseurCarte,
      typeCarte: paiement.typeCarte,
      numeroCarte: paiement.numeroCarte,
      dateExp: paiement.dateExp != null ? paiement.dateExp.format(DATE_TIME_FORMAT) : null,
      crypotogramme: paiement.crypotogramme
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paiement = this.createFromForm();
    if (paiement.id !== undefined) {
      this.subscribeToSaveResponse(this.paiementService.update(paiement));
    } else {
      this.subscribeToSaveResponse(this.paiementService.create(paiement));
    }
  }

  private createFromForm(): IPaiement {
    return {
      ...new Paiement(),
      id: this.editForm.get(['id']).value,
      posseseurCarte: this.editForm.get(['posseseurCarte']).value,
      typeCarte: this.editForm.get(['typeCarte']).value,
      numeroCarte: this.editForm.get(['numeroCarte']).value,
      dateExp: this.editForm.get(['dateExp']).value != null ? moment(this.editForm.get(['dateExp']).value, DATE_TIME_FORMAT) : undefined,
      crypotogramme: this.editForm.get(['crypotogramme']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiement>>) {
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
