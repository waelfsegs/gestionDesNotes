import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { UniteEnseignementDeleteDialogComponent } from 'app/entities/unite-enseignement/unite-enseignement-delete-dialog.component';
import { UniteEnseignementService } from 'app/entities/unite-enseignement/unite-enseignement.service';

describe('Component Tests', () => {
  describe('UniteEnseignement Management Delete Component', () => {
    let comp: UniteEnseignementDeleteDialogComponent;
    let fixture: ComponentFixture<UniteEnseignementDeleteDialogComponent>;
    let service: UniteEnseignementService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [UniteEnseignementDeleteDialogComponent],
      })
        .overrideTemplate(UniteEnseignementDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UniteEnseignementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UniteEnseignementService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
