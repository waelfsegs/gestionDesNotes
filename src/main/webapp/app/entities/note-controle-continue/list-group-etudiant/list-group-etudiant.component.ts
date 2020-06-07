import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'list-group-etudiant',
  templateUrl: './list-group-etudiant.component.html'
})
export class ListGroupEtudiantComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  constructor() {}

  ngOnInit() {}
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
