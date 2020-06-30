import { IExamen } from './../../shared/model/examen.model';

import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

import { TblExamenService } from '../tbl-examen-affecter/tblexamen.service';

@Component({
  selector: 'jhi-corrige-detail',
  templateUrl: './corrige-detail.component.html'
})
export class CorrigeDetailComponent implements OnChanges {
  @Input('idEnv') idEnv: number = 0;
  examens: IExamen[] = [];
  constructor(protected examenService: TblExamenService) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (this.idEnv > 0) {
      this.examenService.query({ 'enveloppeId.equals': this.idEnv }).subscribe(res => {
        if (res.body) this.examens = res.body;
      });
    }
  }
  loadExamen() {}
  modifier(examen: IExamen) {
    examen.done = true;
  }
  edit(examen: IExamen) {
    examen.done = false;
  }
}
