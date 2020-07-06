import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnveloppe, Enveloppe } from 'app/shared/model/enveloppe.model';
import { EnveloppeService } from './enveloppe.service';
import { EnveloppeComponent } from './enveloppe.component';
import { EnveloppeDetailComponent } from './enveloppe-detail.component';
import { EnveloppeUpdateComponent } from './enveloppe-update.component';

@Injectable({ providedIn: 'root' })
export class EnveloppeResolve implements Resolve<IEnveloppe> {
  constructor(private service: EnveloppeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnveloppe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enveloppe: HttpResponse<Enveloppe>) => {
          if (enveloppe.body) {
            return of(enveloppe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Enveloppe());
  }
}

export const enveloppeRoute: Routes = [
  {
    path: '',
    component: EnveloppeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_Enseignant'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.enveloppe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnveloppeDetailComponent,
    resolve: {
      enveloppe: EnveloppeResolve
    },
    data: {
      authorities: ['ROLE_Enseignant '],
      pageTitle: 'gestionNotesFsegsApp.enveloppe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnveloppeUpdateComponent,
    resolve: {
      enveloppe: EnveloppeResolve
    },
    data: {
      authorities: ['ROLE_Enseignant '],
      pageTitle: 'gestionNotesFsegsApp.enveloppe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnveloppeUpdateComponent,
    resolve: {
      enveloppe: EnveloppeResolve
    },
    data: {
      authorities: ['ROLE_Enseignant '],
      pageTitle: 'gestionNotesFsegsApp.enveloppe.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
