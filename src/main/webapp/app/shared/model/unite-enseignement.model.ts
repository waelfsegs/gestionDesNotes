export interface IUniteEnseignement {
  id?: number;
  nomUE?: string;
  desgnationUE?: string;
  coefficientUE?: number;
}

export class UniteEnseignement implements IUniteEnseignement {
  constructor(public id?: number, public nomUE?: string, public desgnationUE?: string, public coefficientUE?: number) {}
}
