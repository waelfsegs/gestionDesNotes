import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAffectationChef } from 'app/shared/model/affectation-chef.model';
import { AffectationChefService } from './affectation-chef.service';

@Component({
  templateUrl: './affectation-chef-delete-dialog.component.html',
})
export class AffectationChefDeleteDialogComponent {
  affectationChef?: IAffectationChef;

  constructor(
    protected affectationChefService: AffectationChefService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.affectationChefService.delete(id).subscribe(() => {
      this.eventManager.broadcast('affectationChefListModification');
      this.activeModal.close();
    });
  }
}
