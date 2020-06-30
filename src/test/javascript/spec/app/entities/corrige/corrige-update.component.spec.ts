import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { CorrigeUpdateComponent } from 'app/entities/corrige/corrige-update.component';
import { CorrigeService } from 'app/entities/corrige/corrige.service';
import { Corrige } from 'app/shared/model/corrige.model';

describe('Component Tests', () => {
  describe('Corrige Management Update Component', () => {
    let comp: CorrigeUpdateComponent;
    let fixture: ComponentFixture<CorrigeUpdateComponent>;
    let service: CorrigeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [CorrigeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CorrigeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CorrigeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CorrigeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Corrige(123);
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
        const entity = new Corrige();
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
