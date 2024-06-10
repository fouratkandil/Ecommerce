import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  products: any[] = [];
    searchproductForm!:FormGroup;

    constructor(
      private customerService:CustomerService, 
      private snackbar: MatSnackBar,
      private fb:FormBuilder
    ){}

    ngOnInit(){
      this.getAllProducts();
      this.searchproductForm = this.fb.group({
        title:[null, [Validators.required]]
      })
    }

    getAllProducts(){
      this.products = [];
      this.customerService.getAllProducts().subscribe(res=>{
        res.forEach(element => {
          element.processedImg = 'data:image/jpg;base64,' + element.byteImg;

          this.products.push(element);
        });
        console.log(this.products);
      })
    }

  submitForm(){
    this.products = [];
    const title = this.searchproductForm.get('title')!.value;
      this.customerService.findAllProductsByName(title).subscribe(res=>{
        res.forEach(element => {
          element.processedImg = 'data:image/jpg;base64,' + element.byteImg;

          this.products.push(element);
        });
        console.log(this.products)
      })
  }

  submitForm1() {
    this.products = [];
    const title = this.searchproductForm.get('title')!.value;
    this.customerService.findAllProductsByName(title).subscribe(res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpg;base64,' + element.byteImg;
        this.products.push(element);
      });
      console.log(this.products);
    });
  }

  addToCart(id:any){
    this.customerService.addToCart(id).subscribe(res=>{
      this.snackbar.open("Product added successfully to Cart", "Close", {duration:5000})
    })
  }

}
