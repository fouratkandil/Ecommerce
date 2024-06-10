import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {
  signupForm! : FormGroup;
  hidePassword = false;

  constructor(private fb:FormBuilder,
    private snackBar:MatSnackBar,
    private authService:AuthService,
    private router:Router){}

  ngOnInit(): void{
    this.signupForm = this.fb.group({
      name : [null, [Validators.required]],
      email : [null, [Validators.required, Validators.email]],
      password : [null, [Validators.required]],
      confirmpassword : [null, [Validators.required]],
    })
  }

  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;
  }

  onSubmit():void{
    const password = this.signupForm.get('password')?.value;
    const confirmpassword = this.signupForm.get('confirmpassword')?.value;

    if(password != confirmpassword){
      this.snackBar.open('Passwords do not match','Close', {duration:5000, panelClass:'errorSnackBar'});
      return;
    }
    this.authService.register(this.signupForm.value).subscribe(
      (response) =>{
        this.snackBar.open('Sign Up successfully', 'Close', {duration:5000});
        this.router.navigateByUrl("/login");
      },
      (error) =>{
        this.snackBar.open('Sign Up failed. Please try again','Close', {duration:5000, panelClass:'errorSnackBar'});
      }
    )
  }
}
