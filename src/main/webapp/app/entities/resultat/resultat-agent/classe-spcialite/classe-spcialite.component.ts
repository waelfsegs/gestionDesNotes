import { ClasseService } from './../../../classe/classe.service';
import { IClasse } from './../../../../shared/model/classe.model';
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
@Component({
  selector: 'classe-spcialite',
  templateUrl: './classe-spcialite.component.html'
})
export class ClasseSpcialiteComponent implements OnChanges {
  @Input('spcialiteid') spcialiteid: number = 0;
  class$: Observable<IClasse[] | null> = of([]);
  constructor(protected activatedRoute: ActivatedRoute, private classeservice: ClasseService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spcialiteid > 0)
      this.class$ = this.classeservice.query({ 'specialiteId.equals': this.spcialiteid }).pipe(map(res => res.body));
  }
}
