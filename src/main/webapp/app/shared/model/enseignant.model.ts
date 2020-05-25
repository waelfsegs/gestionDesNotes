import { Moment } from 'moment';

export interface IEnseignant {
  id?: number;
  nom?: string;
  pernom?: string;
  mail?: string;
  matricule?: number;
  cin?: number;
  dateEmbauchement?: Moment;
  departementId?: number;
}

export class Enseignant implements IEnseignant {
  constructor(
    public id?: number,
    public nom?: string,
    public pernom?: string,
    public mail?: string,
    public matricule?: number,
    public cin?: number,
    public dateEmbauchement?: Moment,
    public departementId?: number
  ) {}
}
