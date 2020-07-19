import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGroupe, Groupe } from 'app/shared/model/groupe.model';
import { GroupeService } from './groupe.service';

@Component({
  selector: 'jhi-groupe-update',
  templateUrl: './groupe-update.component.html'
})
export class GroupeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nomgroup: []
  });

  constructor(protected groupeService: GroupeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupe }) => {
      this.updateForm(groupe);
    });
  }

  updateForm(groupe: IGroupe): void {
    this.editForm.patchValue({
      id: groupe.id,
      nomgroup: groupe.nomgroup
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groupe = this.createFromForm();
    if (groupe.id !== undefined) {
      this.subscribeToSaveResponse(this.groupeService.update(groupe));
    } else {
      this.subscribeToSaveResponse(this.groupeService.create(groupe));
    }
  }

  private createFromForm(): IGroupe {
    return {
      ...new Groupe(),
      id: this.editForm.get(['id'])!.value,
      nomgroup: this.editForm.get(['nomgroup'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupe>>): void {
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
