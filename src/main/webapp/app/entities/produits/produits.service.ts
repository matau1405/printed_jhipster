import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProduits } from 'app/shared/model/produits.model';

type EntityResponseType = HttpResponse<IProduits>;
type EntityArrayResponseType = HttpResponse<IProduits[]>;

@Injectable({ providedIn: 'root' })
export class ProduitsService {
  public resourceUrl = SERVER_API_URL + 'api/produits';

  constructor(protected http: HttpClient) {}

  create(produits: IProduits): Observable<EntityResponseType> {
    return this.http.post<IProduits>(this.resourceUrl, produits, { observe: 'response' });
  }

  update(produits: IProduits): Observable<EntityResponseType> {
    return this.http.put<IProduits>(this.resourceUrl, produits, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProduits>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProduits[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
