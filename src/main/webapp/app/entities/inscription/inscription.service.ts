import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInscription } from 'app/shared/model/inscription.model';

type EntityResponseType = HttpResponse<IInscription>;
type EntityArrayResponseType = HttpResponse<IInscription[]>;

@Injectable({ providedIn: 'root' })
export class InscriptionService {
  public resourceUrl = SERVER_API_URL + 'api/inscriptions';

  constructor(protected http: HttpClient) {}

  create(inscription: IInscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inscription);
    return this.http
      .post<IInscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inscription: IInscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inscription);
    return this.http
      .put<IInscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInscription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inscription: IInscription): IInscription {
    const copy: IInscription = Object.assign({}, inscription, {
      date: inscription.date && inscription.date.isValid() ? inscription.date.format(DATE_FORMAT) : undefined,
      annee: inscription.annee && inscription.annee.isValid() ? inscription.annee.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.annee = res.body.annee ? moment(res.body.annee) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inscription: IInscription) => {
        inscription.date = inscription.date ? moment(inscription.date) : undefined;
        inscription.annee = inscription.annee ? moment(inscription.annee) : undefined;
      });
    }
    return res;
  }
}
