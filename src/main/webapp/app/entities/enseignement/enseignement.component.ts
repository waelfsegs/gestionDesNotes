import { TypeEnseignement } from './../../shared/model/type-enseignement.model';
import { TypeEnseignementService } from './../type-enseignement/type-enseignement.service';
import { EnseignantService } from './../enseignant/enseignant.service';
import { GroupeService } from './../groupe/groupe.service';
import { MatiereService } from './../matiere/matiere.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnseignement } from 'app/shared/model/enseignement.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EnseignementService } from './enseignement.service';
import { EnseignementDeleteDialogComponent } from './enseignement-delete-dialog.component';

@Component({
  selector: 'jhi-enseignement',
  templateUrl: './enseignement.component.html'
})
export class EnseignementComponent implements OnInit, OnDestroy {
  enseignements?: IEnseignement[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected enseignementService: EnseignementService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private matiereService: MatiereService,
    private groupService: GroupeService,
    private enseignentService: EnseignantService,
    private typeEnsiengnantService: TypeEnseignementService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.enseignementService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEnseignement[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEnseignements();
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
  }

  trackId(index: number, item: IEnseignement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEnseignements(): void {
    this.eventSubscriber = this.eventManager.subscribe('enseignementListModification', () => this.loadPage());
  }

  delete(enseignement: IEnseignement): void {
    const modalRef = this.modalService.open(EnseignementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.enseignement = enseignement;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEnseignement[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/enseignement'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.enseignements = data || [];
    this.enseignements.forEach(element => {
      this.getmatiereNomById(element);
      this.getgroupService(element);
      this.getenseignentService(element);
      this.gettypeEnsiengnantService(element);
    });
  }

  getmatiereNomById(element: IEnseignement) {
    if (element.matiereId) {
      this.matiereService.find(element.matiereId).subscribe(res => {
        if (res.body) {
          element.matierenom = res.body.nom;
        }
      });
    }
  }

  getgroupService(element: IEnseignement) {
    if (element.groupeId) {
      this.groupService.find(element.groupeId).subscribe(res => {
        if (res.body) {
          element.groupenom = res.body.nomgroup;
        }
      });
    }
  }

  getenseignentService(element: IEnseignement) {
    if (element.enseignantId) {
      this.enseignentService.find(element.enseignantId).subscribe(res => {
        if (res.body) {
          element.enseignantnom = res.body.nom + ' ' + res.body.pernom;
        }
      });
    }
  }

  gettypeEnsiengnantService(element: IEnseignement) {
    if (element.typeEnseignementId) {
      this.typeEnsiengnantService.find(element.typeEnseignementId).subscribe(res => {
        if (res.body) {
          element.typeEnseignement = res.body.type;
        }
      });
    }
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
