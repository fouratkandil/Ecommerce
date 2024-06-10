import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-viewwishlist',
  templateUrl: './viewwishlist.component.html',
  styleUrl: './viewwishlist.component.scss'
})
export class ViewwishlistComponent {
  products:any[]=[];

  constructor(
    private customerService:CustomerService
  ){}

  ngOnInit():void{
    this.getWishlistByUserId();
  }

  getWishlistByUserId(){
    this.customerService.getWishlistByUserId().subscribe(res=>{
      res.forEach(element => {
        element.processedImg = 'data:image/jpg;base64,' + element.returnedImg;
        this.products.push(element);
      });
    })
  }
  
}
