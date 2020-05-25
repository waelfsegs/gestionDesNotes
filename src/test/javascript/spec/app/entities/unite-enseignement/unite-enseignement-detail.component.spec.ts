import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { UniteEnseignementDetailComponent } from 'app/entities/unite-enseignement/unite-enseignement-detail.component';
import { UniteEnseignement } from 'app/shared/model/unite-enseignement.model';

describe('Component Tests', () => {
  describe('UniteEnseignement Management Detail Component', () => {
    let comp: UniteEnseignementDetailComponent;
    let fixture: ComponentFixture<UniteEnseignementDetailComponent>;
    const route = ({ data: of({ uniteEnseignement: new UniteEnseignement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [UniteEnseignementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UniteEnseignementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UniteEnseignementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load uniteEnseignement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uniteEnseignement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
