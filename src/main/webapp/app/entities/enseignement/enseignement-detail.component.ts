import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnseignement } from 'app/shared/model/enseignement.model';

@Component({
  selector: 'jhi-enseignement-detail',
  templateUrl: './enseignement-detail.component.html'
})
export class EnseignementDetailComponent implements OnInit {
  enseignement: IEnseignement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enseignement }) => (this.enseignement = enseignement));
  }

  previousState(): void {
    window.history.back();
  }
}
