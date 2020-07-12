import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISpicialitematiere, Spicialitematiere } from 'app/shared/model/spicialitematiere.model';
import { SpicialitematiereService } from './spicialitematiere.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';

type SelectableEntity = IMatiere | ISpecialite;

@Component({
  selector: 'jhi-spicialitematiere-update',
  templateUrl: './spicialitematiere-update.component.html'
})
export class SpicialitematiereUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];
  specialites: ISpecialite[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [],
    matiereId: [],
    specialiteId: []
  });

  constructor(
    protected spicialitematiereService: SpicialitematiereService,
    protected matiereService: MatiereService,
    protected specialiteService: SpecialiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spicialitematiere }) => {
      this.updateForm(spicialitematiere);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));

      this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));
    });
  }

  updateForm(spicialitematiere: ISpicialitematiere): void {
    this.editForm.patchValue({
      id: spicialitematiere.id,
      libelle: spicialitematiere.libelle,
      matiereId: spicialitematiere.matiereId,
      specialiteId: spicialitematiere.specialiteId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const spicialitematiere = this.createFromForm();
    if (spicialitematiere.id !== undefined) {
      this.subscribeToSaveResponse(this.spicialitematiereService.update(spicialitematiere));
    } else {
      this.subscribeToSaveResponse(this.spicialitematiereService.create(spicialitematiere));
    }
  }

  private createFromForm(): ISpicialitematiere {
    return {
      ...new Spicialitematiere(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      matiereId: this.editForm.get(['matiereId'])!.value,
      specialiteId: this.editForm.get(['specialiteId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpicialitematiere>>): void {
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
