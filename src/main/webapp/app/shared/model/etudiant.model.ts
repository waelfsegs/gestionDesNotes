import { Moment } from 'moment';

export interface IEtudiant {
  id?: number;
  cin?: number;
  nom?: string;
  matricule?: string;
  prenom?: string;
  tel?: number;
  dateNais?: Moment;
}

export class Etudiant implements IEtudiant {
  constructor(
    public id?: number,
    public cin?: number,
    public nom?: string,
    public matricule?: string,
    public prenom?: string,
    public tel?: number,
    public dateNais?: Moment
  ) {}
}
