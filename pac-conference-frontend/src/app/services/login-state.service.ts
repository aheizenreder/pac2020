import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {
  KeycloakEvent,
  KeycloakEventType,
  KeycloakService,
} from "keycloak-angular";

@Injectable({
  providedIn: "root",
})
export class LoginStateService extends Observable<boolean> {
  constructor(private keycloak: KeycloakService) {
    super((observer) => {
      keycloak.isLoggedIn().then((ls) => observer.next(ls));

      keycloak.keycloakEvents$.subscribe((event: KeycloakEvent) => {
        switch (event.type) {
          case KeycloakEventType.OnTokenExpired:
          case KeycloakEventType.OnAuthError:
          case KeycloakEventType.OnAuthLogout:
          case KeycloakEventType.OnAuthRefreshError:
            return observer.next(false);

          case KeycloakEventType.OnAuthSuccess:
          case KeycloakEventType.OnAuthRefreshSuccess:
            return observer.next(true);
        }
      });
    });
  }
}
