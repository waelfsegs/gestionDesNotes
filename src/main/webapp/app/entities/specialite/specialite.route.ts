import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISpecialite, Specialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from './specialite.service';
import { SpecialiteComponent } from './specialite.component';
import { SpecialiteDetailComponent } from './specialite-detail.component';
import { SpecialiteUpdateComponent } from './specialite-update.component';

@Injectable({ providedIn: 'root' })
export class SpecialiteResolve implements Resolve<ISpecialite> {
  constructor(private service: SpecialiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecialite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((specialite: HttpResponse<Specialite>) => {
          if (specialite.body) {
            return of(specialite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Specialite());
  }
}

export const specialiteRoute: Routes = [
  {
    path: '',
    component: SpecialiteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.specialite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpecialiteDetailComponent,
    resolve: {
      specialite: SpecialiteResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.specialite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpecialiteUpdateComponent,
    resolve: {
      specialite: SpecialiteResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.specialite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpecialiteUpdateComponent,
    resolve: {
      specialite: SpecialiteResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.specialite.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
