import { Resultat } from './../../../../shared/model/resultat.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

import { ResultatService } from 'app/entities/resultat/resultat.service';
import { IEtudiantNote } from 'app/shared/model/etudiantNote';

@Component({
  selector: 'edit-note',
  templateUrl: './edit-note.component.html'
})
export class EditNoteComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  matiereName: string = '';
  etudiantnotes: IEtudiantNote = {};
  matiereid: number = 0;
  resultat: Resultat = {};
  resultatid: number = 0;
  parent: any;
  modalref: any;
  message1: string='';
  message2: string="";
  invalidnote1:boolean=true;
  
  invalidnote2:boolean=true;
  constructor(private resultatService: ResultatService) {}

  ngOnInit() {
    this.invalidnote1=true;
    this.invalidnote2=true;
    if (this.etudiantnotes.notecc1 || this.etudiantnotes.notecc2) {
      this.resultatService
        .query({
          'inscriptionId.equals': this.etudiantnotes.idinscription,
          'matiereId.equals': this.matiereid
        })
        .subscribe(resultat => {
          if (resultat.body && resultat.body.length > 0) {
            if (resultat.body[0].id) this.onSuccess(resultat.body[0].id);
          }
        });
    }
  }
  onSuccess(id: number) {
    this.resultatid = id;
  }
  save() {
    this.resultat.inscriptionId = this.etudiantnotes.idinscription;
    this.resultat.matiereId = this.matiereid;
    this.resultat.notecc1 = this.etudiantnotes.notecc1;
    this.resultat.notecc2 = this.etudiantnotes.notecc2;

    if (this.resultatid > 0) {
      this.resultat.id = this.resultatid;
      this.resultatService.update(this.resultat).subscribe(res => {
        this.modalref.close();
      });
      return;
    } else {
      this.resultatService.create(this.resultat).subscribe(res => {
        this.onUpdateSuccess();
      });
    }
  }
  onUpdateSuccess() {
    this.parent.loadInscription();
    this.modalref.close();
  }
  cheaknotecc1(){
    this.message1=""
    if(this.etudiantnotes.notecc1){
    if(this.etudiantnotes.notecc1<0 ||this.etudiantnotes.notecc1>20)
    {
      this.invalidnote1=true
      this.message1='notecc1 doit etre entre 0 et 20'
    }else{
      this.invalidnote1=false
    }
    }
  }
  cheaknotecc2(){
    this.message2=""
    if(this.etudiantnotes.notecc2){
    if(this.etudiantnotes.notecc2<0 ||this.etudiantnotes.notecc2>20)
    {
      this.invalidnote2=true
      this.message2='notecc2 doit etre entre 0 et 20'
    }else{
      this.invalidnote2=false
    }
    }
  }
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
