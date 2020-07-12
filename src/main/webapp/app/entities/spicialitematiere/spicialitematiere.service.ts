import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpicialitematiere } from 'app/shared/model/spicialitematiere.model';

type EntityResponseType = HttpResponse<ISpicialitematiere>;
type EntityArrayResponseType = HttpResponse<ISpicialitematiere[]>;

@Injectable({ providedIn: 'root' })
export class SpicialitematiereService {
  public resourceUrl = SERVER_API_URL + 'api/spicialitematieres';

  constructor(protected http: HttpClient) {}

  create(spicialitematiere: ISpicialitematiere): Observable<EntityResponseType> {
    return this.http.post<ISpicialitematiere>(this.resourceUrl, spicialitematiere, { observe: 'response' });
  }

  update(spicialitematiere: ISpicialitematiere): Observable<EntityResponseType> {
    return this.http.put<ISpicialitematiere>(this.resourceUrl, spicialitematiere, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpicialitematiere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpicialitematiere[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
