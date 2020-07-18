import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'resultat-agent',
  templateUrl: './resultat-agent.component.html'
})
export class resultatAgentComponent implements OnInit {
  spicialite$: Observable<ISpecialite[] | null> = of([]);
  constructor(protected activatedRoute: ActivatedRoute, private spcialiteservice: SpecialiteService) {}

  ngOnInit(): void {
    this.spicialite$ = this.spcialiteservice.query().pipe(map(res => res.body));
  }
}
