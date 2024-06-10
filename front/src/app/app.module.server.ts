import { NgModule } from '@angular/core';
import { ServerModule } from '@angular/platform-server';

import { APP_BASE_HREF } from '@angular/common';
import { provideClientHydration } from '@angular/platform-browser';

import { AppModule } from './app.module';
import { AppComponent } from './app.component';

@NgModule({
  imports: [
    AppModule,
    ServerModule,
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' },
    provideClientHydration(), // Add this line to include provideClientHydration()
  ],
})
export class AppServerModule {}
