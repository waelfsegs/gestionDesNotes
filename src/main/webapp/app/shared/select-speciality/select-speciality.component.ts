import { INiveau } from './../model/niveau.model';
import { NiveauService } from './../../entities/niveau/niveau.service';
import { SpecialiteService } from './../../entities/specialite/specialite.service';
import { Specialite } from './../model/specialite.model';
import { Component, OnInit, Output, EventEmitter, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-speciality',
  templateUrl: './select-speciality.component.html'
})
export class selectSpecialityComponent implements OnInit, OnChanges {
  @Output('selectSpecialite') selectSpecialite = new EventEmitter<number>();
  @Input('cycleId') cycleId: number = 0;
  spcialiteid: number = 0;
  spcialite$: Observable<INiveau[]> = of([]);
  constructor(private spcialiteService: NiveauService) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (this.cycleId > 0) {
      this.spcialite$ = this.spcialiteService.query({ 'cycleId.equals': this.cycleId }).pipe(map(res => (res.body ? res.body : [])));
    } else {
      this.spcialite$ = of([]);
    }
  }

  ngOnInit(): void {}

  SelectSpecialite() {
    this.selectSpecialite.emit(this.spcialiteid);
  }
}
