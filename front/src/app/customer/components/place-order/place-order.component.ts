import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.scss'
})
export class PlaceOrderComponent {
  
  cartItems: any[] = [];
  orderForm!:FormGroup;

  constructor(
    private customerService:CustomerService,
    private snackbar:MatSnackBar,
    private fb:FormBuilder,
    private router:Router,
    private dialog:MatDialog,
  ){}

  ngOnInit(){
    this.orderForm = this.fb.group({
      address:[null, [Validators.required]],
      orderDescription:[null]
    })
    this.closeForm;
  }

  placeOrder():void{
    this.customerService.placeOrder(this.orderForm.value).subscribe(res=>{
      if(res && res.id!=null){
        this.snackbar.open("Order Placed Successffuly", "Close", {duration:5000});
        this.router.navigateByUrl("/customer/my-orders");
        this.closeForm();
        
      }
      else{
        this.snackbar.open("Something went wrong", "ERROR", {duration:5000});
      }
    });
   

  }
  this
  
  closeForm():void{
    this.dialog.closeAll();
    window.location.reload();

  }

}
