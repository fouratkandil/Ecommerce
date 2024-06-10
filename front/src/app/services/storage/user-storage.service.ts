import { Injectable } from '@angular/core';

const TOKEN = "eco-token";
const USER = "eco-user";

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }

  public saveToken(token: string): void {
    localStorage.removeItem(TOKEN);
    localStorage.setItem(TOKEN, token);
  }

  public saveUser(user): void {
    localStorage.removeItem(USER);
    localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken(): string {
    return window.localStorage.getItem(TOKEN);
  }

  static getUser(): any | null {
    try {
      const userData = window.localStorage.getItem(USER);
      return userData ? JSON.parse(userData) : null;
    } catch (error) {
      console.error('Error parsing user data:', error);
      return null;
    }
  }
  

  static getUserId(): string {
    const user = this.getUser();
    if (user == null) {
      return '';
    }
    return user.id;
  }

  static getUserRole(): string {
    const user = this.getUser();
    if (user == null) {
      return '';
    }
    return user.role;
  }

  static isAdminLoggedIn(): boolean {
    const token = UserStorageService.getToken();
    if (token === null) {
      return false;
    }
    const role: string = UserStorageService.getUserRole();
    return role == "ADMIN";
  }

  static isCustomerLoggedIn(): boolean {
    const token = UserStorageService.getToken();
    if (token === null) {
      return false;
    }
    const role: string = UserStorageService.getUserRole();
    return role == "CUSTOMER";
  }

   static signOut(): boolean {
    try {
      window.localStorage.removeItem(TOKEN);
      window.localStorage.removeItem(USER);
      return true;
    } catch (error) {
      console.error('Error signing out:', error);
      return false;
    }
  }
}
