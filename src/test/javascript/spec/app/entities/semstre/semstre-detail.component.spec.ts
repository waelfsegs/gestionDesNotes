import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionNotesFsegsTestModule } from '../../../test.module';
import { SemstreDetailComponent } from 'app/entities/semstre/semstre-detail.component';
import { Semstre } from 'app/shared/model/semstre.model';

describe('Component Tests', () => {
  describe('Semstre Management Detail Component', () => {
    let comp: SemstreDetailComponent;
    let fixture: ComponentFixture<SemstreDetailComponent>;
    const route = ({ data: of({ semstre: new Semstre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionNotesFsegsTestModule],
        declarations: [SemstreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SemstreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SemstreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load semstre on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.semstre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
