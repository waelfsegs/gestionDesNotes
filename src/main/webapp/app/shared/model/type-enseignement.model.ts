export interface ITypeEnseignement {
  id?: number;
  type?: string;
}

export class TypeEnseignement implements ITypeEnseignement {
  constructor(public id?: number, public type?: string) {}
}
