import { Component, OnInit, OnDestroy } from '@angular/core';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'examen',
  templateUrl: './examen.component.html'
})
export class ExamenComponent implements OnInit, OnDestroy {
  session: string = '';
  constructor() {}
  ngOnInit() {}
  ngOnDestroy() {}
}
