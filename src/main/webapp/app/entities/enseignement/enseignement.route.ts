import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnseignement, Enseignement } from 'app/shared/model/enseignement.model';
import { EnseignementService } from './enseignement.service';
import { EnseignementComponent } from './enseignement.component';
import { EnseignementDetailComponent } from './enseignement-detail.component';
import { EnseignementUpdateComponent } from './enseignement-update.component';

@Injectable({ providedIn: 'root' })
export class EnseignementResolve implements Resolve<IEnseignement> {
  constructor(private service: EnseignementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnseignement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enseignement: HttpResponse<Enseignement>) => {
          if (enseignement.body) {
            return of(enseignement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Enseignement());
  }
}

export const enseignementRoute: Routes = [
  {
    path: '',
    component: EnseignementComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.enseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnseignementDetailComponent,
    resolve: {
      enseignement: EnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.enseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnseignementUpdateComponent,
    resolve: {
      enseignement: EnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.enseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnseignementUpdateComponent,
    resolve: {
      enseignement: EnseignementResolve
    },
    data: {
      authorities: ['ROLE_ChefDepartement'],
      pageTitle: 'gestionNotesFsegsApp.enseignement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
