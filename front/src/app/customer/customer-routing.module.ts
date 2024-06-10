import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerComponent } from './customer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CartComponent } from './components/cart/cart.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { ViewOrderedProductsComponent } from './components/view-ordered-products/view-ordered-products.component';
import { ReviewOrderedProductsComponent } from './components/review-ordered-products/review-ordered-products.component';
import { ViewPrductDetailComponent } from './components/view-prduct-detail/view-prduct-detail.component';
import { ViewwishlistComponent } from './components/viewwishlist/viewwishlist.component';
import { UserUpdateComponent } from './components/user-update/user-update.component';

const routes: Routes = [
  { path: 'CustomerComponent', component: CustomerComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'cart', component: CartComponent },
  { path: 'my_orders', component: MyOrdersComponent },
  { path: 'ordered_products/:orderId', component: ViewOrderedProductsComponent },
  { path: 'review/:productId', component: ReviewOrderedProductsComponent },
  { path: 'product/:productId', component: ViewPrductDetailComponent },
  { path: 'wishlist', component: ViewwishlistComponent },
  { path: 'profile/:userId', component: UserUpdateComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
