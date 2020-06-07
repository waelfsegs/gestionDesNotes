import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

import { IMatiere } from 'app/shared/model/matiere.model';

type EntityResponseType = HttpResponse<IMatiere>;

@Injectable({ providedIn: 'root' })
export class NoteControleContinueService {
  public resourceUrl = SERVER_API_URL + 'api/matieres';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatiere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
