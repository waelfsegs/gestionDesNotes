import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnveloppe } from 'app/shared/model/enveloppe.model';

@Component({
  selector: 'jhi-enveloppe-detail',
  templateUrl: './enveloppe-detail.component.html'
})
export class EnveloppeDetailComponent implements OnInit {
  enveloppe: IEnveloppe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enveloppe }) => (this.enveloppe = enveloppe));
  }

  previousState(): void {
    window.history.back();
  }
}
