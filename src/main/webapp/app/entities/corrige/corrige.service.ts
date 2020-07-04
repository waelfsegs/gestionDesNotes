import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICorrige } from 'app/shared/model/corrige.model';

type EntityResponseType = HttpResponse<ICorrige>;
type EntityArrayResponseType = HttpResponse<ICorrige[]>;

@Injectable({ providedIn: 'root' })
export class CorrigeService {
  public resourceUrl = SERVER_API_URL + 'api/corriges';

  constructor(protected http: HttpClient) {}

  create(corrige: ICorrige): Observable<EntityResponseType> {
    return this.http.post<ICorrige>(this.resourceUrl, corrige, { observe: 'response' });
  }

  update(corrige: ICorrige): Observable<EntityResponseType> {
    return this.http.put<ICorrige>(this.resourceUrl, corrige, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICorrige>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICorrige[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
