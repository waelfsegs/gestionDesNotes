import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
@Component({
  selector: 'note-controle-continue',
  templateUrl: './note-controle-continue.component.html',
  styleUrls: ['note-controle-continue.scss']
})
export class NoteControleContinueComponent implements OnInit, OnDestroy {
  unsubscribe = new Subject();
  currentaccount: Account | null = null;
  matiereid: number = 0;
  groupid: number = 0;
  enseignentid: number = 0;
  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.currentaccount = account;
      if (this.currentaccount) {
        if (this.currentaccount.ensiegnent) {
          this.enseignentid = Number(this.currentaccount.ensiegnent);
        }
      }
    });
  }
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
