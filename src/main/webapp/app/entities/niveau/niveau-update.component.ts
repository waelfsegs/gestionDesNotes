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

@Component({
  selector: 'jhi-niveau-update',
  templateUrl: './niveau-update.component.html',
})
export class NiveauUpdateComponent implements OnInit {
  isSaving = false;
  cycles: ICycle[] = [];

  editForm = this.fb.group({
    id: [],
    niveau: [],
    cycleId: [],
  });

  constructor(
    protected niveauService: NiveauService,
    protected cycleService: CycleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ niveau }) => {
      this.updateForm(niveau);

      this.cycleService.query().subscribe((res: HttpResponse<ICycle[]>) => (this.cycles = res.body || []));
    });
  }

  updateForm(niveau: INiveau): void {
    this.editForm.patchValue({
      id: niveau.id,
      niveau: niveau.niveau,
      cycleId: niveau.cycleId,
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

  trackById(index: number, item: ICycle): any {
    return item.id;
  }
}
