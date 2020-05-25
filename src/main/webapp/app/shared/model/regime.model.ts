export interface IRegime {
  id?: number;
  type?: string;
}

export class Regime implements IRegime {
  constructor(public id?: number, public type?: string) {}
}
