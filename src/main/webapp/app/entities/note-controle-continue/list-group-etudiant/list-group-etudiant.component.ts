import { IEtudiantNote } from './../../../shared/model/etudiantNote';

import { MatiereService } from './../../matiere/matiere.service';

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Subject } from 'rxjs';

import { ResultatService } from './../../resultat/resultat.service';
import { InscriptionService } from './../../inscription/inscription.service';

import { EtudiantService } from './../../etudiant/etudiant.service';

import { IInscription } from 'app/shared/model/inscription.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';
import { EditNoteComponent } from './edit-note/edit-note.component';

@Component({
  selector: 'list-group-etudiant',
  templateUrl: './list-group-etudiant.component.html',
  styleUrls: ['list-group-etudiant.scss']
})
export class ListGroupEtudiantComponent implements OnDestroy, OnInit {
  unsubscribe = new Subject();
  etudiantnotes: IEtudiantNote[] = [];
  groupid: number = 0;
  matiereid: number = 0;
  matiereName: string = '';
  groupName: string = '';
  matricule: string = '';
  constructor(
    private etudiantService: EtudiantService,
    private inscriptionService: InscriptionService,
    private resultatService: ResultatService,
    private modaleService: NgbModal,
    private matiereService: MatiereService,
    private groupService: GroupeService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(param => {
      if (param) {
        this.groupid = Number(param.get('id'));
        this.matiereid = Number(param.get('idmatiere'));
        console.log(this.matiereid);
        this.getGroupBYid(this.groupid);
        this.getMatiereBYid(this.matiereid);
        this.loadInscription();
      }
    });
  }
  getMatiereBYid(id: number) {
    this.matiereService.find(id).subscribe(matiere => {
      if (matiere.body && matiere.body.nom) this.matiereName = matiere.body.nom;
    });
  }
  getGroupBYid(id: number) {
    this.groupService.find(id).subscribe(groupe => {
      if (groupe.body && groupe.body.nomgroup) this.groupName = groupe.body.nomgroup;
    });
  }
  loadInscription() {
    this.etudiantnotes = [];
    this.inscriptionService
      .query({
        'groupeId.equals': this.groupid
      })
      .subscribe(res => {
        if (res.body) this.prpareListEtudiant(res.body);
      });
  }
  prpareListEtudiant(data: IInscription[]) {
    data.forEach(element => {
      let etudiantnote: IEtudiantNote = {};
      if (element.etudiantId) {
        this.etudiantService.find(element.etudiantId).subscribe(etudiant => {
          if (etudiant.body) {
            etudiantnote.nom = etudiant.body.nom;
            etudiantnote.prenom = etudiant.body.prenom;
            etudiantnote.cin = etudiant.body.cin;
            etudiantnote.matricule = etudiant.body.matricule;
            etudiantnote.idinscription = element.id;
            if (element.id) this.getNoteByInscription(element.id, etudiantnote);
            this.etudiantnotes.push(etudiantnote);
          }
        });
      }
    });
  }
  getNoteByInscription(idinscription: number, etudiantnote: IEtudiantNote) {
    this.resultatService.query({ 'inscriptionId.equals': idinscription }).subscribe(resultat => {
      if (resultat.body && resultat.body.length > 0) {
        etudiantnote.notecc1 = resultat.body[0].notecc1;
        etudiantnote.notecc2 = resultat.body[0].notecc2;
      }
    });
  }

  openEdit(etudiantnote: IEtudiantNote) {
    const modelRef: NgbModalRef = this.modaleService.open(EditNoteComponent as Component);
    modelRef.componentInstance.etudiantnotes = etudiantnote;
    modelRef.componentInstance.matiereid = this.matiereid;
    modelRef.componentInstance.parent = this;
    modelRef.componentInstance.matiereName = this.matiereName;
    modelRef.componentInstance.modalref = modelRef;
  }
  clear() {
    console.log('heloo');
    if (!this.matricule) {
      this.loadInscription();
    }
  }
  searchMat() {
    if (this.matricule) {
      let filterd: IEtudiantNote[] = [];
      this.etudiantnotes.forEach(element => {
        if (element.matricule == this.matricule) {
          console.log(element);
          filterd.push(element);
        }
      });
      this.etudiantnotes = [];
      this.etudiantnotes = filterd;
    } else {
      this.loadInscription();
    }
  }

  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
