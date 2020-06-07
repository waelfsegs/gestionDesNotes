import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { NoteControleContinueComponent } from './note-controle-continue.component';

export const note_continue_Rotue: Routes = [
  {
    path: '',
    component: NoteControleContinueComponent,

    data: {
      authorities: ['ROLE_Enseignant'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.matiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
