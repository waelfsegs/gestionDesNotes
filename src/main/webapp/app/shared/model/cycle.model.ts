export interface ICycle {
  id?: number;
  nomcycle?: string;
}

export class Cycle implements ICycle {
  constructor(public id?: number, public nomcycle?: string) {}
}
