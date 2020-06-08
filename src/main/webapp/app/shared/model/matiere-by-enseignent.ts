export interface IMatierByEnseignent {
  id?: number;
  ensgId?: number;
  nomMatiere?: string;
  designation?: string;
  groupe_id?: number;
}

export class MatierByEnseignen implements IMatierByEnseignent {
  constructor(
    public id?: number,
    public groupe_id?: number,
    public nomMatiere?: string,
    public designation?: string,
    public ensgId?: number
  ) {}
}
