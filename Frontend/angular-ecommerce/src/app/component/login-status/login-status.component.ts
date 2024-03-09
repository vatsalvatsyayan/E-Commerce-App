import { Component, Inject, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { OKTA_AUTH, OktaAuthStateService } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {


  isAuthenticated: boolean = false;
  userFullName: string = '';

  constructor(private oktaAuthService: OktaAuthStateService,
    @Inject(OKTA_AUTH) private oktaAuth: OktaAuth,
    public auth: AuthService) { }

  ngOnInit(): void {
    
    // subscribe to authentication state changes 
    this.oktaAuthService.authState$.subscribe(
      (result) => {
        this.isAuthenticated = result.isAuthenticated!;
        this.getUserDetails();
      }
    );
    
    this.auth.isAuthenticated$.subscribe(
      (isAuthenticated) => {
        this.isAuthenticated = isAuthenticated;
        if(isAuthenticated)
        {
          this.getUserDetails();
        }
      }
    );
    
  }
  getUserDetails() {

    // Fetch the logged in user details (user's claims)

    // user full name is exposed as a property name
    this.oktaAuth.getUser().then(
      (res) => {
        this.userFullName = res.name as string;
      }
    );

    this.auth.user$.subscribe(
      (user) => {
        if (user) {
          this.userFullName = user.name as string;
        }
      }
    );

  }

  logout() {
    // Terminates the session with Okta and removes current tokens.
    //this.oktaAuth.signOut();
    this.auth.logout();
  }

}
