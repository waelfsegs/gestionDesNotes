import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { AffectationChefDetailComponent } from 'app/entities/affectation-chef/affectation-chef-detail.component';
import { AffectationChef } from 'app/shared/model/affectation-chef.model';

describe('Component Tests', () => {
  describe('AffectationChef Management Detail Component', () => {
    let comp: AffectationChefDetailComponent;
    let fixture: ComponentFixture<AffectationChefDetailComponent>;
    const route = ({ data: of({ affectationChef: new AffectationChef(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [AffectationChefDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AffectationChefDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AffectationChefDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load affectationChef on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.affectationChef).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
