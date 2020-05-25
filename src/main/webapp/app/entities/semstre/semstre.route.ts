import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISemstre, Semstre } from 'app/shared/model/semstre.model';
import { SemstreService } from './semstre.service';
import { SemstreComponent } from './semstre.component';
import { SemstreDetailComponent } from './semstre-detail.component';
import { SemstreUpdateComponent } from './semstre-update.component';

@Injectable({ providedIn: 'root' })
export class SemstreResolve implements Resolve<ISemstre> {
  constructor(private service: SemstreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISemstre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((semstre: HttpResponse<Semstre>) => {
          if (semstre.body) {
            return of(semstre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Semstre());
  }
}

export const semstreRoute: Routes = [
  {
    path: '',
    component: SemstreComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.semstre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SemstreDetailComponent,
    resolve: {
      semstre: SemstreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionNotesFsegsApp.semstre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SemstreUpdateComponent,
    resolve: {
      semstre: SemstreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionNotesFsegsApp.semstre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SemstreUpdateComponent,
    resolve: {
      semstre: SemstreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionNotesFsegsApp.semstre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
