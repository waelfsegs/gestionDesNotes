import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICorrige, Corrige } from 'app/shared/model/corrige.model';
import { CorrigeService } from './corrige.service';
import { CorrigeComponent } from './corrige.component';
import { CorrigeDetailComponent } from './corrige-detail.component';
import { CorrigeUpdateComponent } from './corrige-update.component';

@Injectable({ providedIn: 'root' })
export class CorrigeResolve implements Resolve<ICorrige> {
  constructor(private service: CorrigeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICorrige> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((corrige: HttpResponse<Corrige>) => {
          if (corrige.body) {
            return of(corrige.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Corrige());
  }
}

export const corrigeRoute: Routes = [
  {
    path: '',
    component: CorrigeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_Enseignant'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.corrige.home.title'
    },
    canActivate: [UserRouteAccessService]
  },

  {
    path: 'new',
    component: CorrigeUpdateComponent,
    resolve: {
      corrige: CorrigeResolve
    },
    data: {
      authorities: ['ROLE_Enseignant'],
      pageTitle: 'gestionNotesFsegsApp.corrige.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CorrigeUpdateComponent,
    resolve: {
      corrige: CorrigeResolve
    },
    data: {
      authorities: ['ROLE_Enseignant'],
      pageTitle: 'gestionNotesFsegsApp.corrige.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
