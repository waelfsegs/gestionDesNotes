import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'etudiant',
        loadChildren: () => import('./etudiant/etudiant.module').then(m => m.GestionNotesFsegsEtudiantModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GestionNotesFsegsEntityModule {}
