import { ResultatService } from 'app/entities/resultat/resultat.service';
import { MatiereService } from './../../../matiere/matiere.service';
import { ClasseService } from './../../../classe/classe.service';
import { IClasse } from './../../../../shared/model/classe.model';
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, flatMap, distinct, concatMap, takeUntil } from 'rxjs/operators';
import { EnseignementService } from 'app/entities/enseignement/enseignement.service';
import { Inote } from 'app/shared/model/note';
import { IMatiere } from 'app/shared/model/matiere.model';
import { IResultat } from 'app/shared/model/resultat.model';
import { ExcelService } from './Excel-service';

@Component({
  selector: 'resultat-classe',
  templateUrl: './resultat-classe.component.html',
  styleUrls:['resultat-classe.scss']
})
export class resultatClasseComponent implements OnInit, OnChanges {
  @Input('classid') classid: number = 0;
  @Input('inscriptionid') inscriptionid: number = 0;
  resultats?: IResultat[];
  notes: Inote[] = [];
  class$: Observable<IClasse[] | null> = of([]);
  constructor(
    protected activatedRoute: ActivatedRoute,
    private enseignementService: EnseignementService,
    private matiereService: MatiereService,
    private resultatService: ResultatService,
    private excelService: ExcelService
  ) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (this.classid > 0 && this.inscriptionid > 0) {
      this.loadresultat();
    }
  }
  loadresultat() {
    if (this.inscriptionid) {
      let note: Inote = {};
      let matiere: IMatiere = {};
      let i = 0;
      this.notes = [];
      this.enseignementService
        .query({ 'classeId.equals': this.classid })
        .pipe(
          map(res => res.body),
          flatMap(res => (res ? res : of(null))),
          distinct(res => (res ? res.matiereId : null)),
          concatMap(ensigment => {
            if (ensigment && ensigment.matiereId) {
              return this.matiereService.find(ensigment.matiereId);
            }
            return of(null);
          }),
          concatMap(matier => {
            if (matier && matier.body) {
              matiere = {};
              matiere = matier.body;
              note = {};
              note.coefficientMatiere = matiere.coefficientMatiere;
              note.nom = matiere.nom;
              note.regime = matiere.regime;
              return this.resultatService
                .query({ 'inscriptionId.equals': this.inscriptionid, 'matiereId.equals': matiere.id })
                .pipe(map(res => res.body));
            }
            return of(null);
          })
        )
        .subscribe(resultat => {
          if (resultat) {
            if (resultat[0]) {
              note.notecc1 = resultat[0].notecc1;
              note.notecc2 = resultat[0].notecc2;
              note.examne = resultat[0].noteexmen;
            }
          }
          this.notes.push(note);
        });
    }
  }
  exportexcel() {
    this.excelService.exportAsExcelFile(this.notes, 'sample');
  }
  ngOnInit(): void {}
}
