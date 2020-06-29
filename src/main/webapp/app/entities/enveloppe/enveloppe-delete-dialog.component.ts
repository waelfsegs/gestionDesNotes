import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnveloppe } from 'app/shared/model/enveloppe.model';
import { EnveloppeService } from './enveloppe.service';

@Component({
  templateUrl: './enveloppe-delete-dialog.component.html'
})
export class EnveloppeDeleteDialogComponent {
  enveloppe?: IEnveloppe;

  constructor(protected enveloppeService: EnveloppeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enveloppeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enveloppeListModification');
      this.activeModal.close();
    });
  }
}
