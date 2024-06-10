import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GlobalService } from '../services/global.service';
import { UserStorageService } from '../services/storage/user-storage.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Overlay, OverlayRef, OverlayConfig } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  title = 'Frontend';
  searchproductForm!:FormGroup;
  products: any[] = [];

  userId:any = UserStorageService.getUserId();

  isCustomerLoggedIn : boolean = UserStorageService.isCustomerLoggedIn();
  isAdminLoggedIn : boolean = UserStorageService.isAdminLoggedIn();

  constructor(private router:Router, 
    private fb:FormBuilder,
    private globalService:GlobalService,
    private snackbar: MatSnackBar,
    private overlay: Overlay,
    private snackBar: MatSnackBar


    ) {}


    sidebar(){
      document.querySelector("#sidebar").classList.toggle("expand");
    }
    
  ngOnInit():void{
    this.router.events.subscribe(event =>{
      this.isCustomerLoggedIn = UserStorageService.isCustomerLoggedIn();
      this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
    })
    this.searchproductForm = this.fb.group({
      title:[null, [Validators.required]]
    })

  }

  onWishlistClick() {
    if (!this.isUserLoggedIn()) {
      // Choose one or both methods for displaying the message:

      // Using Angular Material Snackbar (Simple):
      this.snackBar.open('You have to login first!','login', {
        duration: 2000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
        panelClass: ['login-message'] 
      });

      
    }
  }

  showInlineMessage = false;

  closeMessage() {

    this.showInlineMessage = false;
  }

  isUserLoggedIn() {
    
    return false; // Replace with your logic
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
