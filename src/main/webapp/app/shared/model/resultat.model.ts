export interface IResultat {
  id?: number;
  notecc1?: number;
  notecc2?: number;
  noteexmen?: number;
  matiereId?: number;
  inscriptionId?: number;
}

export class Resultat implements IResultat {
  constructor(
    public id?: number,
    public notecc1?: number,
    public notecc2?: number,
    public noteexmen?: number,
    public matiereId?: number,
    public inscriptionId?: number
  ) {}
}
