import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PlaceOrderComponent } from '../place-order/place-order.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {

  cartItems: any[] = [];
  order:any;
  totalAmount: number | null = null; // Variable to hold total amount

  couponForm!:FormGroup;

  constructor(
    private customerService:CustomerService,
    private matSnackBar:MatSnackBar,
    private fb:FormBuilder,
    private dialog:MatDialog
  ){}


  ngOnInit(){
    this.couponForm = this.fb.group({
      code:[null, [Validators.required]]
    })
    this.getCart();
  }

  applyCoupon():void{
    this.customerService.applyCoupon(this.couponForm.get('code')!.value).subscribe(res=>{
      this.matSnackBar.open("Coupon Applied successfully", "Close", {duration:5000});
      this.getCart();
    }, error=>{
      this.matSnackBar.open(error.error, "Close", {duration:5000});
    })
  } 

  getCart(){
    this.cartItems = [];
    this.customerService.getCartByUserId().subscribe(res=>{
      this.order = res;
      res.cartItems.forEach(element=>{
        element.processedImg = 'data:image/jpg;base64,' + element.returnedImg;
        this.cartItems.push(element);
      });
      // Check if totalAmount exists and assign it
      if (this.order && this.order.totalAmount !== null) {
        this.totalAmount = this.order.totalAmount;
      } else {
        this.totalAmount = null; // Set totalAmount to null if not available
      }
    })
  }
  removeProductFromCart(id: any) {
    this.customerService.removeFromCart(id).subscribe(res => {
        this.matSnackBar.open("Product removed successfully from Cart", "Close", { duration: 5000 });
    });
    this.getCart();
}

  increaseQuantity(productId:any){
    this.customerService.increaseProductQuantity(productId).subscribe(res=>{
      this.matSnackBar.open("Quantity Increased successfully", "Close", {duration:5000});
      this.getCart();
    })
  }

  decreaseQuantity(productId:any){
    this.customerService.decreaseProductQuantity(productId).subscribe(res=>{
      this.matSnackBar.open("Quantity Decreased successfully", "Close", {duration:5000});
      this.getCart();
    })
  }

  placeOrder():void{
    this.dialog.open(PlaceOrderComponent);
  }
}
