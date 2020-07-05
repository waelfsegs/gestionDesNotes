export interface IEnseignement {
  id?: number;
  matiereId?: number;
  enseignantId?: number;
  groupeId?: number;
  typeEnseignementId?: number;
  classeId?: number;
  matierenom?: string;
  groupenom?: string;
  typeEnseignement?: string;
  enseignantnom?: string;
}

export class Enseignement implements IEnseignement {
  constructor(
    public id?: number,
    public matiereId?: number,
    public enseignantId?: number,
    public groupeId?: number,
    public typeEnseignementId?: number,
    public classeId?: number
  ) {}
}
