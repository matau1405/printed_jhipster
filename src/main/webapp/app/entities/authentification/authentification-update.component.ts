import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAuthentification, Authentification } from 'app/shared/model/authentification.model';
import { AuthentificationService } from './authentification.service';
import { IPaiement } from 'app/shared/model/paiement.model';
import { PaiementService } from 'app/entities/paiement/paiement.service';

@Component({
  selector: 'jhi-authentification-update',
  templateUrl: './authentification-update.component.html'
})
export class AuthentificationUpdateComponent implements OnInit {
  isSaving: boolean;

  paiements: IPaiement[];

  editForm = this.fb.group({
    id: [],
    login: [],
    passeword: [],
    paiement: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected authentificationService: AuthentificationService,
    protected paiementService: PaiementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ authentification }) => {
      this.updateForm(authentification);
    });
    this.paiementService
      .query({ filter: 'authentification-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPaiement[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaiement[]>) => response.body)
      )
      .subscribe(
        (res: IPaiement[]) => {
          if (!this.editForm.get('paiement').value || !this.editForm.get('paiement').value.id) {
            this.paiements = res;
          } else {
            this.paiementService
              .find(this.editForm.get('paiement').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPaiement>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPaiement>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPaiement) => (this.paiements = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(authentification: IAuthentification) {
    this.editForm.patchValue({
      id: authentification.id,
      login: authentification.login,
      passeword: authentification.passeword,
      paiement: authentification.paiement
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const authentification = this.createFromForm();
    if (authentification.id !== undefined) {
      this.subscribeToSaveResponse(this.authentificationService.update(authentification));
    } else {
      this.subscribeToSaveResponse(this.authentificationService.create(authentification));
    }
  }

  private createFromForm(): IAuthentification {
    return {
      ...new Authentification(),
      id: this.editForm.get(['id']).value,
      login: this.editForm.get(['login']).value,
      passeword: this.editForm.get(['passeword']).value,
      paiement: this.editForm.get(['paiement']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuthentification>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPaiementById(index: number, item: IPaiement) {
    return item.id;
  }
}
