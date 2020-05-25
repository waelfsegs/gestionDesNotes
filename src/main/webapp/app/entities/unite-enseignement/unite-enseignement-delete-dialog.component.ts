import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUniteEnseignement } from 'app/shared/model/unite-enseignement.model';
import { UniteEnseignementService } from './unite-enseignement.service';

@Component({
  templateUrl: './unite-enseignement-delete-dialog.component.html',
})
export class UniteEnseignementDeleteDialogComponent {
  uniteEnseignement?: IUniteEnseignement;

  constructor(
    protected uniteEnseignementService: UniteEnseignementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uniteEnseignementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('uniteEnseignementListModification');
      this.activeModal.close();
    });
  }
}
