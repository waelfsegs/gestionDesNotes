import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { SpicialitematiereComponent } from './spicialitematiere.component';
import { SpicialitematiereDetailComponent } from './spicialitematiere-detail.component';
import { SpicialitematiereUpdateComponent } from './spicialitematiere-update.component';
import { SpicialitematiereDeleteDialogComponent } from './spicialitematiere-delete-dialog.component';
import { spicialitematiereRoute } from './spicialitematiere.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(spicialitematiereRoute)],
  declarations: [
    SpicialitematiereComponent,
    SpicialitematiereDetailComponent,
    SpicialitematiereUpdateComponent,
    SpicialitematiereDeleteDialogComponent
  ],
  entryComponents: [SpicialitematiereDeleteDialogComponent]
})
export class GestionNotesFsegsSpicialitematiereModule {}
