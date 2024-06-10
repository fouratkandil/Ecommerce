import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-post-product',
  templateUrl: './post-product.component.html',
  styleUrl: './post-product.component.scss'
})
export class PostProductComponent {
  productForm!:FormGroup;
  listOfCategories: any = [];
  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackbar: MatSnackBar,
    private adminService: AdminService
  ){}

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () =>{
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }

  ngOnInit(): void{
    this.productForm = this.fb.group({
      categoryId : [null, [Validators.required]],
      name: [null, [Validators.required]],
      price: [null, [Validators.required]],
      description: [null, [Validators.required]]
    })

    this.getAllCategories();
  }

  getAllCategories(): void {
    this.adminService.getAllCategories().subscribe(res => {
      this.listOfCategories = res;
      console.log('Fetched listOfCategories:', this.listOfCategories);
    });
  }
  

  addProduct(): void {
    if (this.productForm.valid && this.selectedFile) {
      const formData: FormData = new FormData();
      formData.append('img', this.selectedFile); // Append the image file here
      formData.append('categoryId', this.productForm.get('categoryId').value);
      formData.append('name', this.productForm.get('name').value);
      formData.append('description', this.productForm.get('description').value);
      formData.append('price', this.productForm.get('price').value);
  
      this.adminService.addProduct(formData).subscribe((response) => {
        if (response && response.id != null) {
          this.snackbar.open('Product Posted Successfully', 'Close', { duration: 5000 });
          this.router.navigateByUrl("/admin/dashboard");
        } else {
          this.snackbar.open(response.message, 'Close', {
            duration: 5000,
            panelClass: "error-snackbar"
          });
        }
      });
    } else {
      for (const i in this.productForm.controls) {
        this.productForm.controls[i].markAsDirty();
        this.productForm.controls[i].updateValueAndValidity();
      }
    }
  }
  
}
