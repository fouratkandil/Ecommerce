import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { UserStorageService } from './storage/user-storage.service'; 

const BASIC_URL = "http://localhost:9090"; 

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor(private http:HttpClient) { }

  getAllProducts(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/customer/products", {
      headers:this.createAuthorizationHeader()
    });
  }
  getAllProduct(): Observable<any> {
    return this.http.get(BASIC_URL+"/api/customer/products"); // No headers needed
  }
  findAllProductsByName(name:any): Observable<any> {
    return this.http.get(BASIC_URL + `/api/customer/search/${name}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  addToCart(productId:any): Observable<any> {
    const cartDto = {
      productId:productId,
      userId:UserStorageService.getUserId()
    }
    return this.http.post(BASIC_URL + "/api/customer/cart",cartDto, {
      headers:this.createAuthorizationHeader()
    });
  }

  increaseProductQuantity(productId:any): Observable<any> {
    const cartDto = {
      productId:productId,
      userId:UserStorageService.getUserId()
    }
    return this.http.post(BASIC_URL + "/api/customer/addition",cartDto, {
      headers:this.createAuthorizationHeader()
    });
  }

  decreaseProductQuantity(productId:any): Observable<any> {
    const cartDto = {
      productId:productId,
      userId:UserStorageService.getUserId()
    }
    return this.http.post(BASIC_URL + "/api/customer/deduction",cartDto, {
      headers:this.createAuthorizationHeader()
    });
  }

  updateUser(userId:any, userCrudDto: any): Observable<any> {
    return this.http.put(BASIC_URL + `/api/admin/user/${userId}`, userCrudDto, {
      headers:this.createAuthorizationHeader()
    });
  }

  getCartByUserId(): Observable<any> {
    const userId = UserStorageService.getUserId();
    return this.http.get(BASIC_URL + `/api/customer/carts/${userId}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  

  

  
  getProductDetailById(productId:number): Observable<any> {
    return this.http.get(BASIC_URL + `/api/customer/product/${productId}`, {
      headers:this.createAuthorizationHeader()
    });
  }


  addProductToWishlist(whishlistDto:any): Observable<any> {
    return this.http.post(BASIC_URL + `/api/customer/wishlist`, whishlistDto,{
      headers:this.createAuthorizationHeader()
    });
  }

  getWishlistByUserId(): Observable<any> {
    const userId = UserStorageService.getUserId();
    return this.http.get(BASIC_URL + `/api/customer/wishlist/${userId}`,{
      headers:this.createAuthorizationHeader()
    });
  }

  

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + UserStorageService.getToken() 
    )
  }
}
