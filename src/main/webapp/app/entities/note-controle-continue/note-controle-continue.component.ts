import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'note-controle-continue',
  templateUrl: './note-controle-continue.component.html',
  styleUrls: ['note-controle-continue.scss']
})
export class NoteControleContinueComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();

  matiereid: number = 0;
  groupid: number = 0;
  constructor(private accountService: AccountService) {}

  ngOnInit() {}
  selectmatiere(id: number) {
    this.matiereid = id;
  }
  selectgroupe(id: number) {
    this.groupid = id;
  }
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
