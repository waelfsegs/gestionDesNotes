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
import { User, IUser } from 'app/core/user/user.model';
import { RegisterService } from 'app/account/register/register.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle/cycle.service';
import { INiveau } from 'app/shared/model/niveau.model';
import { NiveauService } from 'app/entities/niveau/niveau.service';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';
type SelectableEntity = IEtudiant | IClasse | IGroupe | ISemstre | ICycle | INiveau | ISpecialite;

@Component({
  selector: 'jhi-inscription-update',
  templateUrl: './inscription-update.component.html'
})
export class InscriptionUpdateComponent implements OnInit {
  isSaving = false;
  etudiants: IEtudiant[] = [];
  classes: IClasse[] = [];
  groupes: IGroupe[] = [];
  semstres: ISemstre[] = [];
  cycles: ICycle[] = [];
  niveaus: INiveau[] = [];
  specialites: ISpecialite[] = [];
  dateDp: any;
  anneeDp: any;
  etudiant: IEtudiant = {};
  user: IUser = {};

  editForm = this.fb.group({
    id: [],
    date: [],
    annee: [],
    etudiantId: [],
    classeId: [],
    groupeId: [],
    semstreId: [],
    idetudiant: [],
    cin: [],
    nom: [],
    matricule: [],
    prenom: [],
    tel: [],
    dateNais: [],
    cycleId: [],
    niveauId: [],
    specialiteId: []
  });

  constructor(
    protected inscriptionService: InscriptionService,
    protected etudiantService: EtudiantService,
    protected classeService: ClasseService,
    protected groupeService: GroupeService,
    protected semstreService: SemstreService,
    protected activatedRoute: ActivatedRoute,
    private registerService: RegisterService,
    protected cycleService: CycleService,
    protected niveauService: NiveauService,
    protected specialiteService: SpecialiteService,

    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscription }) => {
      this.updateForm(inscription);

      this.etudiantService.query().subscribe((res: HttpResponse<IEtudiant[]>) => (this.etudiants = res.body || []));

      this.classeService.query().subscribe((res: HttpResponse<IClasse[]>) => (this.classes = res.body || []));

      this.groupeService.query().subscribe((res: HttpResponse<IGroupe[]>) => (this.groupes = res.body || []));

      this.semstreService.query().subscribe((res: HttpResponse<ISemstre[]>) => (this.semstres = res.body || []));
      this.cycleService.query().subscribe((res: HttpResponse<ICycle[]>) => (this.cycles = res.body || []));

      this.niveauService.query().subscribe((res: HttpResponse<INiveau[]>) => (this.niveaus = res.body || []));

      this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));
	  

    });
  }
  prepreUser(etudiant: IEtudiant) {
    this.user.etudiant = etudiant.id;
    (this.user.login = etudiant.cin + ''), (this.user.password = etudiant.matricule + ''), (this.user.activated = true);
    this.user.firstName = etudiant.nom;
    this.user.lastName = etudiant.prenom;
    (this.user.authorities = ['ROLE_ETUDIANT']), (this.user.langKey = 'fr');
  }
  updateForm(inscription: IInscription): void {
    console.log(inscription)
    this.editForm.patchValue({
      id: inscription.id,
      date: inscription.date,
      annee: inscription.annee,
      etudiantId: inscription.etudiantId,
      classeId: inscription.classeId,
      groupeId: inscription.groupeId,
      semstreId: inscription.semstreId,
      cin: inscription.cin,
      nom: inscription.nom,
      prenom: inscription.prenom,
      tel: inscription.tel,
      dateNais: inscription.dateNais,
      matricule: inscription.matricule,
      cycleId: inscription.cycleId,
      niveauId: inscription.niveauId,
      specialiteId: inscription.specialiteId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inscription = this.createFromForm();
    let etudiant: IEtudiant = {};
    etudiant.cin = inscription.cin;
    etudiant.nom = inscription.nom;
    etudiant.prenom = inscription.prenom;
    etudiant.matricule = inscription.matricule;
    etudiant.tel = inscription.tel;
    etudiant.dateNais = inscription.dateNais;
    if (inscription.id !== undefined) {
      etudiant.id = inscription.etudiantId;
      this.etudiantService.update(etudiant).subscribe();
      this.subscribeToUpdateResponse(this.inscriptionService.update(inscription));
    } else {
      this.etudiantService.create(etudiant).subscribe(res => {
        if (res.body) {
          console.log('etudiant', res.body);
          inscription.etudiantId = res.body.id;
          etudiant.id = res.body.id;
          this.prepreUser(etudiant);
          this.subscribeToSaveResponse(this.inscriptionService.create(inscription));
        }
      });
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
      cin: this.editForm.get(['cin'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      dateNais: this.editForm.get(['dateNais'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      cycleId: this.editForm.get(['cycleId'])!.value,
      niveauId: this.editForm.get(['niveauId'])!.value,
      specialiteId: this.editForm.get(['specialiteId'])!.value
	  
    };
  }
  protected subscribeToUpdateResponse(result: Observable<HttpResponse<IInscription>>): void {
    result.subscribe(
      res => {
        this.onSaveSuccess();
      })
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInscription>>): void {
    result.subscribe(
      res => {
        if (res.body) {
          console.log('inscription', res.body);
          console.log('user', this.user);
          this.registerService
            .save({
              login: this.user.login + '',
              email: this.user.login + '@gmail.com',
              password: this.user.password,
              langKey: this.user.langKey,
              etudiant: this.user.etudiant,
              firstName: this.user.firstName,
              activated: true,
              lastName: this.user.lastName,
              authorities: this.user.authorities
            })
            .subscribe(res => {
              this.onSaveSuccess();
            });
        }
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
