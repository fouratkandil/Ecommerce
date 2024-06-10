import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPrductDetailComponent } from './view-prduct-detail.component';

describe('ViewPrductDetailComponent', () => {
  let component: ViewPrductDetailComponent;
  let fixture: ComponentFixture<ViewPrductDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewPrductDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewPrductDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
