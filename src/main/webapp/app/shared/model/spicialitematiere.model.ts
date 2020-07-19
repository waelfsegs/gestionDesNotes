export interface ISpicialitematiere {
  id?: number;
  libelle?: string;
  matiereId?: number;
  specialiteId?: number;
  matiereNom?: string;
}

export class Spicialitematiere implements ISpicialitematiere {
  constructor(public id?: number, public libelle?: string, public matiereId?: number, public specialiteId?: number) {}
}
