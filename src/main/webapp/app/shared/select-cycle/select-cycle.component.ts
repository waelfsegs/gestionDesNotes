import { Cycle } from './../model/cycle.model';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { CycleService } from 'app/entities/cycle/cycle.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'select-cycle',
  templateUrl: './select-cycle.component.html'
})
export class selectCycleComponent implements OnInit {
  @Output('selectCycle') selectCycle = new EventEmitter<number>();
  cycleid: number = 0;
  cycle$: Observable<Cycle[]> = of([]);
  constructor(private cycleservice: CycleService) {}

  ngOnInit(): void {
    this.cycle$ = this.cycleservice.query({}).pipe(map(res => (res.body ? res.body : [])));
  }

  SelectCycle() {
    this.selectCycle.emit(this.cycleid);
  }
}
