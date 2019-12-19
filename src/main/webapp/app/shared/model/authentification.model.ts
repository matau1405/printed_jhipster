import { IPaiement } from 'app/shared/model/paiement.model';

export interface IAuthentification {
  id?: string;
  login?: string;
  passeword?: string;
  paiement?: IPaiement;
}

export class Authentification implements IAuthentification {
  constructor(public id?: string, public login?: string, public passeword?: string, public paiement?: IPaiement) {}
}
