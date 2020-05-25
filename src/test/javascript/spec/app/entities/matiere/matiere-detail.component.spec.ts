import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { MatiereDetailComponent } from 'app/entities/matiere/matiere-detail.component';
import { Matiere } from 'app/shared/model/matiere.model';

describe('Component Tests', () => {
  describe('Matiere Management Detail Component', () => {
    let comp: MatiereDetailComponent;
    let fixture: ComponentFixture<MatiereDetailComponent>;
    const route = ({ data: of({ matiere: new Matiere(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [MatiereDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MatiereDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MatiereDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load matiere on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.matiere).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
