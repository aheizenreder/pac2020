import { BrowserModule } from "@angular/platform-browser";
import { NgModule, APP_INITIALIZER } from "@angular/core";
import { KeycloakAngularModule, KeycloakService } from "keycloak-angular";
import { initializeKeycloak } from "./app.keycloak";
import { AppComponent } from "./app.component";
import { GreetingComponent } from "./components/greeting/greeting.component";
import { HttpClientModule } from "@angular/common/http";
import { ReactiveFormsModule } from "@angular/forms";
import { ApiModule } from "./services/backend-api/api.module";

@NgModule({
  declarations: [AppComponent, GreetingComponent],
  imports: [
    ApiModule,
    BrowserModule,
    KeycloakAngularModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
