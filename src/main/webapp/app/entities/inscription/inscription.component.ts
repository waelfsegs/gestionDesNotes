import { SemstreService } from './../semstre/semstre.service';
import { GroupeService } from './../groupe/groupe.service';
import { ClasseService } from './../classe/classe.service';
import { EtudiantService } from './../etudiant/etudiant.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInscription } from 'app/shared/model/inscription.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InscriptionService } from './inscription.service';
import { InscriptionDeleteDialogComponent } from './inscription-delete-dialog.component';

@Component({
  selector: 'jhi-inscription',
  templateUrl: './inscription.component.html'
})
export class InscriptionComponent implements OnInit, OnDestroy {
  inscriptions?: IInscription[] = [];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected inscriptionService: InscriptionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected etudiantService: EtudiantService,
    protected classService: ClasseService,
    protected groupeService: GroupeService,
    protected semestreService: SemstreService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.inscriptionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IInscription[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.handleBackNavigation();
    this.registerChangeInInscriptions();
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

  trackId(index: number, item: IInscription): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInscriptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('inscriptionListModification', () => this.loadPage());
  }

  delete(inscription: IInscription): void {
    const modalRef = this.modalService.open(InscriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inscription = inscription;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IInscription[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/inscription'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.inscriptions = [];
    if (data) {
      data.forEach(element => {
        this.getNameEtudiantById(element);
        this.getGroupNameById(element);
        this.getSemestertById(element);
        this.getNameclasstById(element);
        if (this.inscriptions) this.inscriptions.push(element);
      });
    }
  }
  getNameEtudiantById(element: IInscription) {
    if (element.etudiantId) {
      this.etudiantService.find(element.etudiantId).subscribe(res => {
        if (res.body) element.nomEtudiant = res.body.nom + ' ' + res.body.prenom;
      });
    }
  }
  getSemestertById(element: IInscription) {
    if (element.semstreId) {
      this.semestreService.find(element.semstreId).subscribe(res => {
        if (res.body) element.semstre = res.body.numSemstre;
      });
    }
  }
  getGroupNameById(element: IInscription) {
    if (element.groupeId) {
      this.groupeService.find(element.groupeId).subscribe(res => {
        if (res.body) element.group = res.body.nomgroup;
      });
    }
  }
  getNameclasstById(element: IInscription) {
    if (element.classeId) {
      this.classService.find(element.classeId).subscribe(res => {
        if (res.body) element.classe = res.body.nom;
      });
    }
  }
  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }
}
