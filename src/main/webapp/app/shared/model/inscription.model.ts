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
  matricule?: number;
  cin?: number;
  nom?: string;
  prenom?: string;
  tel?: number;
  nomclass?: string;
  nomgroup?: string;
  numSemstre?: number;
}

export class Inscription implements IInscription {
  constructor(
    public id?: number,
    public date?: Moment,
    public annee?: Moment,
    public etudiantId?: number,
    public classeId?: number,
    public groupeId?: number,
    public semstreId?: number,
    public cin?: number,
    public nom?: string,
    public prenom?: string,
    public tel?: number,
    public nomclass?: string,
    public nomgroup?: string,
    public numSemstre?: number
  ) {}
}
