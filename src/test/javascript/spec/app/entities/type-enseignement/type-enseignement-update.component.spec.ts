import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { TypeEnseignementUpdateComponent } from 'app/entities/type-enseignement/type-enseignement-update.component';
import { TypeEnseignementService } from 'app/entities/type-enseignement/type-enseignement.service';
import { TypeEnseignement } from 'app/shared/model/type-enseignement.model';

describe('Component Tests', () => {
  describe('TypeEnseignement Management Update Component', () => {
    let comp: TypeEnseignementUpdateComponent;
    let fixture: ComponentFixture<TypeEnseignementUpdateComponent>;
    let service: TypeEnseignementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [TypeEnseignementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeEnseignementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeEnseignementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeEnseignementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeEnseignement(123);
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
        const entity = new TypeEnseignement();
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
