import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduits, Produits } from 'app/shared/model/produits.model';
import { ProduitsService } from './produits.service';
import { IPanier } from 'app/shared/model/panier.model';
import { PanierService } from 'app/entities/panier/panier.service';

@Component({
  selector: 'jhi-produits-update',
  templateUrl: './produits-update.component.html'
})
export class ProduitsUpdateComponent implements OnInit {
  isSaving: boolean;

  paniers: IPanier[];

  editForm = this.fb.group({
    id: [],
    idProd: [],
    nomProd: [],
    descriptionProd: [],
    prixProd: [],
    dispo: [],
    stock: [],
    marque: [],
    personnalisable: [],
    imageProd: [],
    imagePersonnalisation: [],
    taille: [],
    panier: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected produitsService: ProduitsService,
    protected panierService: PanierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produits }) => {
      this.updateForm(produits);
    });
    this.panierService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPanier[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPanier[]>) => response.body)
      )
      .subscribe((res: IPanier[]) => (this.paniers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(produits: IProduits) {
    this.editForm.patchValue({
      id: produits.id,
      idProd: produits.idProd,
      nomProd: produits.nomProd,
      descriptionProd: produits.descriptionProd,
      prixProd: produits.prixProd,
      dispo: produits.dispo,
      stock: produits.stock,
      marque: produits.marque,
      personnalisable: produits.personnalisable,
      imageProd: produits.imageProd,
      imagePersonnalisation: produits.imagePersonnalisation,
      taille: produits.taille,
      panier: produits.panier
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const produits = this.createFromForm();
    if (produits.id !== undefined) {
      this.subscribeToSaveResponse(this.produitsService.update(produits));
    } else {
      this.subscribeToSaveResponse(this.produitsService.create(produits));
    }
  }

  private createFromForm(): IProduits {
    return {
      ...new Produits(),
      id: this.editForm.get(['id']).value,
      idProd: this.editForm.get(['idProd']).value,
      nomProd: this.editForm.get(['nomProd']).value,
      descriptionProd: this.editForm.get(['descriptionProd']).value,
      prixProd: this.editForm.get(['prixProd']).value,
      dispo: this.editForm.get(['dispo']).value,
      stock: this.editForm.get(['stock']).value,
      marque: this.editForm.get(['marque']).value,
      personnalisable: this.editForm.get(['personnalisable']).value,
      imageProd: this.editForm.get(['imageProd']).value,
      imagePersonnalisation: this.editForm.get(['imagePersonnalisation']).value,
      taille: this.editForm.get(['taille']).value,
      panier: this.editForm.get(['panier']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduits>>) {
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
}
