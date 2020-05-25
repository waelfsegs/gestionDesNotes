import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISemstre } from 'app/shared/model/semstre.model';

@Component({
  selector: 'jhi-semstre-detail',
  templateUrl: './semstre-detail.component.html',
})
export class SemstreDetailComponent implements OnInit {
  semstre: ISemstre | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ semstre }) => (this.semstre = semstre));
  }

  previousState(): void {
    window.history.back();
  }
}
