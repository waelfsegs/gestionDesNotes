import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUniteEnseignement } from 'app/shared/model/unite-enseignement.model';

@Component({
  selector: 'jhi-unite-enseignement-detail',
  templateUrl: './unite-enseignement-detail.component.html'
})
export class UniteEnseignementDetailComponent implements OnInit {
  uniteEnseignement: IUniteEnseignement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uniteEnseignement }) => (this.uniteEnseignement = uniteEnseignement));
  }

  previousState(): void {
    window.history.back();
  }
}
