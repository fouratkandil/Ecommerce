import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-user-crud',
  templateUrl: './user-crud.component.html',
  styleUrl: './user-crud.component.scss'
})
export class UserCrudComponent {
  users:any;

  constructor(
    private adminSerice:AdminService, 
    private snackbar: MatSnackBar,
  ){}

  ngOnInit():void{
    this.getAllUsers();
  }


  getAllUsers():void{
    this.adminSerice.getAllUsers().subscribe(res=>{
      this.users = res;
    })
  }
  
  deleteUser(userId: any) {
    this.adminSerice.deleteUser(userId).subscribe({
      next: () => {
        this.snackbar.open('User deleted successfully', 'Close', { duration: 5000 });
        this.getAllUsers(); // Refresh user list after successful deletion
      },
      error: (error) => {
        this.snackbar.open('Error deleting user', 'Close', {
          duration: 5000,
          panelClass: "error-snackbar"
        });
        console.error('Error deleting user:', error);
      }
    });
  }
}
