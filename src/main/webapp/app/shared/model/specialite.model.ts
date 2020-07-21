export interface ISpecialite {
  id?: number;
  libelle?: string;
  cycleId?: number;
  nomcycle?:string
}

export class Specialite implements ISpecialite {
  constructor(public id?: number, public libelle?: string, public cycleId?: number) {}
}
