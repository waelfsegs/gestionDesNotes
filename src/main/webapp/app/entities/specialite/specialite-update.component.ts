import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISpecialite, Specialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from './specialite.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle/cycle.service';

@Component({
  selector: 'jhi-specialite-update',
  templateUrl: './specialite-update.component.html'
})
export class SpecialiteUpdateComponent implements OnInit {
  isSaving = false;
  cycles: ICycle[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [],
    cycleId: []
  });

  constructor(
    protected specialiteService: SpecialiteService,
    protected cycleService: CycleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialite }) => {
      this.updateForm(specialite);

      this.cycleService.query().subscribe((res: HttpResponse<ICycle[]>) => (this.cycles = res.body || []));
    });
  }

  updateForm(specialite: ISpecialite): void {
    this.editForm.patchValue({
      id: specialite.id,
      libelle: specialite.libelle,
      cycleId: specialite.cycleId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specialite = this.createFromForm();
    if (specialite.id !== undefined) {
      this.subscribeToSaveResponse(this.specialiteService.update(specialite));
    } else {
      this.subscribeToSaveResponse(this.specialiteService.create(specialite));
    }
  }

  private createFromForm(): ISpecialite {
    return {
      ...new Specialite(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      cycleId: this.editForm.get(['cycleId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialite>>): void {
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
