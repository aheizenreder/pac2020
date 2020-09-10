import { KeycloakAuthGuard, KeycloakService } from "keycloak-angular";
import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from "@angular/router";
import { environment } from "../environments/environment";

export function initializeKeycloak(
  keycloak: KeycloakService
): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      const { keycloakConfig } = environment;
      try {
        await keycloak.init({
          config: keycloakConfig,
          initOptions: {
            checkLoginIframe: true,
            onLoad: "check-sso",
          },
          bearerExcludedUrls: [],
        });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
  };
}

@Injectable({
  providedIn: "root",
})
export class AppAuthGuard extends KeycloakAuthGuard implements CanActivate {
  constructor(
    protected router: Router,
    protected keycloakAngular: KeycloakService
  ) {
    super(router, keycloakAngular);
  }

  isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {
    return new Promise((resolve, reject) => {
      const requiredRoles: string[] = route.data.roles;

      if (!requiredRoles || requiredRoles.length === 0) {
        return resolve(true);
      }

      if (!this.authenticated) {
        this.keycloakAngular.login().catch((e) => console.error(e));
        return reject(false);
      }

      if (!this.roles || this.roles.length === 0) {
        return resolve(false);
      }

      // check if the user has access to all required roles.
      // reminder: this.roles => roles of the user
      resolve(requiredRoles.every((role) => this.roles.indexOf(role) > -1));
    });
  }
}
