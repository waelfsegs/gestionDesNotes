import { Niveau } from './../model/niveau.model';
import { NiveauService } from './../../entities/niveau/niveau.service';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-niveau',
  templateUrl: './select-niveau.component.html'
})
export class selectNiveauComponent implements OnInit {
  @Output('selectNiveau') selectniveau = new EventEmitter<number>();
  niveauid: number = 0;
  niveau$: Observable<Niveau[]> = of([]);
  constructor(private niveauService: NiveauService) {}

  ngOnInit(): void {
    this.niveau$ = this.niveauService.query({}).pipe(map(res => (res.body ? res.body : [])));
  }

  SelectNiveau() {
    this.selectniveau.emit(this.niveauid);
  }
}
