import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEnseignement, Enseignement } from 'app/shared/model/enseignement.model';
import { EnseignementService } from './enseignement.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { IEnseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from 'app/entities/enseignant/enseignant.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';
import { ITypeEnseignement } from 'app/shared/model/type-enseignement.model';
import { TypeEnseignementService } from 'app/entities/type-enseignement/type-enseignement.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';

type SelectableEntity = IMatiere | IEnseignant | IGroupe | ITypeEnseignement | IClasse;

@Component({
  selector: 'jhi-enseignement-update',
  templateUrl: './enseignement-update.component.html'
})
export class EnseignementUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];
  enseignants: IEnseignant[] = [];
  groupes: IGroupe[] = [];
  typeenseignements: ITypeEnseignement[] = [];
  classes: IClasse[] = [];

  editForm = this.fb.group({
    id: [],
    matiereId: [],
    enseignantId: [],
    groupeId: [],
    typeEnseignementId: [],
    classeId: []
  });

  constructor(
    protected enseignementService: EnseignementService,
    protected matiereService: MatiereService,
    protected enseignantService: EnseignantService,
    protected groupeService: GroupeService,
    protected typeEnseignementService: TypeEnseignementService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enseignement }) => {
      this.updateForm(enseignement);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));

      this.enseignantService.query().subscribe((res: HttpResponse<IEnseignant[]>) => (this.enseignants = res.body || []));

      this.groupeService.query().subscribe((res: HttpResponse<IGroupe[]>) => (this.groupes = res.body || []));

      this.typeEnseignementService.query().subscribe((res: HttpResponse<ITypeEnseignement[]>) => (this.typeenseignements = res.body || []));

      this.classeService.query().subscribe((res: HttpResponse<IClasse[]>) => (this.classes = res.body || []));
    });
  }

  updateForm(enseignement: IEnseignement): void {
    this.editForm.patchValue({
      id: enseignement.id,
      matiereId: enseignement.matiereId,
      enseignantId: enseignement.enseignantId,
      groupeId: enseignement.groupeId,
      typeEnseignementId: enseignement.typeEnseignementId,
      classeId: enseignement.classeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enseignement = this.createFromForm();
    if (enseignement.id !== undefined) {
      this.subscribeToSaveResponse(this.enseignementService.update(enseignement));
    } else {
      this.subscribeToSaveResponse(this.enseignementService.create(enseignement));
    }
  }

  private createFromForm(): IEnseignement {
    return {
      ...new Enseignement(),
      id: this.editForm.get(['id'])!.value,
      matiereId: this.editForm.get(['matiereId'])!.value,
      enseignantId: this.editForm.get(['enseignantId'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value,
      typeEnseignementId: this.editForm.get(['typeEnseignementId'])!.value,
      classeId: this.editForm.get(['classeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnseignement>>): void {
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
