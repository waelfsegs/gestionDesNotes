import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { EnseignementComponent } from './enseignement.component';
import { EnseignementDetailComponent } from './enseignement-detail.component';
import { EnseignementUpdateComponent } from './enseignement-update.component';
import { EnseignementDeleteDialogComponent } from './enseignement-delete-dialog.component';
import { enseignementRoute } from './enseignement.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(enseignementRoute)],
  declarations: [EnseignementComponent, EnseignementDetailComponent, EnseignementUpdateComponent, EnseignementDeleteDialogComponent],
  entryComponents: [EnseignementDeleteDialogComponent],
})
export class GestionNotesFsegsEnseignementModule {}
