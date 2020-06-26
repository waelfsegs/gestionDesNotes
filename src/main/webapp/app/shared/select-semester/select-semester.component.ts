import { SemstreService } from './../../entities/semstre/semstre.service';
import { Semstre } from './../model/semstre.model';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-semester',
  templateUrl: './select-semester.component.html'
})
export class selectSemesterComponent implements OnInit {
  @Output('selectSemestre') selectSemestre = new EventEmitter<number>();
  semestreid: number = 0;
  semestre$: Observable<Semstre[]> = of([]);
  constructor(private semstreService: SemstreService) {}

  ngOnInit(): void {
    this.semestre$ = this.semstreService.query({}).pipe(map(res => (res.body ? res.body : [])));
  }

  SelectSemestre() {
    this.selectSemestre.emit(this.semestreid);
  }
}
