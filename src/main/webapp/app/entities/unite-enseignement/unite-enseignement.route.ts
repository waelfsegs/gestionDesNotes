import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUniteEnseignement, UniteEnseignement } from 'app/shared/model/unite-enseignement.model';
import { UniteEnseignementService } from './unite-enseignement.service';
import { UniteEnseignementComponent } from './unite-enseignement.component';
import { UniteEnseignementDetailComponent } from './unite-enseignement-detail.component';
import { UniteEnseignementUpdateComponent } from './unite-enseignement-update.component';

@Injectable({ providedIn: 'root' })
export class UniteEnseignementResolve implements Resolve<IUniteEnseignement> {
  constructor(private service: UniteEnseignementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUniteEnseignement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((uniteEnseignement: HttpResponse<UniteEnseignement>) => {
          if (uniteEnseignement.body) {
            return of(uniteEnseignement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UniteEnseignement());
  }
}

export const uniteEnseignementRoute: Routes = [
  {
    path: '',
    component: UniteEnseignementComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.uniteEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UniteEnseignementDetailComponent,
    resolve: {
      uniteEnseignement: UniteEnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.uniteEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UniteEnseignementUpdateComponent,
    resolve: {
      uniteEnseignement: UniteEnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.uniteEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UniteEnseignementUpdateComponent,
    resolve: {
      uniteEnseignement: UniteEnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.uniteEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
