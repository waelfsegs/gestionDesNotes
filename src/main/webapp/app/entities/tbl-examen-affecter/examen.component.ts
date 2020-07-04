import { EtudiantService } from './../etudiant/etudiant.service';
import { InscriptionService } from './../inscription/inscription.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { EnseignantService } from './../enseignant/enseignant.service';
import { CorrigeService } from './../corrige/corrige.service';
import { EnveloppeService } from 'app/entities/enveloppe/enveloppe.service';

import { Enseignant } from './../../shared/model/enseignant.model';
import { Corrige } from './../../shared/model/corrige.model';
import { Enveloppe } from './../../shared/model/enveloppe.model';
import { Examen, IExamen } from './../../shared/model/examen.model';
import Swal from 'sweetalert2';
import { TblExamenService } from './tblexamen.service';

@Component({
  selector: 'examen',
  templateUrl: './examen.component.html'
})
export class ExamenComponent implements OnInit, OnDestroy {
  session: string = '';
  ensiegId: number = 0;
  ensiegnant$: Observable<Enseignant[]> = of([]);
  examen: Examen = {};

  enveloppe: Enveloppe = {};
  corrige: Corrige = {};
  start: number = 0;
  end: number = 0;
  tblNumCompose: IExamen[] = [];

  constructor(
    private ensiegnantService: EnseignantService,
    private examenService: TblExamenService,
    private envollopeService: EnveloppeService,
    private corrigeService: CorrigeService,
    private inscriptionService: InscriptionService,
    private etudiantService: EtudiantService
  ) {}

  ngOnInit() {
    this.ensiegnant$ = this.ensiegnantService.query().pipe(map(res => (res.body ? res.body : [])));
  }
  selectNiveau(id: number) {
    this.examen.niveauId = id;
  }
  selectcycle(id: number) {
    this.examen.cycleId = id;
  }
  selectMatiere(id: number) {
    this.examen.matiereId = id;
  }
  selectspeciality(id: number) {
    this.examen.specialiteId = id;
  }
  selectSmestre(id: number) {
    this.examen.semstreId = id;
  }
  valid() {
    for (let i = this.start; i <= this.end; i++) {
      let li: Examen = {};

      li.numcomp = i;
      this.tblNumCompose.push(li);
    }
    this.enveloppe.maiereId = this.examen.matiereId;

    this.envollopeService.create(this.enveloppe).subscribe(res => {
      if (res.body) {
        this.enveloppe.id = res.body.id;
        this.savecorrige();
        this.tblNumCompose.forEach(element => {
          this.examen.numcomp = element.numcomp;
          this.saveExamen(element);
        });
      }
    });
  }

  saveExamen(element: IExamen) {
    this.examen.enveloppeId = this.enveloppe.id;
    this.examenService.create(this.examen).subscribe(res => {
      if (res.body) element.id = res.body.id;
    });
  }
  savecorrige() {
    this.corrige.nbreCopieinenvloppe = this.tblNumCompose.length;
    this.corrige.nbrecopieCorrige = 0;
    this.corrige.enveloppeId = this.enveloppe.id;
    this.corrigeService.create(this.corrige).subscribe();
  }
  edit(row: IExamen) {
    this.getinscriptionbyId(row);
  }
  getinscriptionbyId(row: IExamen) {
    this.etudiantService.query({ 'matricule.equals': row.matricule }).subscribe(res => {
      if (res.body) {
        if (res.body[0]) {
          console.log(res.body[0]);
          this.inscriptionService.query({ 'etudiantId.equals': res.body[0].id }).subscribe(res => {
            if (res.body && res.body[0]) {
              row.cycleId = this.examen.cycleId;
              row.niveauId = this.examen.niveauId;
              row.specialiteId = this.examen.specialiteId;
              row.enveloppeId = this.examen.enveloppeId;
              row.matiereId = this.examen.matiereId;
              row.session = this.examen.session;
              row.inscriptionId = res.body[0].id;
              row.semstreId = this.examen.semstreId;
              console.log(row.id);
              this.examenService.update(row).subscribe(res => {
                row.done = true;
              });
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'soit sur que l etudiant deja inscrit!'
              });
            }
          });
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'cette matricule n exsite pas!'
          });
        }
      }
    });
    //
  }
  modifier(row: IExamen) {
    row.done = false;
  }

  ngOnDestroy() {}
}
