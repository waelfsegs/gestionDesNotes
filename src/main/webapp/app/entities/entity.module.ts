import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'etudiant',
        loadChildren: () => import('./etudiant/etudiant.module').then(m => m.GestionNotesFsegsEtudiantModule)
      },
      {
        path: 'classe',
        loadChildren: () => import('./classe/classe.module').then(m => m.GestionNotesFsegsClasseModule)
      },
      {
        path: 'specialite',
        loadChildren: () => import('./specialite/specialite.module').then(m => m.GestionNotesFsegsSpecialiteModule)
      },
      {
        path: 'niveau',
        loadChildren: () => import('./niveau/niveau.module').then(m => m.GestionNotesFsegsNiveauModule)
      },
      {
        path: 'cycle',
        loadChildren: () => import('./cycle/cycle.module').then(m => m.GestionNotesFsegsCycleModule)
      },
      {
        path: 'resultat',
        loadChildren: () => import('./resultat/resultat.module').then(m => m.GestionNotesFsegsResultatModule)
      },
      {
        path: 'matiere',
        loadChildren: () => import('./matiere/matiere.module').then(m => m.GestionNotesFsegsMatiereModule)
      },
      {
        path: 'groupe',
        loadChildren: () => import('./groupe/groupe.module').then(m => m.GestionNotesFsegsGroupeModule)
      },
      {
        path: 'inscription',
        loadChildren: () => import('./inscription/inscription.module').then(m => m.GestionNotesFsegsInscriptionModule)
      },
      {
        path: 'semstre',
        loadChildren: () => import('./semstre/semstre.module').then(m => m.GestionNotesFsegsSemstreModule)
      },
      {
        path: 'enseignant',
        loadChildren: () => import('./enseignant/enseignant.module').then(m => m.GestionNotesFsegsEnseignantModule)
      },
      {
        path: 'affectation-chef',
        loadChildren: () => import('./affectation-chef/affectation-chef.module').then(m => m.GestionNotesFsegsAffectationChefModule)
      },
      {
        path: 'departement',
        loadChildren: () => import('./departement/departement.module').then(m => m.GestionNotesFsegsDepartementModule)
      },
      {
        path: 'unite-enseignement',
        loadChildren: () => import('./unite-enseignement/unite-enseignement.module').then(m => m.GestionNotesFsegsUniteEnseignementModule)
      },
      {
        path: 'enseignement',
        loadChildren: () => import('./enseignement/enseignement.module').then(m => m.GestionNotesFsegsEnseignementModule)
      },
      {
        path: 'regime',
        loadChildren: () => import('./regime/regime.module').then(m => m.GestionNotesFsegsRegimeModule)
      },
      {
        path: 'type-enseignement',
        loadChildren: () => import('./type-enseignement/type-enseignement.module').then(m => m.GestionNotesFsegsTypeEnseignementModule)
      },
      {
        path: 'note-controle-continue',
        loadChildren: () =>
          import('./note-controle-continue/note-controle-continue.module').then(m => m.GestionNotesFsegSNoteControlContinueModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GestionNotesFsegsEntityModule {}
