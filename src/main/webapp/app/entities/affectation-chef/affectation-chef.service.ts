import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAffectationChef } from 'app/shared/model/affectation-chef.model';

type EntityResponseType = HttpResponse<IAffectationChef>;
type EntityArrayResponseType = HttpResponse<IAffectationChef[]>;

@Injectable({ providedIn: 'root' })
export class AffectationChefService {
  public resourceUrl = SERVER_API_URL + 'api/affectation-chefs';

  constructor(protected http: HttpClient) {}

  create(affectationChef: IAffectationChef): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectationChef);
    return this.http
      .post<IAffectationChef>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(affectationChef: IAffectationChef): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectationChef);
    return this.http
      .put<IAffectationChef>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAffectationChef>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAffectationChef[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  count(req?: any): Observable<HttpResponse<number>> {
    const options = createRequestOption(req);
    return this.http.get<number>(this.resourceUrl + '/count', { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(affectationChef: IAffectationChef): IAffectationChef {
    const copy: IAffectationChef = Object.assign({}, affectationChef, {
      startDate:
        affectationChef.startDate && affectationChef.startDate.isValid() ? affectationChef.startDate.format(DATE_FORMAT) : undefined,
      endDate: affectationChef.endDate && affectationChef.endDate.isValid() ? affectationChef.endDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((affectationChef: IAffectationChef) => {
        affectationChef.startDate = affectationChef.startDate ? moment(affectationChef.startDate) : undefined;
        affectationChef.endDate = affectationChef.endDate ? moment(affectationChef.endDate) : undefined;
      });
    }
    return res;
  }
}
