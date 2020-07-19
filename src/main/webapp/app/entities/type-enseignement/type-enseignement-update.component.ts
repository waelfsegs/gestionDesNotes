import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeEnseignement, TypeEnseignement } from 'app/shared/model/type-enseignement.model';
import { TypeEnseignementService } from './type-enseignement.service';

@Component({
  selector: 'jhi-type-enseignement-update',
  templateUrl: './type-enseignement-update.component.html'
})
export class TypeEnseignementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: []
  });

  constructor(
    protected typeEnseignementService: TypeEnseignementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeEnseignement }) => {
      this.updateForm(typeEnseignement);
    });
  }

  updateForm(typeEnseignement: ITypeEnseignement): void {
    this.editForm.patchValue({
      id: typeEnseignement.id,
      type: typeEnseignement.type
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeEnseignement = this.createFromForm();
    if (typeEnseignement.id !== undefined) {
      this.subscribeToSaveResponse(this.typeEnseignementService.update(typeEnseignement));
    } else {
      this.subscribeToSaveResponse(this.typeEnseignementService.create(typeEnseignement));
    }
  }

  private createFromForm(): ITypeEnseignement {
    return {
      ...new TypeEnseignement(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeEnseignement>>): void {
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
