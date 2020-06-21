import { IMatierByEnseignent } from './../../../shared/model/matiere-by-enseignent';
import { Component, OnInit, OnDestroy, Output, EventEmitter, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';
import { NoteControleContinueService } from '../note-controle-continue.service';
import { takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'list-matiere-enseignent',
  templateUrl: './list-matiere-enseignent.component.html',
  styleUrls: ['list-matiere-enseignent.scss']
})
export class ListMatiereEnsiegnentComponent implements OnInit, OnDestroy, OnChanges {
  unsubscribe = new Subject();
  listMatiere: BehaviorSubject<IMatierByEnseignent[]> = new BehaviorSubject<IMatierByEnseignent[]>([]);
  @Input('enseignentid') idenseignent: number = 0;
  @Output('selectmatiere') selectmatiere: EventEmitter<number> = new EventEmitter();
  constructor(private noteService: NoteControleContinueService) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.loadMatiere();
  }

  ngOnInit() {}
  loadMatiere() {
    console.log('matier', this.idenseignent);
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
