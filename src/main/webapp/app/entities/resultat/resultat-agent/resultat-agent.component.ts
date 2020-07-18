import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'resultat-agent',
  templateUrl: './resultat-agent.component.html'
})
export class resultatAgentComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {}
}
