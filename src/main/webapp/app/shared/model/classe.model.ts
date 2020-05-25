export interface IClasse {
  id?: number;
  nom?: string;
  specialiteId?: number;
  niveauId?: number;
}

export class Classe implements IClasse {
  constructor(public id?: number, public nom?: string, public specialiteId?: number, public niveauId?: number) {}
}
