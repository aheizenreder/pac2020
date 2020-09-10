import { Component } from "@angular/core";
import { KeycloakService } from "keycloak-angular";
import { LoginStateService } from "./services/login-state.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "demo-angular-frontend";
  constructor(
    private keycloakService: KeycloakService,
    public loginState: LoginStateService
  ) {}

  login() {
    this.keycloakService.login();
  }

  logout() {
    this.keycloakService.logout(window.location.origin);
  }
}
