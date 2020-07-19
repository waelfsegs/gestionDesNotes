import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICycle, Cycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';

@Component({
  selector: 'jhi-cycle-update',
  templateUrl: './cycle-update.component.html'
})
export class CycleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nomcycle: []
  });

  constructor(protected cycleService: CycleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cycle }) => {
      this.updateForm(cycle);
    });
  }

  updateForm(cycle: ICycle): void {
    this.editForm.patchValue({
      id: cycle.id,
      nomcycle: cycle.nomcycle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cycle = this.createFromForm();
    if (cycle.id !== undefined) {
      this.subscribeToSaveResponse(this.cycleService.update(cycle));
    } else {
      this.subscribeToSaveResponse(this.cycleService.create(cycle));
    }
  }

  private createFromForm(): ICycle {
    return {
      ...new Cycle(),
      id: this.editForm.get(['id'])!.value,
      nomcycle: this.editForm.get(['nomcycle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICycle>>): void {
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
