import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { UniteEnseignementUpdateComponent } from 'app/entities/unite-enseignement/unite-enseignement-update.component';
import { UniteEnseignementService } from 'app/entities/unite-enseignement/unite-enseignement.service';
import { UniteEnseignement } from 'app/shared/model/unite-enseignement.model';

describe('Component Tests', () => {
  describe('UniteEnseignement Management Update Component', () => {
    let comp: UniteEnseignementUpdateComponent;
    let fixture: ComponentFixture<UniteEnseignementUpdateComponent>;
    let service: UniteEnseignementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [UniteEnseignementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UniteEnseignementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UniteEnseignementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UniteEnseignementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UniteEnseignement(123);
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
        const entity = new UniteEnseignement();
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
