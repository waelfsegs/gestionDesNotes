export interface IDepartement {
  id?: number;
  nomDep?: string;
  designiation?: string;
}

export class Departement implements IDepartement {
  constructor(public id?: number, public nomDep?: string, public designiation?: string) {}
}
