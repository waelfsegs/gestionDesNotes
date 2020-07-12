import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INiveau, Niveau } from 'app/shared/model/niveau.model';
import { NiveauService } from './niveau.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle/cycle.service';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';

type SelectableEntity = ICycle | ISpecialite;

@Component({
  selector: 'jhi-niveau-update',
  templateUrl: './niveau-update.component.html'
})
export class NiveauUpdateComponent implements OnInit {
  isSaving = false;
  cycles: ICycle[] = [];
  specialites: ISpecialite[] = [];

  editForm = this.fb.group({
    id: [],
    niveau: [],
    cycleId: [],
    specialiteId: []
  });

  constructor(
    protected niveauService: NiveauService,
    protected cycleService: CycleService,
    protected specialiteService: SpecialiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ niveau }) => {
      this.updateForm(niveau);

      this.cycleService.query().subscribe((res: HttpResponse<ICycle[]>) => (this.cycles = res.body || []));

      this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));
    });
  }

  updateForm(niveau: INiveau): void {
    this.editForm.patchValue({
      id: niveau.id,
      niveau: niveau.niveau,
      cycleId: niveau.cycleId,
      specialiteId: niveau.specialiteId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const niveau = this.createFromForm();
    if (niveau.id !== undefined) {
      this.subscribeToSaveResponse(this.niveauService.update(niveau));
    } else {
      this.subscribeToSaveResponse(this.niveauService.create(niveau));
    }
  }

  private createFromForm(): INiveau {
    return {
      ...new Niveau(),
      id: this.editForm.get(['id'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      cycleId: this.editForm.get(['cycleId'])!.value,
      specialiteId: this.editForm.get(['specialiteId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INiveau>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
