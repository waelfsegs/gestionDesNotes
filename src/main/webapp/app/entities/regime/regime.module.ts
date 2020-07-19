import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { RegimeComponent } from './regime.component';
import { RegimeDetailComponent } from './regime-detail.component';
import { RegimeUpdateComponent } from './regime-update.component';
import { RegimeDeleteDialogComponent } from './regime-delete-dialog.component';
import { regimeRoute } from './regime.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(regimeRoute)],
  declarations: [RegimeComponent, RegimeDetailComponent, RegimeUpdateComponent, RegimeDeleteDialogComponent],
  entryComponents: [RegimeDeleteDialogComponent]
})
export class GestionNotesFsegsRegimeModule {}
