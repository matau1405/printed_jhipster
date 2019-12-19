import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommande, Commande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { IPanier } from 'app/shared/model/panier.model';
import { PanierService } from 'app/entities/panier/panier.service';
import { IPaiement } from 'app/shared/model/paiement.model';
import { PaiementService } from 'app/entities/paiement/paiement.service';

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html'
})
export class CommandeUpdateComponent implements OnInit {
  isSaving: boolean;

  paniers: IPanier[];

  paiements: IPaiement[];

  editForm = this.fb.group({
    id: [],
    idCmd: [],
    dateCmd: [],
    montantCmd: [],
    delaiLivraisonCmd: [],
    etatLivraisonCmd: [],
    lieuLivraisonCmd: [],
    modeLivraisonCmd: [],
    prixTotalCmd: [],
    modePaiement: [],
    panier: [],
    paiement: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commandeService: CommandeService,
    protected panierService: PanierService,
    protected paiementService: PaiementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ commande }) => {
      this.updateForm(commande);
    });
    this.panierService
      .query({ filter: 'commande-is-null' })
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
    this.paiementService
      .query({ filter: 'commande-is-null' })
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

  updateForm(commande: ICommande) {
    this.editForm.patchValue({
      id: commande.id,
      idCmd: commande.idCmd,
      dateCmd: commande.dateCmd,
      montantCmd: commande.montantCmd,
      delaiLivraisonCmd: commande.delaiLivraisonCmd,
      etatLivraisonCmd: commande.etatLivraisonCmd,
      lieuLivraisonCmd: commande.lieuLivraisonCmd,
      modeLivraisonCmd: commande.modeLivraisonCmd,
      prixTotalCmd: commande.prixTotalCmd,
      modePaiement: commande.modePaiement,
      panier: commande.panier,
      paiement: commande.paiement
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const commande = this.createFromForm();
    if (commande.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  private createFromForm(): ICommande {
    return {
      ...new Commande(),
      id: this.editForm.get(['id']).value,
      idCmd: this.editForm.get(['idCmd']).value,
      dateCmd: this.editForm.get(['dateCmd']).value,
      montantCmd: this.editForm.get(['montantCmd']).value,
      delaiLivraisonCmd: this.editForm.get(['delaiLivraisonCmd']).value,
      etatLivraisonCmd: this.editForm.get(['etatLivraisonCmd']).value,
      lieuLivraisonCmd: this.editForm.get(['lieuLivraisonCmd']).value,
      modeLivraisonCmd: this.editForm.get(['modeLivraisonCmd']).value,
      prixTotalCmd: this.editForm.get(['prixTotalCmd']).value,
      modePaiement: this.editForm.get(['modePaiement']).value,
      panier: this.editForm.get(['panier']).value,
      paiement: this.editForm.get(['paiement']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>) {
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

  trackPaiementById(index: number, item: IPaiement) {
    return item.id;
  }
}
