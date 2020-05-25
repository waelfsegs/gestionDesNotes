import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInscription, Inscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from './inscription.service';
import { IEtudiant } from 'app/shared/model/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/etudiant.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';
import { ISemstre } from 'app/shared/model/semstre.model';
import { SemstreService } from 'app/entities/semstre/semstre.service';

type SelectableEntity = IEtudiant | IClasse | IGroupe | ISemstre;

@Component({
  selector: 'jhi-inscription-update',
  templateUrl: './inscription-update.component.html',
})
export class InscriptionUpdateComponent implements OnInit {
  isSaving = false;
  etudiants: IEtudiant[] = [];
  classes: IClasse[] = [];
  groupes: IGroupe[] = [];
  semstres: ISemstre[] = [];
  dateDp: any;
  anneeDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    annee: [],
    etudiantId: [],
    classeId: [],
    groupeId: [],
    semstreId: [],
  });

  constructor(
    protected inscriptionService: InscriptionService,
    protected etudiantService: EtudiantService,
    protected classeService: ClasseService,
    protected groupeService: GroupeService,
    protected semstreService: SemstreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscription }) => {
      this.updateForm(inscription);

      this.etudiantService.query().subscribe((res: HttpResponse<IEtudiant[]>) => (this.etudiants = res.body || []));

      this.classeService.query().subscribe((res: HttpResponse<IClasse[]>) => (this.classes = res.body || []));

      this.groupeService.query().subscribe((res: HttpResponse<IGroupe[]>) => (this.groupes = res.body || []));

      this.semstreService.query().subscribe((res: HttpResponse<ISemstre[]>) => (this.semstres = res.body || []));
    });
  }

  updateForm(inscription: IInscription): void {
    this.editForm.patchValue({
      id: inscription.id,
      date: inscription.date,
      annee: inscription.annee,
      etudiantId: inscription.etudiantId,
      classeId: inscription.classeId,
      groupeId: inscription.groupeId,
      semstreId: inscription.semstreId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inscription = this.createFromForm();
    if (inscription.id !== undefined) {
      this.subscribeToSaveResponse(this.inscriptionService.update(inscription));
    } else {
      this.subscribeToSaveResponse(this.inscriptionService.create(inscription));
    }
  }

  private createFromForm(): IInscription {
    return {
      ...new Inscription(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      etudiantId: this.editForm.get(['etudiantId'])!.value,
      classeId: this.editForm.get(['classeId'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value,
      semstreId: this.editForm.get(['semstreId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInscription>>): void {
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
