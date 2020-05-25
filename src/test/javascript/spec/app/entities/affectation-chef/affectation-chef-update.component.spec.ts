import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { AffectationChefUpdateComponent } from 'app/entities/affectation-chef/affectation-chef-update.component';
import { AffectationChefService } from 'app/entities/affectation-chef/affectation-chef.service';
import { AffectationChef } from 'app/shared/model/affectation-chef.model';

describe('Component Tests', () => {
  describe('AffectationChef Management Update Component', () => {
    let comp: AffectationChefUpdateComponent;
    let fixture: ComponentFixture<AffectationChefUpdateComponent>;
    let service: AffectationChefService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [AffectationChefUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AffectationChefUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AffectationChefUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AffectationChefService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AffectationChef(123);
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
        const entity = new AffectationChef();
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
