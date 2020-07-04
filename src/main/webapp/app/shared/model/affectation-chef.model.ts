import { Moment } from 'moment';

export interface IAffectationChef {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  departementId?: number;
  enseignantId?: number;
  departementName?: string;
  enseignantName?: string;
  enseignantPrenom?: string;
}

export class AffectationChef implements IAffectationChef {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public departementId?: number,
    public enseignantId?: number,
    public departementName?: string,
    public enseignantName?: string,
    public enseignantPrenom?: string
  ) {}
}
