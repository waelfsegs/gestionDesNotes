export interface INiveau {
  id?: number;
  niveau?: string;
  cycleId?: number;
  nomcycle?: string;
  specialiteId?: number;
  specialiteNom?: string;
}

export class Niveau implements INiveau {
  constructor(public id?: number, public niveau?: string, public cycleId?: number) {}
}
