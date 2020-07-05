import { SpecialiteService } from './../../entities/specialite/specialite.service';
import { Specialite } from './../model/specialite.model';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-speciality',
  templateUrl: './select-speciality.component.html'
})
export class selectSpecialityComponent implements OnInit {
  @Output('selectSpecialite') selectSpecialite = new EventEmitter<number>();
  spcialiteid: number = 0;
  spcialite$: Observable<Specialite[]> = of([]);
  constructor(private spcialiteService: SpecialiteService) {}

  ngOnInit(): void {
    this.spcialite$ = this.spcialiteService.query({}).pipe(map(res => (res.body ? res.body : [])));
  }

  SelectSpecialite() {
    this.selectSpecialite.emit(this.spcialiteid);
  }
}
