import { Moment } from 'moment';

export interface IEtudiantNote {
  id?: number;
  cin?: number;
  nom?: string;
  matricule?: string;
  prenom?: string;
  notecc1?: number;
  notecc2?: number;
  idinscription?: number;
}

export class EtudiantNote implements IEtudiantNote {
  constructor(
    public id?: number,
    public cin?: number,
    public nom?: string,
    public matricule?: string,
    public prenom?: string,
    public notecc1?: number,
    public notecc2?: number,
    public idinscription?: number
  ) {}
}
