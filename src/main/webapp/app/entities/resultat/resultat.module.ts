import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { ResultatComponent } from './resultat.component';
import { ResultatDetailComponent } from './resultat-detail.component';
import { ResultatUpdateComponent } from './resultat-update.component';
import { ResultatDeleteDialogComponent } from './resultat-delete-dialog.component';
import { resultatRoute } from './resultat.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(resultatRoute)],
  declarations: [ResultatComponent, ResultatDetailComponent, ResultatUpdateComponent, ResultatDeleteDialogComponent],
  entryComponents: [ResultatDeleteDialogComponent],
})
export class GestionNotesFsegsResultatModule {}
