import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAffectationChef } from 'app/shared/model/affectation-chef.model';

@Component({
  selector: 'jhi-affectation-chef-detail',
  templateUrl: './affectation-chef-detail.component.html',
})
export class AffectationChefDetailComponent implements OnInit {
  affectationChef: IAffectationChef | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affectationChef }) => (this.affectationChef = affectationChef));
  }

  previousState(): void {
    window.history.back();
  }
}
