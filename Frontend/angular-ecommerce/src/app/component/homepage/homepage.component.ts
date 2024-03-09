import { Component, OnInit } from '@angular/core';

import { AuthService } from '@auth0/auth0-angular';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  accessToken?: Observable<string | null>;
  constructor(public auth: AuthService) { }

  ngOnInit(): void {
    this.auth.user$.subscribe(user => {
      console.log('User Information:', user);
    });

    this.accessToken = this.auth.getAccessTokenSilently();
    console.log('access token ', this.accessToken )

  }

}
