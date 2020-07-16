import { CorrigeService } from './corrige.service';
import { IResultat } from './../../shared/model/resultat.model';
import { ResultatService } from './../resultat/resultat.service';
import { IExamen } from './../../shared/model/examen.model';

import { Component, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';

import { TblExamenService } from '../tbl-examen-affecter/tblexamen.service';
import { Corrige } from 'app/shared/model/corrige.model';
import { timeStamp } from 'console';

@Component({
  selector: 'jhi-corrige-detail',
  templateUrl: './corrige-detail.component.html'
})
export class CorrigeDetailComponent implements OnChanges {
  @Input('idEnv') idEnv: number = 0;
  @Input('corrige') corrige: Corrige = {};
  @Output('ischange') ischange = new EventEmitter();
  message: string = '';
  examens: IExamen[] = [];
  constructor(
    protected examenService: TblExamenService,
    private resultatService: ResultatService,
    private corrigeService: CorrigeService
  ) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (this.idEnv > 0) {
      this.examenService.query({ 'enveloppeId.equals': this.idEnv }).subscribe(res => {
        if (res.body) this.loadExamen(res.body);
      });
    }
  }
  loadExamen(examens: IExamen[]) {
    this.examens = [];
    examens.forEach(element => {
      console.log('matiere', element.matiereId);
      console.log('ins', element.inscriptionId);
      this.resultatService
        .query({
          'inscriptionId.equals': element.inscriptionId,
          'matiereId.equals': element.matiereId
        })
        .subscribe(res => {
          if (res.body && res.body[0]) {
            element.resultatid = res.body[0].id;

            element.note = res.body[0].noteexmen;
            if (res.body[0].noteexmen) {
              element.done = true;
            }
          }
          this.examens.push(element);
        });
    });
  }
  modifier(examen: IExamen) {
    examen.forEdit = true;
  }
  edit(examen: IExamen) {
    this.message = '';
    if (examen.note && examen.note > -1 && examen.note < 21) {
      console.log(examen.resultatid);
      let resultat: IResultat = {};
      let corrige: Corrige = this.corrige;
      if (examen.resultatid) {
        this.resultatService.find(examen.resultatid).subscribe(res => {
          if (res.body) {
            resultat = res.body;
            resultat.noteexmen = examen.note;

            this.resultatService.update(resultat).subscribe(res => {
              if (this.corrige.nbrecopieCorrige != undefined && this.corrige.nbrecopieCorrige >= 0) {
                console.log('if', this.corrige.nbrecopieCorrige);
                corrige.nbrecopieCorrige = this.corrige.nbrecopieCorrige + 1;
              } else {
                console.log('else', this.corrige.nbrecopieCorrige);
                corrige.nbrecopieCorrige = 1;
              }
              this.corrigeService.update(corrige).subscribe(res => {
                examen.done = true;
                this.ischange.emit();
              });
            });
          }
        });
      } else {
        resultat.inscriptionId = examen.inscriptionId;
        resultat.matiereId = examen.matiereId;
        resultat.noteexmen = examen.note;
        this.resultatService.create(resultat).subscribe(res => {
          if (this.corrige.nbrecopieCorrige != undefined && this.corrige.nbrecopieCorrige >= 0) {
            console.log('if', this.corrige.nbrecopieCorrige);
            corrige.nbrecopieCorrige = this.corrige.nbrecopieCorrige + 1;
          } else {
            console.log('else', this.corrige.nbrecopieCorrige);
            corrige.nbrecopieCorrige = 1;
          }
          this.corrigeService.update(corrige).subscribe(res => {
            examen.done = true;
            this.ischange.emit();
          });
        });
      }
    } else {
      this.message = 'les note doit etre entre 0 et 20';
    }
  }
  editnote(examen: IExamen) {
    this.message = '';
    if (examen.note && examen.note > -1 && examen.note < 21) {
      console.log(examen.resultatid);
      let resultat: IResultat = {};
      let corrige: Corrige = this.corrige;
      if (examen.resultatid) {
        this.resultatService.find(examen.resultatid).subscribe(res => {
          if (res.body) {
            resultat = res.body;
            resultat.noteexmen = examen.note;
            this.resultatService.update(resultat).subscribe(res => (examen.forEdit = false));
          }
        });
      }
    } else {
      this.message = 'les note doit etre entre 0 et 20';
    }
  }
}
