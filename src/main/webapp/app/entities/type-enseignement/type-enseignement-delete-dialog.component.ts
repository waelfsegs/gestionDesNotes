import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeEnseignement } from 'app/shared/model/type-enseignement.model';
import { TypeEnseignementService } from './type-enseignement.service';

@Component({
  templateUrl: './type-enseignement-delete-dialog.component.html'
})
export class TypeEnseignementDeleteDialogComponent {
  typeEnseignement?: ITypeEnseignement;

  constructor(
    protected typeEnseignementService: TypeEnseignementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeEnseignementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeEnseignementListModification');
      this.activeModal.close();
    });
  }
}
