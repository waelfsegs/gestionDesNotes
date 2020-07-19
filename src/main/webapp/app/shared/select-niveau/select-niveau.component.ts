import { Niveau } from './../model/niveau.model';
import { NiveauService } from './../../entities/niveau/niveau.service';
import { Component, OnInit, EventEmitter, Output, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-niveau',
  templateUrl: './select-niveau.component.html'
})
export class selectNiveauComponent implements OnInit, OnChanges {
  @Output('selectNiveau') selectniveau = new EventEmitter<number>();
  @Input('cycleId') cycleid: number = 0;
  niveauid: number = 0;
  niveau$: Observable<Niveau[]> = of([]);
  constructor(private niveauService: NiveauService) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (this.cycleid > 0) {
      this.niveau$ = this.niveauService.query({ 'cycleId.equals': this.cycleid }).pipe(map(res => (res.body ? res.body : [])));
    } else {
      this.niveau$ = of([]);
    }
  }

  ngOnInit(): void {}

  SelectNiveau() {
    this.selectniveau.emit(this.niveauid);
  }
}
