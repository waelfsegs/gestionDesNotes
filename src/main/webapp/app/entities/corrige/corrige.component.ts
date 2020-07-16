import { map } from 'rxjs/operators';
import { EnveloppeService } from 'app/entities/enveloppe/enveloppe.service';
import { MatiereService } from './../matiere/matiere.service';
import { Account } from './../../core/user/account.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICorrige } from 'app/shared/model/corrige.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CorrigeService } from './corrige.service';
import { CorrigeDeleteDialogComponent } from './corrige-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-corrige',
  templateUrl: './corrige.component.html',
  styleUrls: ['/corrige.scss']
})
export class CorrigeComponent implements OnInit, OnDestroy {
  corriges?: ICorrige[];
  corrige: ICorrige = {};
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  currentaccount: Account | null = null;
  ensgId: any;
  envid: number = 0;
  constructor(
    protected corrigeService: CorrigeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService,
    private matiereService: MatiereService,
    private enveloppeService: EnveloppeService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;

      this.accountService.getAuthenticationState().subscribe(account => {
        this.currentaccount = account;
        if (this.currentaccount) {
          if (this.currentaccount.ensiegnent) {
            this.ensgId = Number(this.currentaccount.ensiegnent);
            this.loadPage(Number(this.currentaccount.ensiegnent));
          }
        }
      });
    });
    this.registerChangeInCorriges();
  }

  loadPage(idensieg: number, page?: number): void {
    const pageToLoad: number = page || this.page;

    this.corrigeService
      .query({
        'enseignantId.equals': idensieg,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICorrige[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }
  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICorrige): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCorriges(): void {
    this.eventSubscriber = this.eventManager.subscribe('corrigeListModification', () => this.loadPage(this.ensgId));
  }

  delete(corrige: ICorrige): void {
    const modalRef = this.modalService.open(CorrigeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.corrige = corrige;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICorrige[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/corrige'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.corriges = data || [];
    this.corriges.forEach(element => {
      console.log('hi');
      let matierid: number;
      if (element.enveloppeId) {
        console.log('enveloppeId', element.enveloppeId);
        this.enveloppeService
          .find(element.enveloppeId)
          .pipe(
            map(envlope => {
              console.log('envlope.body', envlope.body);
              if (envlope && envlope.body) {
                if (envlope.body.maiere) {
                  element.matiere = envlope.body.maiere;
                }
              }
            })
          )
          .subscribe();
      }
    });
  }
  details(enveloppeId: number, corrige: ICorrige) {
    this.envid = enveloppeId;
    this.corrige = corrige;
  }
  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
