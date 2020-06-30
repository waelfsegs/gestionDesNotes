import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { CorrigeDetailComponent } from 'app/entities/corrige/corrige-detail.component';
import { Corrige } from 'app/shared/model/corrige.model';

describe('Component Tests', () => {
  describe('Corrige Management Detail Component', () => {
    let comp: CorrigeDetailComponent;
    let fixture: ComponentFixture<CorrigeDetailComponent>;
    const route = ({ data: of({ corrige: new Corrige(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [CorrigeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CorrigeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CorrigeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load corrige on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.corrige).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
