import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'classe-spcialite',
  templateUrl: './classe-spcialite.component.html'
})
export class ClasseSpcialiteComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {}
}
