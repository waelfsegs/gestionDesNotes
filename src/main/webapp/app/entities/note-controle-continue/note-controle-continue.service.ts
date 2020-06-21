import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

import { IMatiere } from 'app/shared/model/matiere.model';
import { IGroupEnseigner } from 'app/shared/model/groupe-enseigner';
import { IMatierByEnseignent } from 'app/shared/model/matiere-by-enseignent';
import { IGroupbyetudiant } from 'app/shared/model/group-by-etudiant';

type EntityResponseType = HttpResponse<IMatiere>;

@Injectable({ providedIn: 'root' })
export class NoteControleContinueService {
  public resourceUrl = SERVER_API_URL + 'api/';

  constructor(protected http: HttpClient) {}

  getmatierebyenseignement(id: number): Observable<HttpResponse<IMatierByEnseignent[]>> {
    return this.http.get<IMatierByEnseignent[]>(`${this.resourceUrl}getmatierebyenseignement/${id}`, { observe: 'response' });
  }
  getEtudiantByGroup(id: number): Observable<HttpResponse<IGroupbyetudiant[]>> {
    return this.http.get<IGroupbyetudiant[]>(`${this.resourceUrl}getEtudiantByGroup/${id}`, { observe: 'response' });
  }
  getGroupEnseigner(idens: number, matiereid: number): Observable<HttpResponse<IGroupEnseigner[]>> {
    return this.http.get<IGroupEnseigner[]>(`${this.resourceUrl}getGroupEnseigner/${idens},${matiereid}`, { observe: 'response' });
  }
}
