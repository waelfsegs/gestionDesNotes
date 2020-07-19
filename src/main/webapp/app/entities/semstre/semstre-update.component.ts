import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISemstre, Semstre } from 'app/shared/model/semstre.model';
import { SemstreService } from './semstre.service';

@Component({
  selector: 'jhi-semstre-update',
  templateUrl: './semstre-update.component.html'
})
export class SemstreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    annee: [],
    numSemstre: []
  });

  constructor(protected semstreService: SemstreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ semstre }) => {
      this.updateForm(semstre);
    });
  }

  updateForm(semstre: ISemstre): void {
    this.editForm.patchValue({
      id: semstre.id,
      annee: semstre.annee,
      numSemstre: semstre.numSemstre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const semstre = this.createFromForm();
    if (semstre.id !== undefined) {
      this.subscribeToSaveResponse(this.semstreService.update(semstre));
    } else {
      this.subscribeToSaveResponse(this.semstreService.create(semstre));
    }
  }

  private createFromForm(): ISemstre {
    return {
      ...new Semstre(),
      id: this.editForm.get(['id'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      numSemstre: this.editForm.get(['numSemstre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISemstre>>): void {
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
