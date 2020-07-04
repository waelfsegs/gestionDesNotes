import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { EnveloppeComponent } from './enveloppe.component';
import { EnveloppeDetailComponent } from './enveloppe-detail.component';
import { EnveloppeUpdateComponent } from './enveloppe-update.component';
import { EnveloppeDeleteDialogComponent } from './enveloppe-delete-dialog.component';
import { enveloppeRoute } from './enveloppe.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(enveloppeRoute)],
  declarations: [EnveloppeComponent, EnveloppeDetailComponent, EnveloppeUpdateComponent, EnveloppeDeleteDialogComponent],
  entryComponents: [EnveloppeDeleteDialogComponent]
})
export class GestionNotesFsegsEnveloppeModule {}
