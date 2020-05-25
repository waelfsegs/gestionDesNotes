import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAffectationChef, AffectationChef } from 'app/shared/model/affectation-chef.model';
import { AffectationChefService } from './affectation-chef.service';
import { AffectationChefComponent } from './affectation-chef.component';
import { AffectationChefDetailComponent } from './affectation-chef-detail.component';
import { AffectationChefUpdateComponent } from './affectation-chef-update.component';

@Injectable({ providedIn: 'root' })
export class AffectationChefResolve implements Resolve<IAffectationChef> {
  constructor(private service: AffectationChefService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAffectationChef> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((affectationChef: HttpResponse<AffectationChef>) => {
          if (affectationChef.body) {
            return of(affectationChef.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AffectationChef());
  }
}

export const affectationChefRoute: Routes = [
  {
    path: '',
    component: AffectationChefComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.affectationChef.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AffectationChefDetailComponent,
    resolve: {
      affectationChef: AffectationChefResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.affectationChef.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AffectationChefUpdateComponent,
    resolve: {
      affectationChef: AffectationChefResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.affectationChef.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AffectationChefUpdateComponent,
    resolve: {
      affectationChef: AffectationChefResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.affectationChef.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
