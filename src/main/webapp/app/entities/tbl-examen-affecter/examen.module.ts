import { exmanRoute } from './examen.route';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionNotesFsegsSharedModule } from 'app/shared/shared.module';
import { ExamenComponent } from './examen.component';

@NgModule({
  imports: [GestionNotesFsegsSharedModule, RouterModule.forChild(exmanRoute)],
  declarations: [ExamenComponent],
  entryComponents: []
})
export class GestionNotesFsegsExamenModule {}
