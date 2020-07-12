import { ExamenComponent } from './examen.component';

import { Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const exmanRoute: Routes = [
  {
    path: '',
    component: ExamenComponent,

    data: {
      authorities: ['ROLE_AGENT'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.etudiant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
