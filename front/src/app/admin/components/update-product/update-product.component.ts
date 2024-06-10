import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.scss'
})
export class UpdateProductComponent {

  productId= this.activatedRoute.snapshot.params["productId"];

  productForm!:FormGroup;
  listOfCategories: any = [];
  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;
  existingImage: string | null = null;
  imgChange=false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackbar: MatSnackBar,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute
  ){}

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.imgChange=true;

    this.existingImage=null;
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
    this.getProductById();
  }

  getAllCategories(): void {
    this.adminService.getAllCategories().subscribe(res => {
      this.listOfCategories = res;
      console.log('Fetched listOfCategories:', this.listOfCategories);
    });
  }
  
  getProductById():void{
    this.adminService.getProductById(this.productId).subscribe(res=>{
      this.productForm.patchValue(res);
      this.existingImage = 'data:image/jpg;base64,' + res.byteImg;
    })
  }

  updateProduct(): void {
    if (this.productForm.valid) {
      const formData: FormData = new FormData();

      if(this.imgChange && this.selectedFile){
        formData.append('img', this.selectedFile); // Append the image file here
      }
      formData.append('categoryId', this.productForm.get('categoryId').value);
      formData.append('name', this.productForm.get('name').value);
      formData.append('description', this.productForm.get('description').value);
      formData.append('price', this.productForm.get('price').value);
  
      this.adminService.updateProduct(this.productId,formData).subscribe((response) => {
        if (response && response.id != null) {
          this.snackbar.open('Product Updated Successfully', 'Close', { duration: 5000 });
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
