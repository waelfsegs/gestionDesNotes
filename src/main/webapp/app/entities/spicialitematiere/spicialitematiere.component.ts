import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpicialitematiere } from 'app/shared/model/spicialitematiere.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SpicialitematiereService } from './spicialitematiere.service';
import { SpicialitematiereDeleteDialogComponent } from './spicialitematiere-delete-dialog.component';

@Component({
  selector: 'jhi-spicialitematiere',
  templateUrl: './spicialitematiere.component.html'
})
export class SpicialitematiereComponent implements OnInit, OnDestroy {
  spicialitematieres?: ISpicialitematiere[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected spicialitematiereService: SpicialitematiereService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.spicialitematiereService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISpicialitematiere[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSpicialitematieres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISpicialitematiere): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSpicialitematieres(): void {
    this.eventSubscriber = this.eventManager.subscribe('spicialitematiereListModification', () => this.loadPage());
  }

  delete(spicialitematiere: ISpicialitematiere): void {
    const modalRef = this.modalService.open(SpicialitematiereDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.spicialitematiere = spicialitematiere;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISpicialitematiere[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/spicialitematiere'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.spicialitematieres = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
