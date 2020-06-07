import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'note-controle-continue',
  templateUrl: './note-controle-continue.component.html',
  styleUrls: ['note-controle-continue.scss']
})
export class NoteControleContinueComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  constructor() {}

  ngOnInit() {}
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
