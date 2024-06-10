import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../../services/storage/user-storage.service';

const BASIC_URL = "http://localhost:9090"; 

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  addCategory(catRequest: any): Observable<any> {
    return this.http.post(BASIC_URL + "/api/admin/category", catRequest, {
      headers:this.createAuthorizationHeader()
    });
  }

  addProduct(productDto: any): Observable<any> {
    return this.http.post(BASIC_URL + "/api/admin/product", productDto, {
      headers:this.createAuthorizationHeader()
    });
  }

  updateProduct(productId:any, productDto: any): Observable<any> {
    return this.http.put(BASIC_URL + `/api/admin/product/${productId}`, productDto, {
      headers:this.createAuthorizationHeader()
    });
  }

  addCoupon(couponDto: any): Observable<any> {
    return this.http.post(BASIC_URL + "/api/admin/coupons", couponDto, {
      headers:this.createAuthorizationHeader()
    });
  }

  getCoupons(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/admin/coupons", {
      headers:this.createAuthorizationHeader()
    });
  }

  getPlacedOrders(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/admin/placeOrders", {
      headers:this.createAuthorizationHeader()
    });
  }

  getAnalytics(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/admin/order/analytics", {
      headers:this.createAuthorizationHeader()
    });
  }

  getAllCategories(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/admin/categories", {
      headers:this.createAuthorizationHeader()
    });
  }

  getAllProducts(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/admin/products", {
      headers:this.createAuthorizationHeader()
    });
  }

  getAllUsers(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/admin/users", {
      headers:this.createAuthorizationHeader()
    });
  }

  changeOrderStatus(orderId:number, status:string): Observable<any> {
    return this.http.get(BASIC_URL + `/api/admin/order/${orderId}/${status}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  findAllProductsByName(name:any): Observable<any> {
    return this.http.get(BASIC_URL + `/api/admin/search/${name}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  getProductById(productId:any): Observable<any> {
    return this.http.get(BASIC_URL + `/api/admin/product/${productId}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  postFAQ(productId:number, faqDto:any): Observable<any> {
    return this.http.post(BASIC_URL + `/api/admin/faq/${productId}`, faqDto, {
      headers:this.createAuthorizationHeader()
    });
  }



  deleteProduct(id: any): Observable<any> {
    return this.http.delete(BASIC_URL + `/api/admin/product/delete/${id}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  deleteUser(id: any): Observable<any> {
    return this.http.delete(BASIC_URL + `/api/admin/user/delete/${id}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + UserStorageService.getToken() 
    )
  }
}
