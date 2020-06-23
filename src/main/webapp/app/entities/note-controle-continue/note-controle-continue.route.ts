import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { NoteControleContinueComponent } from './note-controle-continue.component';
import { ListGroupEtudiantComponent } from './list-group-etudiant/list-group-etudiant.component';

export const note_continue_Rotue: Routes = [
  {
    path: '',
    component: NoteControleContinueComponent,

    data: {
      authorities: ['ROLE_Enseignant','ROLE_ChefDepartement'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.matiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/:idmatiere/group',
    component: ListGroupEtudiantComponent,

    data: {
      authorities: ['ROLE_Enseignant','ROLE_ChefDepartement'],
      defaultSort: 'id,asc',
      pageTitle: 'gestionNotesFsegsApp.matiere.home.title'
    }
  }
];
