import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { CycleComponent } from './cycle.component';
import { CycleDetailComponent } from './cycle-detail.component';
import { CycleUpdateComponent } from './cycle-update.component';
import { CycleDeleteDialogComponent } from './cycle-delete-dialog.component';
import { cycleRoute } from './cycle.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(cycleRoute)],
  declarations: [CycleComponent, CycleDetailComponent, CycleUpdateComponent, CycleDeleteDialogComponent],
  entryComponents: [CycleDeleteDialogComponent],
})
export class GestionNotesFsegsCycleModule {}
