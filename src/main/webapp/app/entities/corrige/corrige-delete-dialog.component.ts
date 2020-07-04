import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICorrige } from 'app/shared/model/corrige.model';
import { CorrigeService } from './corrige.service';

@Component({
  templateUrl: './corrige-delete-dialog.component.html'
})
export class CorrigeDeleteDialogComponent {
  corrige?: ICorrige;

  constructor(protected corrigeService: CorrigeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.corrigeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('corrigeListModification');
      this.activeModal.close();
    });
  }
}
