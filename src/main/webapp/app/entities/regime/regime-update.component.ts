import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRegime, Regime } from 'app/shared/model/regime.model';
import { RegimeService } from './regime.service';

@Component({
  selector: 'jhi-regime-update',
  templateUrl: './regime-update.component.html',
})
export class RegimeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [],
  });

  constructor(protected regimeService: RegimeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regime }) => {
      this.updateForm(regime);
    });
  }

  updateForm(regime: IRegime): void {
    this.editForm.patchValue({
      id: regime.id,
      type: regime.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const regime = this.createFromForm();
    if (regime.id !== undefined) {
      this.subscribeToSaveResponse(this.regimeService.update(regime));
    } else {
      this.subscribeToSaveResponse(this.regimeService.create(regime));
    }
  }

  private createFromForm(): IRegime {
    return {
      ...new Regime(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegime>>): void {
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
}
