import { IPanier } from 'app/shared/model/panier.model';

export interface IProduits {
  id?: string;
  idProd?: string;
  nomProd?: string;
  descriptionProd?: string;
  prixProd?: string;
  dispo?: boolean;
  stock?: number;
  marque?: string;
  personnalisable?: boolean;
  imageProd?: string;
  imagePersonnalisation?: string;
  panier?: IPanier;
}

export class Produits implements IProduits {
  constructor(
    public id?: string,
    public idProd?: string,
    public nomProd?: string,
    public descriptionProd?: string,
    public prixProd?: string,
    public dispo?: boolean,
    public stock?: number,
    public marque?: string,
    public personnalisable?: boolean,
    public imageProd?: string,
    public imagePersonnalisation?: string,
    public panier?: IPanier
  ) {
    this.dispo = this.dispo || false;
    this.personnalisable = this.personnalisable || false;
  }
}
