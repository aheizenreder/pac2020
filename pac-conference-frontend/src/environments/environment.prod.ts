import { KeycloakConfig } from "keycloak-angular";

// Add here your keycloak setup infos
const keycloakConfig: KeycloakConfig = {
  url: "https://keycloak.prodyna.com/auth/",
  realm: "demo",
  clientId: "angular-frontend",
};

export const environment = {
  production: true,
  keycloakConfig,
};
