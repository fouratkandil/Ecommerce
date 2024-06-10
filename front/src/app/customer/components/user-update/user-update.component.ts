import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserStorageService } from '../../../services/storage/user-storage.service';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrl: './user-update.component.scss'
})
export class UserUpdateComponent {
  userId:any = UserStorageService.getUserId();
  signupForm! : FormGroup;
  hidePassword = false;
  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;
  existingImage: string | null = null;
  imgChange=false;
  constructor(private fb:FormBuilder,
    private snackBar:MatSnackBar,
    private cutomerService:CustomerService,
    private router:Router){}

  ngOnInit(): void{
    this.signupForm = this.fb.group({
      name : [null, [Validators.required]],
      email : [null, [Validators.required, Validators.email]],
      password : [null, [Validators.required]],
      confirmpassword : [null, [Validators.required]],
    })

    this.getUserById();
  }
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

  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;
  }

  getUserById():void{
    this.cutomerService.getUserById(this.userId).subscribe(res=>{
      this.signupForm.patchValue({
        name:res.name,
        email:res.email,
      });
      console.log(res);
    })
  }

  onSubmit():void{
    if(this.signupForm.valid){
      const password = this.signupForm.get('password')?.value;
      const confirmpassword = this.signupForm.get('confirmpassword')?.value;
      const formData: FormData = new FormData();
      if(password != confirmpassword){
        this.snackBar.open('Passwords do not match','Close', {duration:5000, panelClass:'errorSnackBar'});
        return;
      }
      formData.append('name', this.signupForm.get('name').value);
      formData.append('email', this.signupForm.get('email').value);
      formData.append('password', this.signupForm.get('password').value);
      this.cutomerService.updateUser(this.userId,formData).subscribe(
        (response) =>{
          this.snackBar.open('User Updated  successfully', 'Close', {duration:5000});
          this.router.navigateByUrl("/customer/dashboard");
        },
        (error) =>{
          this.snackBar.open('Failed to update. Please try again','Close', {duration:5000, panelClass:'errorSnackBar'});
        }
      )
    }
}
}
