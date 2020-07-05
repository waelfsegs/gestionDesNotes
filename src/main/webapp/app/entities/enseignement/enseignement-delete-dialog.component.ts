import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnseignement } from 'app/shared/model/enseignement.model';
import { EnseignementService } from './enseignement.service';

@Component({
  templateUrl: './enseignement-delete-dialog.component.html'
})
export class EnseignementDeleteDialogComponent {
  enseignement?: IEnseignement;

  constructor(
    protected enseignementService: EnseignementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enseignementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enseignementListModification');
      this.activeModal.close();
    });
  }
}
