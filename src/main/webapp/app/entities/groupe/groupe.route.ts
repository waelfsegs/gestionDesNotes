import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGroupe, Groupe } from 'app/shared/model/groupe.model';
import { GroupeService } from './groupe.service';
import { GroupeComponent } from './groupe.component';
import { GroupeDetailComponent } from './groupe-detail.component';
import { GroupeUpdateComponent } from './groupe-update.component';

@Injectable({ providedIn: 'root' })
export class GroupeResolve implements Resolve<IGroupe> {
  constructor(private service: GroupeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGroupe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((groupe: HttpResponse<Groupe>) => {
          if (groupe.body) {
            return of(groupe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Groupe());
  }
}

export const groupeRoute: Routes = [
  {
    path: '',
    component: GroupeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.groupe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GroupeDetailComponent,
    resolve: {
      groupe: GroupeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN','ROLE_Enseignant'],
      pageTitle: 'gestionNotesFsegsApp.groupe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GroupeUpdateComponent,
    resolve: {
      groupe: GroupeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.groupe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GroupeUpdateComponent,
    resolve: {
      groupe: GroupeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.groupe.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
