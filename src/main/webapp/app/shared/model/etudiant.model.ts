export interface IEtudiant {
  id?: number;
  cin?: number;
  nom?: string;
  matericule?: string;
  prenom?: string;
}

export class Etudiant implements IEtudiant {
  constructor(public id?: number, public cin?: number, public nom?: string, public matericule?: string, public prenom?: string) {}
}
