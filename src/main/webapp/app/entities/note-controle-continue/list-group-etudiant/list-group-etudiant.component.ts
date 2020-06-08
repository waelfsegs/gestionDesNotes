import { takeUntil, take } from 'rxjs/operators';
import { NoteControleContinueService } from './../note-controle-continue.service';
import { Component, OnInit, OnDestroy, OnChanges, Input, SimpleChanges } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';
import { IGroupbyetudiant } from 'app/shared/model/group-by-etudiant';

@Component({
  selector: 'list-group-etudiant',
  templateUrl: './list-group-etudiant.component.html',
  styleUrls: ['list-group-etudiant.scss']
})
export class ListGroupEtudiantComponent implements OnDestroy, OnChanges {
  listEtudiant: BehaviorSubject<IGroupbyetudiant[]> = new BehaviorSubject<IGroupbyetudiant[]>([]);

  unsubscribe = new Subject();
  @Input('groupid') groupid: number = 0;
  constructor(private noteService: NoteControleContinueService) {}

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.groupid);
    this.loadGroupEtudiant();
  }

  loadGroupEtudiant() {
    this.noteService
      .getEtudiantByGroup(this.groupid)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe(res => {
        if (res.body) this.listEtudiant.next(res.body);
        else this.listEtudiant.next([]);
      });
  }
  isshow(etudiant: IGroupbyetudiant) {
    if (etudiant.show == undefined || etudiant.show === null) etudiant.show = false;
    etudiant.show = !etudiant.show;
  }
  hide(etudiant: IGroupbyetudiant) {
    etudiant.show = false;
    /*this.listEtudiant.pipe(take(1))
  .subscribe((etudiants:IGroupbyetudiant[]) => {
    let groupetudiants:IGroupbyetudiant[]=[]

    etudiants.forEach(element=>{
if(element.matricule==etudiant.matricule){
etudiant.show=false

}

groupetudiants.push(element);
    })
    console.log(groupetudiants)
    this.listEtudiant.next(groupetudiants);
  });

 
    
    

 */
  }
  trackId(index: number, item: IGroupbyetudiant): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.matricule!;
  }
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
