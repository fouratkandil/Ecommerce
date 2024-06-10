import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostProductFAQComponent } from './post-product-faq.component';

describe('PostProductFAQComponent', () => {
  let component: PostProductFAQComponent;
  let fixture: ComponentFixture<PostProductFAQComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PostProductFAQComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PostProductFAQComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
