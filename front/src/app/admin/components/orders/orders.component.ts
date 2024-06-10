import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss'
})
export class OrdersComponent {

  orders:any;

  constructor(
    private adminSerice:AdminService, 
    private snackbar: MatSnackBar,
  ){}

  ngOnInit(){
    this.getPlacedOrders();
  }

  getPlacedOrders():void{
    this.adminSerice.getPlacedOrders().subscribe(res=>{
      this.orders = res;
    })
  }

  changeOrderStatus(orderId:number, status:string):void{
    this.adminSerice.changeOrderStatus(orderId,status).subscribe(res=>{
      if(res && res.id != null){
        this.snackbar.open('Order Status Changed Successfully','Close', {duration:5000});
        this.getPlacedOrders();  
      }
      else{
        this.snackbar.open('Somrthing went wrong','Close', {duration:5000});
      }
    })
  }
}
