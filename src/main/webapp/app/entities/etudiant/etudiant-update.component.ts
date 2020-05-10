import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtudiant, Etudiant } from 'app/shared/model/etudiant.model';
import { EtudiantService } from './etudiant.service';

@Component({
  selector: 'jhi-etudiant-update',
  templateUrl: './etudiant-update.component.html'
})
export class EtudiantUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cin: [],
    nom: [],
    matericule: [],
    prenom: []
  });

  constructor(protected etudiantService: EtudiantService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etudiant }) => {
      this.updateForm(etudiant);
    });
  }

  updateForm(etudiant: IEtudiant): void {
    this.editForm.patchValue({
      id: etudiant.id,
      cin: etudiant.cin,
      nom: etudiant.nom,
      matericule: etudiant.matericule,
      prenom: etudiant.prenom
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etudiant = this.createFromForm();
    if (etudiant.id !== undefined) {
      this.subscribeToSaveResponse(this.etudiantService.update(etudiant));
    } else {
      this.subscribeToSaveResponse(this.etudiantService.create(etudiant));
    }
  }

  private createFromForm(): IEtudiant {
    return {
      ...new Etudiant(),
      id: this.editForm.get(['id'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      matericule: this.editForm.get(['matericule'])!.value,
      prenom: this.editForm.get(['prenom'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtudiant>>): void {
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
}
