import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { AffectationChefComponent } from './affectation-chef.component';
import { AffectationChefDetailComponent } from './affectation-chef-detail.component';
import { AffectationChefUpdateComponent } from './affectation-chef-update.component';
import { AffectationChefDeleteDialogComponent } from './affectation-chef-delete-dialog.component';
import { affectationChefRoute } from './affectation-chef.route';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(affectationChefRoute)],
  declarations: [
    AffectationChefComponent,
    AffectationChefDetailComponent,
    AffectationChefUpdateComponent,
    AffectationChefDeleteDialogComponent,
  ],
  entryComponents: [AffectationChefDeleteDialogComponent],
})
export class GestionNotesFsegsAffectationChefModule {}
