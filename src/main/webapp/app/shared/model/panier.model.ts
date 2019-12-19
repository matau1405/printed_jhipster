import { IProduits } from 'app/shared/model/produits.model';

export interface IPanier {
  id?: string;
  idPanier?: string;
  produits?: IProduits[];
}

export class Panier implements IPanier {
  constructor(public id?: string, public idPanier?: string, public produits?: IProduits[]) {}
}
