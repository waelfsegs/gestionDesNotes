import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'list-matiere-enseignent',
  templateUrl: './list-matiere-enseignent.component.html'
})
export class ListMatiereEnsiegnentComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  constructor() {}

  ngOnInit() {}
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
