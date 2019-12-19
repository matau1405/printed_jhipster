import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuthentification } from 'app/shared/model/authentification.model';

type EntityResponseType = HttpResponse<IAuthentification>;
type EntityArrayResponseType = HttpResponse<IAuthentification[]>;

@Injectable({ providedIn: 'root' })
export class AuthentificationService {
  public resourceUrl = SERVER_API_URL + 'api/authentifications';

  constructor(protected http: HttpClient) {}

  create(authentification: IAuthentification): Observable<EntityResponseType> {
    return this.http.post<IAuthentification>(this.resourceUrl, authentification, { observe: 'response' });
  }

  update(authentification: IAuthentification): Observable<EntityResponseType> {
    return this.http.put<IAuthentification>(this.resourceUrl, authentification, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAuthentification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthentification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
