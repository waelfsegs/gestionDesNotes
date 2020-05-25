import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { EnseignementUpdateComponent } from 'app/entities/enseignement/enseignement-update.component';
import { EnseignementService } from 'app/entities/enseignement/enseignement.service';
import { Enseignement } from 'app/shared/model/enseignement.model';

describe('Component Tests', () => {
  describe('Enseignement Management Update Component', () => {
    let comp: EnseignementUpdateComponent;
    let fixture: ComponentFixture<EnseignementUpdateComponent>;
    let service: EnseignementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [EnseignementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EnseignementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnseignementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnseignementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Enseignement(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Enseignement();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
