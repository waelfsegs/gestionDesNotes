import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { ResultatComponent } from './resultat.component';
import { ResultatDetailComponent } from './resultat-detail.component';
import { ResultatUpdateComponent } from './resultat-update.component';
import { ResultatDeleteDialogComponent } from './resultat-delete-dialog.component';
import { resultatRoute } from './resultat.route';
import { resultatClasseComponent } from './resultat-agent/resultat-classe/resultat-classe.component';
import { resultatAgentComponent } from './resultat-agent/resultat-agent.component';
import { ClasseSpcialiteComponent } from './resultat-agent/classe-spcialite/classe-spcialite.component';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(resultatRoute)],
  declarations: [
    ResultatComponent,
    ResultatDetailComponent,
    ResultatUpdateComponent,
    ResultatDeleteDialogComponent,
    resultatClasseComponent,
    resultatAgentComponent,
    ClasseSpcialiteComponent
  ],
  entryComponents: [ResultatDeleteDialogComponent]
})
export class GestionNotesFsegsResultatModule {}
