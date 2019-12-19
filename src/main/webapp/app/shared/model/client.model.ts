import { Moment } from 'moment';
import { IPanier } from 'app/shared/model/panier.model';
import { IAuthentification } from 'app/shared/model/authentification.model';

export interface IClient {
  id?: string;
  idClient?: string;
  nomClient?: string;
  prenomClient?: string;
  dateNaissanceClient?: Moment;
  adresseClient?: string;
  villeClient?: string;
  paysClient?: string;
  emailClient?: string;
  listCommande?: string;
  panier?: IPanier;
  paiement?: IAuthentification;
}

export class Client implements IClient {
  constructor(
    public id?: string,
    public idClient?: string,
    public nomClient?: string,
    public prenomClient?: string,
    public dateNaissanceClient?: Moment,
    public adresseClient?: string,
    public villeClient?: string,
    public paysClient?: string,
    public emailClient?: string,
    public listCommande?: string,
    public panier?: IPanier,
    public paiement?: IAuthentification
  ) {}
}
