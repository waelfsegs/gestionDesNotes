export interface IGroupbyetudiant {
  groupe_id?: number;
  nom?: string;
  matricule?: string;
  prenom?: string;
  show?: boolean;
}

export class Groupbyetudiant implements IGroupbyetudiant {
  constructor(public groupe_id?: number, public nom?: string, public matricule?: string, public prenom?: string, show?: boolean) {}
}
