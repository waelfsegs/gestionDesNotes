import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpicialitematiere } from 'app/shared/model/spicialitematiere.model';

@Component({
  selector: 'jhi-spicialitematiere-detail',
  templateUrl: './spicialitematiere-detail.component.html'
})
export class SpicialitematiereDetailComponent implements OnInit {
  spicialitematiere: ISpicialitematiere | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spicialitematiere }) => (this.spicialitematiere = spicialitematiere));
  }

  previousState(): void {
    window.history.back();
  }
}
