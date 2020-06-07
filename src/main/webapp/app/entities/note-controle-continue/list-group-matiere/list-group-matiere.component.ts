import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'list-group-matiere',
  templateUrl: './list-group-matiere.component.html'
})
export class ListGroupMatiereComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  constructor() {}

  ngOnInit() {}
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
