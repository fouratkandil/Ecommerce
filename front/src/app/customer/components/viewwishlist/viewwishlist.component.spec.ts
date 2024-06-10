import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewwishlistComponent } from './viewwishlist.component';

describe('ViewwishlistComponent', () => {
  let component: ViewwishlistComponent;
  let fixture: ComponentFixture<ViewwishlistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewwishlistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewwishlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
