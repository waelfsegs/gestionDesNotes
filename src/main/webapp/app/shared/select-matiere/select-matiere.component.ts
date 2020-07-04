import { Matiere } from 'app/shared/model/matiere.model';
import { Observable, of } from 'rxjs';

import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MatiereService } from './../../entities/matiere/matiere.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-matiere',
  templateUrl: './select-matiere.component.html'
})
export class SelectMatiereComponent implements OnInit {
  @Output('selectMatiere') selectMatier = new EventEmitter<number>();
  matiereid: number = 0;
  matieres$: Observable<Matiere[]> = of([]);
  constructor(private matiereservice: MatiereService) {}

  ngOnInit(): void {
    this.matieres$ = this.matiereservice.query({}).pipe(map(res => (res.body ? res.body : [])));
  }

  SelectMatiere() {
    this.selectMatier.emit(this.matiereid);
  }
}
