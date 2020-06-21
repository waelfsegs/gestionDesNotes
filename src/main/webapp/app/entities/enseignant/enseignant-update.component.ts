import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEnseignant, Enseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from './enseignant.service';
import { IDepartement } from 'app/shared/model/departement.model';
import { DepartementService } from 'app/entities/departement/departement.service';
import { User } from 'app/core/user/user.model';
import { RegisterService } from 'app/account/register/register.service';
import { JhiLanguageService } from 'ng-jhipster';

import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-enseignant-update',
  templateUrl: './enseignant-update.component.html'
})
export class EnseignantUpdateComponent implements OnInit {
  user: User = {};
  isSaving = false;
  departements: IDepartement[] = [];
  dateEmbauchementDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    pernom: [],
    mail: [],
    matricule: [],
    cin: [],
    dateEmbauchement: [],
    departementId: []
  });

  constructor(
    protected enseignantService: EnseignantService,
    protected departementService: DepartementService,
    protected activatedRoute: ActivatedRoute,
    private registerService: RegisterService,
    private fb: FormBuilder,
    private userService: UserService,
    private languageService: JhiLanguageService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enseignant }) => {
      this.updateForm(enseignant);

      this.departementService.query().subscribe((res: HttpResponse<IDepartement[]>) => (this.departements = res.body || []));
    });
  }

  updateForm(enseignant: IEnseignant): void {
    this.editForm.patchValue({
      id: enseignant.id,
      nom: enseignant.nom,
      pernom: enseignant.pernom,
      mail: enseignant.mail,
      matricule: enseignant.matricule,
      cin: enseignant.cin,
      dateEmbauchement: enseignant.dateEmbauchement,
      departementId: enseignant.departementId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enseignant = this.createFromForm();
    if (enseignant.id !== undefined) {
      this.subscribeToSaveResponse(this.enseignantService.update(enseignant));
    } else {
      this.subscribeToSaveResponse(this.enseignantService.create(enseignant));
    }
  }
  prepreUser(enseignant: IEnseignant) {
    this.user.ensiegnent = enseignant.id;
    this.user.activated = true;
    this.user.firstName = enseignant.nom;
    this.user.lastName = enseignant.pernom;
    (this.user.authorities = ['ROLE_Enseignant']), (this.user.langKey = 'fr');
  }
  private createFromForm(): IEnseignant {
    return {
      ...new Enseignant(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      pernom: this.editForm.get(['pernom'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      dateEmbauchement: this.editForm.get(['dateEmbauchement'])!.value,
      departementId: this.editForm.get(['departementId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnseignant>>): void {
    result.subscribe(
      res => {
        if (res.body) {
          this.prepreUser(res.body);
          this.registerService
            .save({
              login: res.body.cin + '',
              email: res.body.mail,
              password: res.body.matricule + '',
              langKey: this.languageService.getCurrentLanguage(),
              ensiegnent: this.user.ensiegnent,
              firstName: this.user.firstName,
              activated: true,
              lastName: this.user.lastName,
              authorities: this.user.authorities
            })
            .subscribe(() => this.onSaveSuccess());
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

  trackById(index: number, item: IDepartement): any {
    return item.id;
  }
}
