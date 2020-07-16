export interface IExamen {
  id?: number;
  matricule?: string;
  session?: string;
  numcomp?: number;
  matiereId?: number;
  inscriptionId?: number;
  semstreId?: number;
  niveauId?: number;
  specialiteId?: number;
  enveloppeId?: number;
  cycleId?: number;
  done?: boolean;
  note?: number;
  resultatid?: number;
  forEdit?: boolean;
}

export class Examen implements IExamen {
  constructor(
    public id?: number,
    public matricule?: string,
    public session?: string,
    public numcomp?: number,
    public matiereId?: number,
    public inscriptionId?: number,
    public semstreId?: number,
    public niveauId?: number,
    public specialiteId?: number,
    public enveloppeId?: number,
    public cycleId?: number
  ) {}
}
