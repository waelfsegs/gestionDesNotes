export interface ISemstre {
  id?: number;
  annee?: number;
  numSemstre?: number;
}

export class Semstre implements ISemstre {
  constructor(public id?: number, public annee?: number, public numSemstre?: number) {}
}
