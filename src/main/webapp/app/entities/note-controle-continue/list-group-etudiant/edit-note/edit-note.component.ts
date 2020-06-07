import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'edit-note',
  templateUrl: './edit-note.component.html'
})
export class EditNoteComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  constructor() {}

  ngOnInit() {}
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
