import { IMatierByEnseignent } from './../../../shared/model/matiere-by-enseignent';
import { Component, OnInit, OnDestroy, Output, EventEmitter, Input } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';
import { NoteControleContinueService } from '../note-controle-continue.service';
import { takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'list-matiere-enseignent',
  templateUrl: './list-matiere-enseignent.component.html',
  styleUrls: ['list-matiere-enseignent.scss']
})
export class ListMatiereEnsiegnentComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  listMatiere: BehaviorSubject<IMatierByEnseignent[]> = new BehaviorSubject<IMatierByEnseignent[]>([]);
  @Input('enseignentid') idenseignent: number = 0;
  @Output('selectmatiere') selectmatiere: EventEmitter<number> = new EventEmitter();
  constructor(private noteService: NoteControleContinueService) {}

  ngOnInit() {
    this.loadMatiere();
  }
  loadMatiere() {
    this.noteService
      .getmatierebyenseignement(this.idenseignent)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((res: HttpResponse<IMatierByEnseignent[]>) => {
        if (res.body) {
          this.listMatiere.next(res.body);
        } else this.listMatiere.next([]);
      });
  }
  selectMatiere(matiere: IMatierByEnseignent) {
    this.selectmatiere.emit(matiere.id);
  }
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
