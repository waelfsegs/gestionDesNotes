import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResultat } from 'app/shared/model/resultat.model';

type EntityResponseType = HttpResponse<IResultat>;
type EntityArrayResponseType = HttpResponse<IResultat[]>;

@Injectable({ providedIn: 'root' })
export class ResultatService {
  public resourceUrl = SERVER_API_URL + 'api/resultats';

  constructor(protected http: HttpClient) {}

  create(resultat: IResultat): Observable<EntityResponseType> {
    return this.http.post<IResultat>(this.resourceUrl, resultat, { observe: 'response' });
  }

  update(resultat: IResultat): Observable<EntityResponseType> {
    return this.http.put<IResultat>(this.resourceUrl, resultat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
