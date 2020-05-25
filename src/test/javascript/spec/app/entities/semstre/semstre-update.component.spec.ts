import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { SemstreUpdateComponent } from 'app/entities/semstre/semstre-update.component';
import { SemstreService } from 'app/entities/semstre/semstre.service';
import { Semstre } from 'app/shared/model/semstre.model';

describe('Component Tests', () => {
  describe('Semstre Management Update Component', () => {
    let comp: SemstreUpdateComponent;
    let fixture: ComponentFixture<SemstreUpdateComponent>;
    let service: SemstreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [SemstreUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SemstreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SemstreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SemstreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Semstre(123);
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
        const entity = new Semstre();
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
