export interface IPaiement {
  id?: string;
  posseseurCarte?: string;
  typeCarte?: string;
  numeroCarte?: number;
  dateExp?: string;
  crypotogramme?: number;
}

export class Paiement implements IPaiement {
  constructor(
    public id?: string,
    public posseseurCarte?: string,
    public typeCarte?: string,
    public numeroCarte?: number,
    public dateExp?: string,
    public crypotogramme?: number
  ) {}
}
