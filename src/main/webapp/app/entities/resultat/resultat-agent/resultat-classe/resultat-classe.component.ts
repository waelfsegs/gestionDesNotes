import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'resultat-classe',
  templateUrl: './resultat-classe.component.html'
})
export class resultatClasseComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {}
}
