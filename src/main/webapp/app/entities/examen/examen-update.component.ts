import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExamen, Examen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription/inscription.service';
import { ISemstre } from 'app/shared/model/semstre.model';
import { SemstreService } from 'app/entities/semstre/semstre.service';
import { INiveau } from 'app/shared/model/niveau.model';
import { NiveauService } from 'app/entities/niveau/niveau.service';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';
import { IEnveloppe } from 'app/shared/model/enveloppe.model';
import { EnveloppeService } from 'app/entities/enveloppe/enveloppe.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle/cycle.service';

type SelectableEntity = IMatiere | IInscription | ISemstre | INiveau | ISpecialite | IEnveloppe | ICycle;

@Component({
  selector: 'jhi-examen-update',
  templateUrl: './examen-update.component.html'
})
export class ExamenUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];
  inscriptions: IInscription[] = [];
  semstres: ISemstre[] = [];
  niveaus: INiveau[] = [];
  specialites: ISpecialite[] = [];
  enveloppes: IEnveloppe[] = [];
  cycles: ICycle[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [],
    session: [],
    numcomp: [],
    matiereId: [],
    inscriptionId: [],
    semstreId: [],
    niveauId: [],
    specialiteId: [],
    enveloppeId: [],
    cycleId: []
  });

  constructor(
    protected examenService: ExamenService,
    protected matiereService: MatiereService,
    protected inscriptionService: InscriptionService,
    protected semstreService: SemstreService,
    protected niveauService: NiveauService,
    protected specialiteService: SpecialiteService,
    protected enveloppeService: EnveloppeService,
    protected cycleService: CycleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examen }) => {
      this.updateForm(examen);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));

      this.inscriptionService.query().subscribe((res: HttpResponse<IInscription[]>) => (this.inscriptions = res.body || []));

      this.semstreService.query().subscribe((res: HttpResponse<ISemstre[]>) => (this.semstres = res.body || []));

      this.niveauService.query().subscribe((res: HttpResponse<INiveau[]>) => (this.niveaus = res.body || []));

      this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));

      this.enveloppeService.query().subscribe((res: HttpResponse<IEnveloppe[]>) => (this.enveloppes = res.body || []));

      this.cycleService.query().subscribe((res: HttpResponse<ICycle[]>) => (this.cycles = res.body || []));
    });
  }

  updateForm(examen: IExamen): void {
    this.editForm.patchValue({
      id: examen.id,
      matricule: examen.matricule,
      session: examen.session,
      numcomp: examen.numcomp,
      matiereId: examen.matiereId,
      inscriptionId: examen.inscriptionId,
      semstreId: examen.semstreId,
      niveauId: examen.niveauId,
      specialiteId: examen.specialiteId,
      enveloppeId: examen.enveloppeId,
      cycleId: examen.cycleId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examen = this.createFromForm();
    if (examen.id !== undefined) {
      this.subscribeToSaveResponse(this.examenService.update(examen));
    } else {
      this.subscribeToSaveResponse(this.examenService.create(examen));
    }
  }

  private createFromForm(): IExamen {
    return {
      ...new Examen(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      session: this.editForm.get(['session'])!.value,
      numcomp: this.editForm.get(['numcomp'])!.value,
      matiereId: this.editForm.get(['matiereId'])!.value,
      inscriptionId: this.editForm.get(['inscriptionId'])!.value,
      semstreId: this.editForm.get(['semstreId'])!.value,
      niveauId: this.editForm.get(['niveauId'])!.value,
      specialiteId: this.editForm.get(['specialiteId'])!.value,
      enveloppeId: this.editForm.get(['enveloppeId'])!.value,
      cycleId: this.editForm.get(['cycleId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamen>>): void {
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
