import { NgModule } from '@angular/core';
import { GestionNotesFsegsSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { SelectMatiereComponent } from './select-matiere/select-matiere.component';
import { selectNiveauComponent } from './select-niveau/select-niveau.component';
import { selectSpecialityComponent } from './select-speciality/select-speciality.component';
import { selectSemesterComponent } from './select-semester/select-semester.component';
import { selectCycleComponent } from './select-cycle/select-cycle.component';

@NgModule({
  imports: [GestionNotesFsegsSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    SelectMatiereComponent,
    selectNiveauComponent,
    selectSpecialityComponent,
    selectSemesterComponent,
    selectCycleComponent
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    GestionNotesFsegsSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    SelectMatiereComponent,
    selectNiveauComponent,
    selectSpecialityComponent,
    selectSemesterComponent,
    selectCycleComponent
  ]
})
export class GestionNotesFsegsSharedModule {}
