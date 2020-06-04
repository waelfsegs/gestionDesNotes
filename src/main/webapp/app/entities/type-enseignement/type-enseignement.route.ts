import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeEnseignement, TypeEnseignement } from 'app/shared/model/type-enseignement.model';
import { TypeEnseignementService } from './type-enseignement.service';
import { TypeEnseignementComponent } from './type-enseignement.component';
import { TypeEnseignementDetailComponent } from './type-enseignement-detail.component';
import { TypeEnseignementUpdateComponent } from './type-enseignement-update.component';

@Injectable({ providedIn: 'root' })
export class TypeEnseignementResolve implements Resolve<ITypeEnseignement> {
  constructor(private service: TypeEnseignementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeEnseignement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeEnseignement: HttpResponse<TypeEnseignement>) => {
          if (typeEnseignement.body) {
            return of(typeEnseignement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeEnseignement());
  }
}

export const typeEnseignementRoute: Routes = [
  {
    path: '',
    component: TypeEnseignementComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.typeEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeEnseignementDetailComponent,
    resolve: {
      typeEnseignement: TypeEnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.typeEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeEnseignementUpdateComponent,
    resolve: {
      typeEnseignement: TypeEnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.typeEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeEnseignementUpdateComponent,
    resolve: {
      typeEnseignement: TypeEnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.typeEnseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
