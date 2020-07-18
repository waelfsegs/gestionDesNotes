import { Inscription } from './../../../../shared/model/inscription.model';
import { InscriptionService } from './../../../inscription/inscription.service';
import { IClasse } from './../../../../shared/model/classe.model';
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'inscription-class',
  templateUrl: './inscription-class.component.html'
})
export class InscriptionClasseComponent implements OnChanges {
  @Input('calsseid') calsseid: number = 0;
  inscription$: Observable<Inscription[] | null> = of([]);
  constructor(protected activatedRoute: ActivatedRoute, private inscriptionService: InscriptionService) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (this.calsseid > 0)
      this.inscription$ = this.inscriptionService.query({ 'classeId.equals': this.calsseid }).pipe(map(res => res.body));
  }
}
