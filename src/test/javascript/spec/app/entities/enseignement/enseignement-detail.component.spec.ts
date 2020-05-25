import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { EnseignementDetailComponent } from 'app/entities/enseignement/enseignement-detail.component';
import { Enseignement } from 'app/shared/model/enseignement.model';

describe('Component Tests', () => {
  describe('Enseignement Management Detail Component', () => {
    let comp: EnseignementDetailComponent;
    let fixture: ComponentFixture<EnseignementDetailComponent>;
    const route = ({ data: of({ enseignement: new Enseignement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [EnseignementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EnseignementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnseignementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load enseignement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enseignement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
