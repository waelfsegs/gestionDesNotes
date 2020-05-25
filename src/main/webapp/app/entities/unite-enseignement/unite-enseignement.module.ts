import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { UniteEnseignementComponent } from './unite-enseignement.component';
import { UniteEnseignementDetailComponent } from './unite-enseignement-detail.component';
import { UniteEnseignementUpdateComponent } from './unite-enseignement-update.component';
import { UniteEnseignementDeleteDialogComponent } from './unite-enseignement-delete-dialog.component';
import { uniteEnseignementRoute } from './unite-enseignement.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(uniteEnseignementRoute)],
  declarations: [
    UniteEnseignementComponent,
    UniteEnseignementDetailComponent,
    UniteEnseignementUpdateComponent,
    UniteEnseignementDeleteDialogComponent,
  ],
  entryComponents: [UniteEnseignementDeleteDialogComponent],
})
export class GestionNotesFsegsUniteEnseignementModule {}
