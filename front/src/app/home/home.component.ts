import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { GlobalService } from '../services/global.service';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  id: number | any;
  name: string| any;
  description: string| any;
  price: number| any;
  quantity: number| any;
  image: string| any;
  products: any[] = [];
  title = 'Frontend';
  searchproductForm!:FormGroup;

  userId:any = UserStorageService.getUserId();

  isCustomerLoggedIn : boolean = UserStorageService.isCustomerLoggedIn();
  isAdminLoggedIn : boolean = UserStorageService.isAdminLoggedIn();

  constructor(private router:Router, 
    private fb:FormBuilder,
    private globalService:GlobalService,
    private snackbar:MatSnackBar,
    ) {}

  ngOnInit():void{
    this.router.events.subscribe(event =>{
      this.isCustomerLoggedIn = UserStorageService.isCustomerLoggedIn();
      this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
    })
    this.searchproductForm = this.fb.group({
      title:[null, [Validators.required]]
    })

    this.products = [
      {
        id: 1,
        name: "Ps5",
        description: "this the best gaming ps relesased now ",
        price: 5000,
        processedImg: "./../../../assets/img/product01.png"
      },
      {
        id: 2,
        name: "Pc",
        description: "this a new version of pcs",
        price: 3000,
        processedImg: "./../../../assets/img/product03.png"
      },
      
    ];
    }


  

    onWishlistClick() {
      if (!this.isUserLoggedIn()) {
        // Choose one or both methods for displaying the message:
  
        // Using Angular Material Snackbar (Simple):
        this.snackbar.open('You have to login first!','login', {
          duration: 2000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
          panelClass: ['login-message'] 
        });
  
        
      }
    }  
    isUserLoggedIn() {
    
      return false; // Replace with your logic
    }
  
    showInlineMessage = false;
  
    closeMessage() {
  
      this.showInlineMessage = false;
    }






  


  submitForm(){
    this.products = [];
    const title = this.searchproductForm.get('title')!.value;
      this.globalService.findAllProductsByName(title).subscribe(res=>{
        res.forEach(element => {
          element.processedImg = 'data:image/jpg;base64,' + element.byteImg;

          this.products.push(element);
        });
        console.log(this.products)
      })
  }

  logout(){
    if (UserStorageService.signOut()) {
      this.router.navigateByUrl("/login");
    } else {
      console.error('Failed to sign out');
    }
  }

  getUserName(): String{
    return UserStorageService.getUser();
  }
  
  
}
