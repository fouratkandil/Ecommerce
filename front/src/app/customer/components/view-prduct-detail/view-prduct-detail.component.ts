import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { UserStorageService } from '../../../services/storage/user-storage.service';

@Component({
  selector: 'app-view-prduct-detail',
  templateUrl: './view-prduct-detail.component.html',
  styleUrl: './view-prduct-detail.component.scss'
})
export class ViewPrductDetailComponent {

  productId:number = this.activatedRoute.snapshot.params["productId"];

  product:any;
  FAQs:any[]=[];
  reviews:any[]=[];

  constructor(
    private snackBar:MatSnackBar,
    private customerService:CustomerService,
    private activatedRoute:ActivatedRoute
  ){}

  ngOnInit():void{
    this.getProuctDetailById();
  }

  getProuctDetailById():void{
    this.customerService.getProductDetailById(this.productId).subscribe(res=>{
      this.product = res.productDto;
      this.product.processedImg = 'data:image/jpg;base64,' + res.productDto.byteImg;

      this.FAQs = res.faqDtoList;

      res.reviewDtoList.forEach(element => {
        element.processedImg = 'data:image/jpg;base64,' + element.returnedImg;
        this.reviews.push(element);
      });
    })
  }

  addToWishList():void{
    const heartIcon = document.querySelector('.heart-icon');
    heartIcon.classList.toggle('active');
    
    const wishListDto={
      productId:this.productId,
      userId:UserStorageService.getUserId()
    }
    this.customerService.addProductToWishlist(wishListDto).subscribe(res=>{
      if(res && res.id!=null){
        this.snackBar.open("Product Added to WishList Successffuly", "Close", {duration:5000});
      }
      else{
        this.snackBar.open("Already in WishList", "ERROR", {duration:5000});
      }
    })
  }
  updateWishlistQuantity(increment: number): void {
    // Fetch the current wishlist quantity
    const wishlistQuantityElement = document.querySelector('.wishlist-quantity');
    let wishlistQuantity = parseInt(wishlistQuantityElement.textContent);
  
    // Update the wishlist quantity by the increment
    wishlistQuantity += increment;
  
    // Update the UI with the new wishlist quantity
    wishlistQuantityElement.textContent = wishlistQuantity.toString();
  }
}
