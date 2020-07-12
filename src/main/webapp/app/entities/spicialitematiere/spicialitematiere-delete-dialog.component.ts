import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpicialitematiere } from 'app/shared/model/spicialitematiere.model';
import { SpicialitematiereService } from './spicialitematiere.service';

@Component({
  templateUrl: './spicialitematiere-delete-dialog.component.html'
})
export class SpicialitematiereDeleteDialogComponent {
  spicialitematiere?: ISpicialitematiere;

  constructor(
    protected spicialitematiereService: SpicialitematiereService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.spicialitematiereService.delete(id).subscribe(() => {
      this.eventManager.broadcast('spicialitematiereListModification');
      this.activeModal.close();
    });
  }
}
