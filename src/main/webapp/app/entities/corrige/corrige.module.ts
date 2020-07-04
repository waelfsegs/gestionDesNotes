import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { CorrigeComponent } from './corrige.component';
import { CorrigeDetailComponent } from './corrige-detail.component';
import { CorrigeUpdateComponent } from './corrige-update.component';
import { CorrigeDeleteDialogComponent } from './corrige-delete-dialog.component';
import { corrigeRoute } from './corrige.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(corrigeRoute)],
  declarations: [CorrigeComponent, CorrigeDetailComponent, CorrigeUpdateComponent, CorrigeDeleteDialogComponent],
  entryComponents: [CorrigeDeleteDialogComponent, CorrigeDetailComponent]
})
export class GestionNotesFsegsCorrigeModule {}
