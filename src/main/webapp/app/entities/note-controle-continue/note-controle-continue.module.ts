import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { note_continue_Rotue } from './note-controle-continue.route';
import { NoteControleContinueComponent } from './note-controle-continue.component';
import { ListMatiereEnsiegnentComponent } from './list-matiere-enseignent/list-matiere-enseignent.component';
import { ListGroupEtudiantComponent } from './list-group-etudiant/list-group-etudiant.component';
import { ListGroupMatiereComponent } from './list-group-matiere/list-group-matiere.component';
import { EditNoteComponent } from './list-group-etudiant/edit-note/edit-note.component';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(note_continue_Rotue)],
  declarations: [
    NoteControleContinueComponent,
    ListMatiereEnsiegnentComponent,
    ListGroupEtudiantComponent,
    ListGroupMatiereComponent,
    EditNoteComponent,
    EditNoteComponent
  ],
  entryComponents: [EditNoteComponent]
})
export class GestionNotesFsegSNoteControlContinueModule {}
