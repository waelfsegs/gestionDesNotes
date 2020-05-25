export interface IGroupe {
  id?: number;
  nomgroup?: string;
}

export class Groupe implements IGroupe {
  constructor(public id?: number, public nomgroup?: string) {}
}
