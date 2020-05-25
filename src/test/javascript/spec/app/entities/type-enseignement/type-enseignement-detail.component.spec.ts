import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { TypeEnseignementDetailComponent } from 'app/entities/type-enseignement/type-enseignement-detail.component';
import { TypeEnseignement } from 'app/shared/model/type-enseignement.model';

describe('Component Tests', () => {
  describe('TypeEnseignement Management Detail Component', () => {
    let comp: TypeEnseignementDetailComponent;
    let fixture: ComponentFixture<TypeEnseignementDetailComponent>;
    const route = ({ data: of({ typeEnseignement: new TypeEnseignement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [TypeEnseignementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeEnseignementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeEnseignementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeEnseignement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeEnseignement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
