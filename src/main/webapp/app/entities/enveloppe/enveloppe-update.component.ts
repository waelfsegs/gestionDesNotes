import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEnveloppe, Enveloppe } from 'app/shared/model/enveloppe.model';
import { EnveloppeService } from './enveloppe.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';

@Component({
  selector: 'jhi-enveloppe-update',
  templateUrl: './enveloppe-update.component.html'
})
export class EnveloppeUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];

  editForm = this.fb.group({
    id: [],
    nameenv: [],
    maiereId: []
  });

  constructor(
    protected enveloppeService: EnveloppeService,
    protected matiereService: MatiereService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enveloppe }) => {
      this.updateForm(enveloppe);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));
    });
  }

  updateForm(enveloppe: IEnveloppe): void {
    this.editForm.patchValue({
      id: enveloppe.id,
      nameenv: enveloppe.nameenv,
      maiereId: enveloppe.maiereId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enveloppe = this.createFromForm();
    if (enveloppe.id !== undefined) {
      this.subscribeToSaveResponse(this.enveloppeService.update(enveloppe));
    } else {
      this.subscribeToSaveResponse(this.enveloppeService.create(enveloppe));
    }
  }

  private createFromForm(): IEnveloppe {
    return {
      ...new Enveloppe(),
      id: this.editForm.get(['id'])!.value,
      nameenv: this.editForm.get(['nameenv'])!.value,
      maiereId: this.editForm.get(['maiereId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnveloppe>>): void {
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

  trackById(index: number, item: IMatiere): any {
    return item.id;
  }
}
