import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUniteEnseignement, UniteEnseignement } from 'app/shared/model/unite-enseignement.model';
import { UniteEnseignementService } from './unite-enseignement.service';

@Component({
  selector: 'jhi-unite-enseignement-update',
  templateUrl: './unite-enseignement-update.component.html',
})
export class UniteEnseignementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nomUE: [],
    desgnationUE: [],
    coefficientUE: [],
  });

  constructor(
    protected uniteEnseignementService: UniteEnseignementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uniteEnseignement }) => {
      this.updateForm(uniteEnseignement);
    });
  }

  updateForm(uniteEnseignement: IUniteEnseignement): void {
    this.editForm.patchValue({
      id: uniteEnseignement.id,
      nomUE: uniteEnseignement.nomUE,
      desgnationUE: uniteEnseignement.desgnationUE,
      coefficientUE: uniteEnseignement.coefficientUE,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uniteEnseignement = this.createFromForm();
    if (uniteEnseignement.id !== undefined) {
      this.subscribeToSaveResponse(this.uniteEnseignementService.update(uniteEnseignement));
    } else {
      this.subscribeToSaveResponse(this.uniteEnseignementService.create(uniteEnseignement));
    }
  }

  private createFromForm(): IUniteEnseignement {
    return {
      ...new UniteEnseignement(),
      id: this.editForm.get(['id'])!.value,
      nomUE: this.editForm.get(['nomUE'])!.value,
      desgnationUE: this.editForm.get(['desgnationUE'])!.value,
      coefficientUE: this.editForm.get(['coefficientUE'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUniteEnseignement>>): void {
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
