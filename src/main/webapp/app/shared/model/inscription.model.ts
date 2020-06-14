import { Moment } from 'moment';

export interface IInscription {
  id?: number;
  date?: Moment;
  annee?: Moment;
  etudiantId?: number;
  classeId?: number;
  groupeId?: number;
  semstreId?: number;
  nomEtudiant?: string;
  classe?: string;
  group?: string;
  semstre?: number;
}

export class Inscription implements IInscription {
  constructor(
    public id?: number,
    public date?: Moment,
    public annee?: Moment,
    public etudiantId?: number,
    public classeId?: number,
    public groupeId?: number,
    public semstreId?: number
  ) {}
}
