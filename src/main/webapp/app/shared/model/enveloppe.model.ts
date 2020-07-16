export interface IEnveloppe {
  id?: number;
  nameenv?: string;
  maiereId?: number;
  maiere?: string;
}

export class Enveloppe implements IEnveloppe {
  constructor(public id?: number, public nameenv?: string, public maiereId?: number) {}
}
