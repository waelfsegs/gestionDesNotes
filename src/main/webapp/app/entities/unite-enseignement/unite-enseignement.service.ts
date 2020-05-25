import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUniteEnseignement } from 'app/shared/model/unite-enseignement.model';

type EntityResponseType = HttpResponse<IUniteEnseignement>;
type EntityArrayResponseType = HttpResponse<IUniteEnseignement[]>;

@Injectable({ providedIn: 'root' })
export class UniteEnseignementService {
  public resourceUrl = SERVER_API_URL + 'api/unite-enseignements';

  constructor(protected http: HttpClient) {}

  create(uniteEnseignement: IUniteEnseignement): Observable<EntityResponseType> {
    return this.http.post<IUniteEnseignement>(this.resourceUrl, uniteEnseignement, { observe: 'response' });
  }

  update(uniteEnseignement: IUniteEnseignement): Observable<EntityResponseType> {
    return this.http.put<IUniteEnseignement>(this.resourceUrl, uniteEnseignement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUniteEnseignement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUniteEnseignement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
