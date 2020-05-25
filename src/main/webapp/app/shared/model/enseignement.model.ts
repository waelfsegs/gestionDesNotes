export interface IEnseignement {
  id?: number;
  matiereId?: number;
  enseignantId?: number;
  groupeId?: number;
  typeEnseignementId?: number;
}

export class Enseignement implements IEnseignement {
  constructor(
    public id?: number,
    public matiereId?: number,
    public enseignantId?: number,
    public groupeId?: number,
    public typeEnseignementId?: number
  ) {}
}
