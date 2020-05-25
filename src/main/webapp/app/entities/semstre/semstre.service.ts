import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISemstre } from 'app/shared/model/semstre.model';

type EntityResponseType = HttpResponse<ISemstre>;
type EntityArrayResponseType = HttpResponse<ISemstre[]>;

@Injectable({ providedIn: 'root' })
export class SemstreService {
  public resourceUrl = SERVER_API_URL + 'api/semstres';

  constructor(protected http: HttpClient) {}

  create(semstre: ISemstre): Observable<EntityResponseType> {
    return this.http.post<ISemstre>(this.resourceUrl, semstre, { observe: 'response' });
  }

  update(semstre: ISemstre): Observable<EntityResponseType> {
    return this.http.put<ISemstre>(this.resourceUrl, semstre, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISemstre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISemstre[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
