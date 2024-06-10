import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserStorageService } from '../services/storage/user-storage.service';

import { MatIconModule } from '@angular/material/icon'; // Import this line

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  hidePassword: boolean = false;

  constructor(private fb: FormBuilder,
             private snackBar: MatSnackBar,
             private authService: AuthService,
             private router: Router,
            ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  onSubmit(): void {
    const username = this.loginForm.get('email')!.value;
    const password = this.loginForm.get('password')!.value;

    this.authService.login(username, password).subscribe(
      (res) => {
        if (UserStorageService.isAdminLoggedIn()) {
          this.router.navigateByUrl("/admin/dashboard");
        } else if (UserStorageService.isCustomerLoggedIn()) {
          this.router.navigateByUrl("/customer/dashboard");
        }
      },
      (error) => {
        this.snackBar.open('Bad credentials', 'ERROR', { duration: 5000 });
      }
    );
  }
}
