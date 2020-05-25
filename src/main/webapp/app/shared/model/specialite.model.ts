export interface ISpecialite {
  id?: number;
  libelle?: string;
}

export class Specialite implements ISpecialite {
  constructor(public id?: number, public libelle?: string) {}
}
