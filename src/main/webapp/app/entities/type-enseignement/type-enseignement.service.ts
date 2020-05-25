import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeEnseignement } from 'app/shared/model/type-enseignement.model';

type EntityResponseType = HttpResponse<ITypeEnseignement>;
type EntityArrayResponseType = HttpResponse<ITypeEnseignement[]>;

@Injectable({ providedIn: 'root' })
export class TypeEnseignementService {
  public resourceUrl = SERVER_API_URL + 'api/type-enseignements';

  constructor(protected http: HttpClient) {}

  create(typeEnseignement: ITypeEnseignement): Observable<EntityResponseType> {
    return this.http.post<ITypeEnseignement>(this.resourceUrl, typeEnseignement, { observe: 'response' });
  }

  update(typeEnseignement: ITypeEnseignement): Observable<EntityResponseType> {
    return this.http.put<ITypeEnseignement>(this.resourceUrl, typeEnseignement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeEnseignement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeEnseignement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
