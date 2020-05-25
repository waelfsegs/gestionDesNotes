import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAffectationChef, AffectationChef } from 'app/shared/model/affectation-chef.model';
import { AffectationChefService } from './affectation-chef.service';
import { IDepartement } from 'app/shared/model/departement.model';
import { DepartementService } from 'app/entities/departement/departement.service';
import { IEnseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from 'app/entities/enseignant/enseignant.service';

type SelectableEntity = IDepartement | IEnseignant;

@Component({
  selector: 'jhi-affectation-chef-update',
  templateUrl: './affectation-chef-update.component.html',
})
export class AffectationChefUpdateComponent implements OnInit {
  isSaving = false;
  departements: IDepartement[] = [];
  enseignants: IEnseignant[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    departementId: [],
    enseignantId: [],
  });

  constructor(
    protected affectationChefService: AffectationChefService,
    protected departementService: DepartementService,
    protected enseignantService: EnseignantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affectationChef }) => {
      this.updateForm(affectationChef);

      this.departementService.query().subscribe((res: HttpResponse<IDepartement[]>) => (this.departements = res.body || []));

      this.enseignantService.query().subscribe((res: HttpResponse<IEnseignant[]>) => (this.enseignants = res.body || []));
    });
  }

  updateForm(affectationChef: IAffectationChef): void {
    this.editForm.patchValue({
      id: affectationChef.id,
      startDate: affectationChef.startDate,
      endDate: affectationChef.endDate,
      departementId: affectationChef.departementId,
      enseignantId: affectationChef.enseignantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const affectationChef = this.createFromForm();
    if (affectationChef.id !== undefined) {
      this.subscribeToSaveResponse(this.affectationChefService.update(affectationChef));
    } else {
      this.subscribeToSaveResponse(this.affectationChefService.create(affectationChef));
    }
  }

  private createFromForm(): IAffectationChef {
    return {
      ...new AffectationChef(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      departementId: this.editForm.get(['departementId'])!.value,
      enseignantId: this.editForm.get(['enseignantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffectationChef>>): void {
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
