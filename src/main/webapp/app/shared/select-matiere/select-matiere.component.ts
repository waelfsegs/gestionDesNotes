import { ISpicialitematiere } from './../model/spicialitematiere.model';
import { SpicialitematiereService } from './../../entities/spicialitematiere/spicialitematiere.service';
import { Matiere } from 'app/shared/model/matiere.model';
import { Observable, of } from 'rxjs';

import { Component, OnInit, Output, EventEmitter, Input, OnChanges, SimpleChanges } from '@angular/core';
import { MatiereService } from './../../entities/matiere/matiere.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-matiere',
  templateUrl: './select-matiere.component.html'
})
export class SelectMatiereComponent implements OnInit, OnChanges {
  @Output('selectMatiere') selectMatier = new EventEmitter<number>();
  @Input('spcialiteid') spcialiteid: number = 0;
  matiereid: number = 0;
  matieres$: Observable<ISpicialitematiere[]> = of([]);
  constructor(private matiereservice: SpicialitematiereService) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (this.spcialiteid > 1) {
      this.matieres$ = this.matiereservice.query({ 'specialiteId.equals': this.spcialiteid }).pipe(map(res => (res.body ? res.body : [])));
    } else {
      this.matieres$ = of([]);
    }
  }

  ngOnInit(): void {}

  SelectMatiere() {
    this.selectMatier.emit(this.matiereid);
  }
}
