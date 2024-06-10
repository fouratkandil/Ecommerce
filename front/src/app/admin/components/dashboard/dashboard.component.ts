import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
    products: any[] = [];
    searchproductForm!:FormGroup;

    constructor(
      private adminSerice:AdminService, 
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
      this.adminSerice.getAllProducts().subscribe(res=>{
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
      this.adminSerice.findAllProductsByName(title).subscribe(res=>{
        res.forEach(element => {
          element.processedImg = 'data:image/jpg;base64,' + element.byteImg;

          this.products.push(element);
        });
      })
  }

  deleteProduct(productId: any) {
    this.adminSerice.deleteProduct(productId).subscribe({
      next: () => {
        this.snackbar.open('Product deleted successfully', 'Close', { duration: 5000 });
        this.getAllProducts(); // Refresh product list after successful deletion
      },
      error: (error) => {
        this.snackbar.open('Error deleting product', 'Close', {
          duration: 5000,
          panelClass: "error-snackbar"
        });
        console.error('Error deleting product:', error);
      }
    });
  }
  
}
