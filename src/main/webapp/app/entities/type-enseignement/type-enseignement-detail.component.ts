import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeEnseignement } from 'app/shared/model/type-enseignement.model';

@Component({
  selector: 'jhi-type-enseignement-detail',
  templateUrl: './type-enseignement-detail.component.html',
})
export class TypeEnseignementDetailComponent implements OnInit {
  typeEnseignement: ITypeEnseignement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeEnseignement }) => (this.typeEnseignement = typeEnseignement));
  }

  previousState(): void {
    window.history.back();
  }
}
