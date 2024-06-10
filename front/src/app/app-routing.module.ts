import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { TrackOrderComponent } from './track-order/track-order.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [{path:"login", component:LoginComponent}, 
                        {path:"signup", component:SignupComponent},
                        {path:"order", component:TrackOrderComponent},
                        { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
                        { path: 'customer', loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule) },
                        {
                          path: '', component: HomeComponent
                        },];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
