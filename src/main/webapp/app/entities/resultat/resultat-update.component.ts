import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IResultat, Resultat } from 'app/shared/model/resultat.model';
import { ResultatService } from './resultat.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription/inscription.service';

type SelectableEntity = IMatiere | IInscription;

@Component({
  selector: 'jhi-resultat-update',
  templateUrl: './resultat-update.component.html'
})
export class ResultatUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];
  inscriptions: IInscription[] = [];

  editForm = this.fb.group({
    id: [],
    notecc1: [],
    notecc2: [],
    noteexmen: [],
    matiereId: [],
    inscriptionId: []
  });

  constructor(
    protected resultatService: ResultatService,
    protected matiereService: MatiereService,
    protected inscriptionService: InscriptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultat }) => {
      this.updateForm(resultat);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));

      this.inscriptionService.query().subscribe((res: HttpResponse<IInscription[]>) => (this.inscriptions = res.body || []));
    });
  }

  updateForm(resultat: IResultat): void {
    this.editForm.patchValue({
      id: resultat.id,
      notecc1: resultat.notecc1,
      notecc2: resultat.notecc2,
      noteexmen: resultat.noteexmen,
      matiereId: resultat.matiereId,
      inscriptionId: resultat.inscriptionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultat = this.createFromForm();
    if (resultat.id !== undefined) {
      this.subscribeToSaveResponse(this.resultatService.update(resultat));
    } else {
      this.subscribeToSaveResponse(this.resultatService.create(resultat));
    }
  }

  private createFromForm(): IResultat {
    return {
      ...new Resultat(),
      id: this.editForm.get(['id'])!.value,
      notecc1: this.editForm.get(['notecc1'])!.value,
      notecc2: this.editForm.get(['notecc2'])!.value,
      noteexmen: this.editForm.get(['noteexmen'])!.value,
      matiereId: this.editForm.get(['matiereId'])!.value,
      inscriptionId: this.editForm.get(['inscriptionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultat>>): void {
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
