import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISemstre } from 'app/shared/model/semstre.model';
import { SemstreService } from './semstre.service';

@Component({
  templateUrl: './semstre-delete-dialog.component.html',
})
export class SemstreDeleteDialogComponent {
  semstre?: ISemstre;

  constructor(protected semstreService: SemstreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.semstreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('semstreListModification');
      this.activeModal.close();
    });
  }
}
