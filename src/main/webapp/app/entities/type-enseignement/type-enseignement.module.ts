import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { TypeEnseignementComponent } from './type-enseignement.component';
import { TypeEnseignementDetailComponent } from './type-enseignement-detail.component';
import { TypeEnseignementUpdateComponent } from './type-enseignement-update.component';
import { TypeEnseignementDeleteDialogComponent } from './type-enseignement-delete-dialog.component';
import { typeEnseignementRoute } from './type-enseignement.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(typeEnseignementRoute)],
  declarations: [
    TypeEnseignementComponent,
    TypeEnseignementDetailComponent,
    TypeEnseignementUpdateComponent,
    TypeEnseignementDeleteDialogComponent,
  ],
  entryComponents: [TypeEnseignementDeleteDialogComponent],
})
export class GestionNotesFsegsTypeEnseignementModule {}
