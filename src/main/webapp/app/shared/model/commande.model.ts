import { IPanier } from 'app/shared/model/panier.model';
import { IPaiement } from 'app/shared/model/paiement.model';

export interface ICommande {
  id?: string;
  idCmd?: string;
  dateCmd?: string;
  montantCmd?: number;
  delaiLivraisonCmd?: number;
  etatLivraisonCmd?: string;
  lieuLivraisonCmd?: string;
  modeLivraisonCmd?: string;
  prixTotalCmd?: number;
  modePaiement?: string;
  panier?: IPanier;
  paiement?: IPaiement;
}

export class Commande implements ICommande {
  constructor(
    public id?: string,
    public idCmd?: string,
    public dateCmd?: string,
    public montantCmd?: number,
    public delaiLivraisonCmd?: number,
    public etatLivraisonCmd?: string,
    public lieuLivraisonCmd?: string,
    public modeLivraisonCmd?: string,
    public prixTotalCmd?: number,
    public modePaiement?: string,
    public panier?: IPanier,
    public paiement?: IPaiement
  ) {}
}
