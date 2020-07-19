import { ISpicialitematiere } from './../../shared/model/spicialitematiere.model';
import { SpicialitematiereService } from './../spicialitematiere/spicialitematiere.service';
import { map } from 'rxjs/operators';
import { ISpecialite } from './../../shared/model/specialite.model';
import { SpecialiteService } from './../specialite/specialite.service';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormControl, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IMatiere, Matiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';
import { IRegime } from 'app/shared/model/regime.model';
import { RegimeService } from 'app/entities/regime/regime.service';
import { ClassGetter } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'jhi-matiere-update',
  templateUrl: './matiere-update.component.html'
})
export class MatiereUpdateComponent implements OnInit {
  isSaving = false;
  regimes: IRegime[] = [];
  spicailite$: Observable<ISpecialite[] | null> = of([]);

  editForm = this.fb.group({
    id: [],
    coefficientMatiere: [],
    coefficientTp: [],
    coefficientDc: [],
    coefficientExem: [],
    designation: [],
    nom: [],
    regimeId: [],
    checkArray: this.fb.array([])
  });
  matier: any;

  constructor(
    protected matiereService: MatiereService,
    protected regimeService: RegimeService,
    protected activatedRoute: ActivatedRoute,
    private spcialiteSercice: SpecialiteService,
    private fb: FormBuilder,
    private spicialitematiereService: SpicialitematiereService
  ) {}

  ngOnInit(): void {
    this.spicailite$ = this.spcialiteSercice.query().pipe(map(res => res.body));
    this.activatedRoute.data.subscribe(({ matiere }) => {
      this.updateForm(matiere);
      this.matier=matiere

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
      regimeId: matiere.regimeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matiere = this.createFromForm();
    if (matiere.id !== undefined) {
      this.subscribeToUpdateResponse(this.matiereService.update(matiere));
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
      regimeId: this.editForm.get(['regimeId'])!.value
    };
  }
  protected subscribeToUpdateResponse(result: Observable<HttpResponse<IMatiere>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatiere>>): void {
    result.subscribe(
      res => {
        const checkArray: FormArray = this.editForm.get('checkArray') as FormArray;
        let selectedvalues: any[] = checkArray.value;
        selectedvalues.forEach(element => {
          let spciatiltematire: ISpicialitematiere = {};
          spciatiltematire.libelle = this.editForm.get(['nom'])!.value;
          if (res.body) spciatiltematire.matiereId = res.body.id;
          spciatiltematire.specialiteId = Number(element);
          this.spicialitematiereService.create(spciatiltematire).subscribe(res => {
            this.onSaveSuccess();
          });
        });
      },
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
  onCheckboxChange(e: any) {
    const checkArray: FormArray = this.editForm.get('checkArray') as FormArray;

    if (e.target.checked) {
      checkArray.push(new FormControl(e.target.value));
    } else {
      let i: number = 0;
      checkArray.controls.forEach(item => {
        if (item.value == e.target.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }
  trackById(index: number, item: IRegime): any {
    return item.id;
  }
}
