import { IMatiere } from './../../shared/model/matiere.model';
import { IEnseignement } from './../../shared/model/enseignement.model';
import { IEnseignant } from './../../shared/model/enseignant.model';
import { EnseignementService } from './../enseignement/enseignement.service';
import { map, concatMap, flatMap, distinct, takeUntil, switchMap } from 'rxjs/operators';
import { Account } from './../../core/user/account.model';
import { MatiereService } from './../matiere/matiere.service';
import { ClasseService } from './../classe/classe.service';
import { InscriptionService } from './../inscription/inscription.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultat } from 'app/shared/model/resultat.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ResultatService } from './resultat.service';
import { ResultatDeleteDialogComponent } from './resultat-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { of } from 'rxjs';
import { Inote } from 'app/shared/model/note';
import { not } from '@angular/compiler/src/output/output_ast';
@Component({
  selector: 'jhi-resultat',
  templateUrl: './resultat.component.html'
})
export class ResultatComponent implements OnInit, OnDestroy {
  resultats?: IResultat[];
  notes: Inote[] = [];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  account: Account | null = null;
  unsibscribe = new Subject();
  constructor(
    protected resultatService: ResultatService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private inscriptionService: InscriptionService,
    private calssService: ClasseService,
    private matiereService: MatiereService,
    private accountService: AccountService,
    private enseignementService: EnseignementService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.unsibscribe))
      .subscribe(account => {
        this.account = account;
        if (this.account) {
          this.inscriptionService
            .query({ 'etudiantId.equals': this.account.etudiant })
            .pipe(
              map(res => (res.body ? res.body[0] : null)),
              takeUntil(this.unsibscribe)
            )
            .subscribe(inscription => {
              if (inscription) {
                let note: Inote = {};
                let matiere: IMatiere = {};
                let i = 0;
                this.notes = [];
                this.enseignementService
                  .query({ 'classeId.equals': inscription.classeId })
                  .pipe(
                    map(res => res.body),
                    flatMap(res => (res ? res : of(null))),
                    distinct(res => (res ? res.matiereId : null)),
                    concatMap(ensigment => {
                      if (ensigment && ensigment.matiereId) {
                        return this.matiereService.find(ensigment.matiereId);
                      }
                      return of(null);
                    }),
                    concatMap(matier => {
                      if (matier && matier.body) {
                        matiere = {};
                        matiere = matier.body;
                        note = {};
                        note.coefficientMatiere = matiere.coefficientMatiere;
                        note.nom = matiere.nom;
                        note.regime = matiere.regime;
                        return this.resultatService.query({ 'inscriptionId.equals': inscription.id, 'matiereId.equals': matiere.id }).pipe(
                          map(res => res.body),
                          takeUntil(this.unsibscribe)
                        );
                      }
                      return of(null);
                    }),
                    takeUntil(this.unsibscribe)
                  )
                  .subscribe(resultat => {
                    if (resultat) {
                      if (resultat[0]) {
                        note.notecc1 = resultat[0].notecc1;
                        note.notecc2 = resultat[0].notecc2;
                        note.examne = resultat[0].noteexmen;
                      }
                    }
                    this.notes.push(note);
                  });
              }
            });
        }
      });
  }

  ngOnInit(): void {
    this.loadPage();

    this.handleBackNavigation();
    this.registerChangeInResultats();
  }

  handleBackNavigation(): void {
    this.activatedRoute.queryParamMap.subscribe((params: ParamMap) => {
      /* const prevPage = params.get('page');
      const prevSort = params.get('sort');
      const prevSortSplit = prevSort?.split(',');
      if (prevSortSplit) {
        this.predicate = prevSortSplit[0];
        this.ascending = prevSortSplit[1] === 'asc';
      }
      if (prevPage && +prevPage !== this.page) {
        this.ngbPaginationPage = +prevPage;
        this.loadPage(+prevPage);
      } else {
        this.loadPage(this.page);
      }*/
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
    this.unsibscribe.next();
    this.unsibscribe.complete();
  }

  trackId(index: number, item: IResultat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInResultats(): void {
    this.eventSubscriber = this.eventManager.subscribe('resultatListModification', () => this.loadPage());
  }

  delete(resultat: IResultat): void {
    const modalRef = this.modalService.open(ResultatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resultat = resultat;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IResultat[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/resultat'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.resultats = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
