import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEnveloppe } from 'app/shared/model/enveloppe.model';

type EntityResponseType = HttpResponse<IEnveloppe>;
type EntityArrayResponseType = HttpResponse<IEnveloppe[]>;

@Injectable({ providedIn: 'root' })
export class EnveloppeService {
  public resourceUrl = SERVER_API_URL + 'api/enveloppes';

  constructor(protected http: HttpClient) {}

  create(enveloppe: IEnveloppe): Observable<EntityResponseType> {
    return this.http.post<IEnveloppe>(this.resourceUrl, enveloppe, { observe: 'response' });
  }

  update(enveloppe: IEnveloppe): Observable<EntityResponseType> {
    return this.http.put<IEnveloppe>(this.resourceUrl, enveloppe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnveloppe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnveloppe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
