import { Moment } from 'moment';
import { IPanier } from 'app/shared/model/panier.model';
import { IPaiement } from 'app/shared/model/paiement.model';
import { ModeDeLivraison } from 'app/shared/model/enumerations/mode-de-livraison.model';
import { StatusCommande } from 'app/shared/model/enumerations/status-commande.model';

export interface ICommande {
  id?: string;
  idCmd?: string;
  dateCmd?: Moment;
  montantCmd?: number;
  delaiLivraisonCmd?: number;
  etatLivraisonCmd?: string;
  lieuLivraisonCmd?: string;
  modeLivraisonCmd?: ModeDeLivraison;
  status?: StatusCommande;
  panier?: IPanier;
  paiement?: IPaiement;
}

export class Commande implements ICommande {
  constructor(
    public id?: string,
    public idCmd?: string,
    public dateCmd?: Moment,
    public montantCmd?: number,
    public delaiLivraisonCmd?: number,
    public etatLivraisonCmd?: string,
    public lieuLivraisonCmd?: string,
    public modeLivraisonCmd?: ModeDeLivraison,
    public status?: StatusCommande,
    public panier?: IPanier,
    public paiement?: IPaiement
  ) {}
}
