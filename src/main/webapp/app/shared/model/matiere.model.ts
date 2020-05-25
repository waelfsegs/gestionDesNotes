export interface IMatiere {
  id?: number;
  coefficientMatiere?: number;
  coefficientTp?: number;
  coefficientDc?: number;
  coefficientExem?: number;
  designation?: string;
  nom?: string;
  regimeId?: number;
}

export class Matiere implements IMatiere {
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
