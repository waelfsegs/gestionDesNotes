import { Component, OnInit, OnDestroy, Output, EventEmitter, Input } from '@angular/core';
import { Subject } from 'rxjs';
import { IGroupbyetudiant } from 'app/shared/model/group-by-etudiant';

@Component({
  selector: 'edit-note',
  templateUrl: './edit-note.component.html'
})
export class EditNoteComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  @Input('etudiant') etudiant: IGroupbyetudiant = {};
  @Output('hide') hide: EventEmitter<IGroupbyetudiant> = new EventEmitter();
  constructor() {}

  ngOnInit() {}
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
  Hide() {
    console.log('hello', this.etudiant);
    this.hide.emit(this.etudiant);
  }
}
