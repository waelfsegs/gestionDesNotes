import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { NiveauComponent } from './niveau.component';
import { NiveauDetailComponent } from './niveau-detail.component';
import { NiveauUpdateComponent } from './niveau-update.component';
import { NiveauDeleteDialogComponent } from './niveau-delete-dialog.component';
import { niveauRoute } from './niveau.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(niveauRoute)],
  declarations: [NiveauComponent, NiveauDetailComponent, NiveauUpdateComponent, NiveauDeleteDialogComponent],
  entryComponents: [NiveauDeleteDialogComponent]
})
export class GestionNotesFsegsNiveauModule {}
