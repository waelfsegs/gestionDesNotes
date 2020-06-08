import { takeUntil } from 'rxjs/operators';
import { IGroupEnseigner } from 'app/shared/model/groupe-enseigner';
import { Component, SimpleChanges, OnDestroy, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';
import { NoteControleContinueService } from '../note-controle-continue.service';

@Component({
  selector: 'list-group-matiere',
  templateUrl: './list-group-matiere.component.html',
  styleUrls: ['list-group-matiere.scss']
})
export class ListGroupMatiereComponent implements OnDestroy, OnChanges {
  unsubscribe = new Subject();
  listGroup: BehaviorSubject<IGroupEnseigner[]> = new BehaviorSubject<IGroupEnseigner[]>([]);
  @Input('matiereid') matiereid: number = 0;
  @Output('selectgroupe') selectgroupe: EventEmitter<number> = new EventEmitter();
  constructor(private noteService: NoteControleContinueService) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.loadGroup();
  }

  loadGroup() {
    this.noteService
      .getGroupEnseigner(1, this.matiereid)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe(res => {
        if (res.body) {
          this.listGroup.next(res.body);
        } else {
          this.listGroup.next([]);
        }
      });
  }

  selectGroup(groupe: IGroupEnseigner) {
    this.selectgroupe.emit(groupe.id);
  }

  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
