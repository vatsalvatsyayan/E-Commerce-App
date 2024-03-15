import { Component, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {

  isAuthenticated: boolean = false;
  userFullName: string = '';

  storage: Storage = sessionStorage;

  constructor(public auth: AuthService) { }

  ngOnInit(): void {
    
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
    this.auth.user$.subscribe(
      (user) => {
        if (user) {
          this.userFullName = user.name as string;

          const theEmail = user.email;

          this.storage.setItem('userEmail', JSON.stringify(theEmail));
        }
      }
    );

  }

  logout() {
    this.auth.logout();
  }

}
