export interface Inote {
  coefficientMatiere?: number;
  nom?: string;
  regime?: string;
  notecc1?: number;
  notecc2?: number;
  examne?: number;
}

export class Note implements Inote {
  constructor(
    public id?: number,
    public coefficientMatiere?: number,
    public coefficientTp?: number,
    public coefficientDc?: number,
    public coefficientExem?: number,
    public designation?: string,
    public nom?: string,
    public regimeId?: number
  ) {}
}
