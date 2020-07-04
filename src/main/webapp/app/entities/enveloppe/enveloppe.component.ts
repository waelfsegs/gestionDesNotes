import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnveloppe } from 'app/shared/model/enveloppe.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EnveloppeService } from './enveloppe.service';
import { EnveloppeDeleteDialogComponent } from './enveloppe-delete-dialog.component';

@Component({
  selector: 'jhi-enveloppe',
  templateUrl: './enveloppe.component.html'
})
export class EnveloppeComponent implements OnInit, OnDestroy {
  enveloppes?: IEnveloppe[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected enveloppeService: EnveloppeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.enveloppeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEnveloppe[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEnveloppes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEnveloppe): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEnveloppes(): void {
    this.eventSubscriber = this.eventManager.subscribe('enveloppeListModification', () => this.loadPage());
  }

  delete(enveloppe: IEnveloppe): void {
    const modalRef = this.modalService.open(EnveloppeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.enveloppe = enveloppe;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEnveloppe[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/enveloppe'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.enveloppes = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
