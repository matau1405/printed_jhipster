import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IPanier } from 'app/shared/model/panier.model';
import { PanierService } from 'app/entities/panier/panier.service';
import { IAuthentification } from 'app/shared/model/authentification.model';
import { AuthentificationService } from 'app/entities/authentification/authentification.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html'
})
export class ClientUpdateComponent implements OnInit {
  isSaving: boolean;

  paniers: IPanier[];

  paiements: IAuthentification[];

  editForm = this.fb.group({
    id: [],
    idClient: [],
    nomClient: [],
    prenomClient: [],
    dateNaissanceClient: [],
    adresseClient: [null, [Validators.required, Validators.maxLength(100)]],
    villeClient: [],
    paysClient: [],
    emailClient: [],
    listCommande: [],
    panier: [],
    paiement: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clientService: ClientService,
    protected panierService: PanierService,
    protected authentificationService: AuthentificationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);
    });
    this.panierService
      .query({ filter: 'client-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPanier[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPanier[]>) => response.body)
      )
      .subscribe(
        (res: IPanier[]) => {
          if (!this.editForm.get('panier').value || !this.editForm.get('panier').value.id) {
            this.paniers = res;
          } else {
            this.panierService
              .find(this.editForm.get('panier').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPanier>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPanier>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPanier) => (this.paniers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.authentificationService
      .query({ filter: 'client-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAuthentification[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAuthentification[]>) => response.body)
      )
      .subscribe(
        (res: IAuthentification[]) => {
          if (!this.editForm.get('paiement').value || !this.editForm.get('paiement').value.id) {
            this.paiements = res;
          } else {
            this.authentificationService
              .find(this.editForm.get('paiement').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAuthentification>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAuthentification>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAuthentification) => (this.paiements = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(client: IClient) {
    this.editForm.patchValue({
      id: client.id,
      idClient: client.idClient,
      nomClient: client.nomClient,
      prenomClient: client.prenomClient,
      dateNaissanceClient: client.dateNaissanceClient != null ? client.dateNaissanceClient.format(DATE_TIME_FORMAT) : null,
      adresseClient: client.adresseClient,
      villeClient: client.villeClient,
      paysClient: client.paysClient,
      emailClient: client.emailClient,
      listCommande: client.listCommande,
      panier: client.panier,
      paiement: client.paiement
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id']).value,
      idClient: this.editForm.get(['idClient']).value,
      nomClient: this.editForm.get(['nomClient']).value,
      prenomClient: this.editForm.get(['prenomClient']).value,
      dateNaissanceClient:
        this.editForm.get(['dateNaissanceClient']).value != null
          ? moment(this.editForm.get(['dateNaissanceClient']).value, DATE_TIME_FORMAT)
          : undefined,
      adresseClient: this.editForm.get(['adresseClient']).value,
      villeClient: this.editForm.get(['villeClient']).value,
      paysClient: this.editForm.get(['paysClient']).value,
      emailClient: this.editForm.get(['emailClient']).value,
      listCommande: this.editForm.get(['listCommande']).value,
      panier: this.editForm.get(['panier']).value,
      paiement: this.editForm.get(['paiement']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>) {
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

  trackPanierById(index: number, item: IPanier) {
    return item.id;
  }

  trackAuthentificationById(index: number, item: IAuthentification) {
    return item.id;
  }
}
