export interface IGroupEnseigner {
  id?: number;
  nomgroup?: string;
  ensid?: string;
  matiereid?: string;
}

export class GroupEnseigner implements IGroupEnseigner {
  constructor(public id?: number, public nomgroup?: string, public ensid?: string, public matiereid?: string) {}
}
