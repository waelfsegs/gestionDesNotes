import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMatiere, Matiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';
import { IRegime } from 'app/shared/model/regime.model';
import { RegimeService } from 'app/entities/regime/regime.service';

@Component({
  selector: 'jhi-matiere-update',
  templateUrl: './matiere-update.component.html',
})
export class MatiereUpdateComponent implements OnInit {
  isSaving = false;
  regimes: IRegime[] = [];

  editForm = this.fb.group({
    id: [],
    coefficientMatiere: [],
    coefficientTp: [],
    coefficientDc: [],
    coefficientExem: [],
    designation: [],
    nom: [],
    regimeId: [],
  });

  constructor(
    protected matiereService: MatiereService,
    protected regimeService: RegimeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matiere }) => {
      this.updateForm(matiere);

      this.regimeService.query().subscribe((res: HttpResponse<IRegime[]>) => (this.regimes = res.body || []));
    });
  }

  updateForm(matiere: IMatiere): void {
    this.editForm.patchValue({
      id: matiere.id,
      coefficientMatiere: matiere.coefficientMatiere,
      coefficientTp: matiere.coefficientTp,
      coefficientDc: matiere.coefficientDc,
      coefficientExem: matiere.coefficientExem,
      designation: matiere.designation,
      nom: matiere.nom,
      regimeId: matiere.regimeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matiere = this.createFromForm();
    if (matiere.id !== undefined) {
      this.subscribeToSaveResponse(this.matiereService.update(matiere));
    } else {
      this.subscribeToSaveResponse(this.matiereService.create(matiere));
    }
  }

  private createFromForm(): IMatiere {
    return {
      ...new Matiere(),
      id: this.editForm.get(['id'])!.value,
      coefficientMatiere: this.editForm.get(['coefficientMatiere'])!.value,
      coefficientTp: this.editForm.get(['coefficientTp'])!.value,
      coefficientDc: this.editForm.get(['coefficientDc'])!.value,
      coefficientExem: this.editForm.get(['coefficientExem'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      regimeId: this.editForm.get(['regimeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatiere>>): void {
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

  trackById(index: number, item: IRegime): any {
    return item.id;
  }
}
