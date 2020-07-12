import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISpicialitematiere, Spicialitematiere } from 'app/shared/model/spicialitematiere.model';
import { SpicialitematiereService } from './spicialitematiere.service';
import { SpicialitematiereComponent } from './spicialitematiere.component';
import { SpicialitematiereDetailComponent } from './spicialitematiere-detail.component';
import { SpicialitematiereUpdateComponent } from './spicialitematiere-update.component';

@Injectable({ providedIn: 'root' })
export class SpicialitematiereResolve implements Resolve<ISpicialitematiere> {
  constructor(private service: SpicialitematiereService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpicialitematiere> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((spicialitematiere: HttpResponse<Spicialitematiere>) => {
          if (spicialitematiere.body) {
            return of(spicialitematiere.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Spicialitematiere());
  }
}

export const spicialitematiereRoute: Routes = [
  {
    path: '',
    component: SpicialitematiereComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.spicialitematiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpicialitematiereDetailComponent,
    resolve: {
      spicialitematiere: SpicialitematiereResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.spicialitematiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpicialitematiereUpdateComponent,
    resolve: {
      spicialitematiere: SpicialitematiereResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.spicialitematiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpicialitematiereUpdateComponent,
    resolve: {
      spicialitematiere: SpicialitematiereResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'gestionNotesFsegsApp.spicialitematiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
