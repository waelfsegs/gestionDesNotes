export interface ICorrige {
  id?: number;
  enseignantId?: number;
  enveloppeId?: number;
  enseignantNom?: string;
  enseignantPrenom?: string;
  enveloppenameenv?: string;
  nbrecopieCorrige?: number;
  nbreCopieinenvloppe?: number;
}

export class Corrige implements ICorrige {
  constructor(
    public id?: number,
    public enseignantId?: number,
    public enveloppeId?: number,
    public nbrecopieCorrige?: number,
    public nbreCopieinenvloppe?: number
  ) {}
}
