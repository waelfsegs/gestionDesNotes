import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { SemstreComponent } from './semstre.component';
import { SemstreDetailComponent } from './semstre-detail.component';
import { SemstreUpdateComponent } from './semstre-update.component';
import { SemstreDeleteDialogComponent } from './semstre-delete-dialog.component';
import { semstreRoute } from './semstre.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(semstreRoute)],
  declarations: [SemstreComponent, SemstreDetailComponent, SemstreUpdateComponent, SemstreDeleteDialogComponent],
  entryComponents: [SemstreDeleteDialogComponent]
})
export class GestionNotesFsegsSemstreModule {}
