import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICorrige, Corrige } from 'app/shared/model/corrige.model';
import { CorrigeService } from './corrige.service';
import { IEnseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from 'app/entities/enseignant/enseignant.service';
import { IEnveloppe } from 'app/shared/model/enveloppe.model';
import { EnveloppeService } from 'app/entities/enveloppe/enveloppe.service';

type SelectableEntity = IEnseignant | IEnveloppe;

@Component({
  selector: 'jhi-corrige-update',
  templateUrl: './corrige-update.component.html'
})
export class CorrigeUpdateComponent implements OnInit {
  isSaving = false;
  enseignants: IEnseignant[] = [];
  enveloppes: IEnveloppe[] = [];

  editForm = this.fb.group({
    id: [],
    enseignantId: [],
    enveloppeId: []
  });

  constructor(
    protected corrigeService: CorrigeService,
    protected enseignantService: EnseignantService,
    protected enveloppeService: EnveloppeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ corrige }) => {
      this.updateForm(corrige);

      this.enseignantService.query().subscribe((res: HttpResponse<IEnseignant[]>) => (this.enseignants = res.body || []));

      this.enveloppeService.query().subscribe((res: HttpResponse<IEnveloppe[]>) => (this.enveloppes = res.body || []));
    });
  }

  updateForm(corrige: ICorrige): void {
    this.editForm.patchValue({
      id: corrige.id,
      enseignantId: corrige.enseignantId,
      enveloppeId: corrige.enveloppeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const corrige = this.createFromForm();
    if (corrige.id !== undefined) {
      this.subscribeToSaveResponse(this.corrigeService.update(corrige));
    } else {
      this.subscribeToSaveResponse(this.corrigeService.create(corrige));
    }
  }

  private createFromForm(): ICorrige {
    return {
      ...new Corrige(),
      id: this.editForm.get(['id'])!.value,
      enseignantId: this.editForm.get(['enseignantId'])!.value,
      enveloppeId: this.editForm.get(['enveloppeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICorrige>>): void {
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
