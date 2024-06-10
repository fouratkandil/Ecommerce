import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DemoAngularMaterailModule } from '../DemoAngularMaterailModule';
import { CartComponent } from './components/cart/cart.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { PlaceOrderComponent } from './components/place-order/place-order.component';
import { ReviewOrderedProductsComponent } from './components/review-ordered-products/review-ordered-products.component';
import { UserUpdateComponent } from './components/user-update/user-update.component';
import { ViewOrderedProductsComponent } from './components/view-ordered-products/view-ordered-products.component';
import { ViewPrductDetailComponent } from './components/view-prduct-detail/view-prduct-detail.component';
import { ViewwishlistComponent } from './components/viewwishlist/viewwishlist.component';
import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerComponent } from './customer.component';


@NgModule({
  declarations: [
    CustomerComponent,
    DashboardComponent,
    CartComponent,
    PlaceOrderComponent,
    MyOrdersComponent,
    ViewOrderedProductsComponent,
    ReviewOrderedProductsComponent,
    ViewPrductDetailComponent,
    ViewwishlistComponent,
    UserUpdateComponent,
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    DemoAngularMaterailModule
  ]
})
export class CustomerModule { }
