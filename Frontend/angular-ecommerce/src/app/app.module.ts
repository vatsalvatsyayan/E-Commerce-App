import { Injector, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ProductListComponent } from './component/product-list/product-list.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ProductService } from './services/product.service';

import { Routes, RouterModule, Router} from '@angular/router';
import { ProductCategoryMenuComponent } from './component/product-category-menu/product-category-menu.component';
import { SearchComponent } from './component/search/search.component';
import { ProductDetailsComponent } from './component/product-details/product-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CartStatusComponent } from './component/cart-status/cart-status.component';
import { CartDetailsComponent } from './component/cart-details/cart-details.component';
import { CheckoutComponent } from './component/checkout/checkout.component';
import { ReactiveFormsModule } from '@angular/forms';

import { AuthGuard, AuthHttpInterceptor, AuthModule, AuthService } from '@auth0/auth0-angular';
import { LoginComponent } from './component/login/login.component';
import { LoginStatusComponent } from './component/login-status/login-status.component';
import { MembersPageComponent } from './component/members-page/members-page.component';
import { OrderHistoryComponent } from './component/order-history/order-history.component';

function sendToLoginPage(auth: AuthService, injector : Injector){
    // use injector to acccess any service available within your app
    const router = injector.get(Router);

    // redirect the user to your custom login page
    router.navigate(['/login']);
}

const routes: Routes = [
  {path: 'order-history', component: OrderHistoryComponent, canActivate: [AuthGuard],
                    data: {onAuthRequired: sendToLoginPage}},

                    {path: 'login', component: LoginComponent},
  {path: 'members', component: MembersPageComponent, canActivate: [AuthGuard],
                    data: {onAuthRequired: sendToLoginPage}},
  
                    {path: 'login', component: LoginComponent},
  {path: 'checkout', component: CheckoutComponent},
  {path: 'cart-details', component: CartDetailsComponent},
  {path: 'products/:id', component: ProductDetailsComponent},
  {path: 'search/:keyword', component: ProductListComponent},
  {path: 'category/:id', component: ProductListComponent},
  {path: 'category', component: ProductListComponent},
  {path: 'products', component: ProductListComponent},
  {path: '', redirectTo: '/products', pathMatch: 'full'},
  {path: '**', redirectTo: '/products', pathMatch: 'full'}
];



@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    ProductCategoryMenuComponent,
    SearchComponent,
    ProductDetailsComponent,
    CartStatusComponent,
    CartDetailsComponent,
    CheckoutComponent,
    LoginComponent,
    LoginStatusComponent,
    MembersPageComponent,
    OrderHistoryComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    AuthModule.forRoot({
      domain: 'dev-6ztx7jb2twrna510.us.auth0.com',
      clientId: '4NqVSDiUS60mNQh50QauYr18K5EpO2Iz',
      authorizationParams: {
        redirect_uri: 'http://localhost:4200',
        audience: 'http://localhost:8080',
        scope: 'openid profile email',
      },
      httpInterceptor: {
        allowedList: [
          {
            uri: 'http://localhost:8080/*',
            tokenOptions: {
              authorizationParams: {
                audience: 'http://localhost:8080',
                scope: 'openid profile email'
              }
            }
          }
        ]
      }
    }),
  ],
  providers: [ProductService, 
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHttpInterceptor,
      multi: true
    },
    ],
  bootstrap: [AppComponent]
})


export class AppModule { }
