locals {
  db_name = "keycloak"
  db_user = "keycloak"
}

resource "kubernetes_namespace" "keycloak" {
  metadata {
    name = var.keycloak_namespace
  }
}

resource "random_password" "mariadb-password" {
  length = 16
  special = false
}

resource "kubernetes_secret" "mariadb-access" {
  metadata {
    name = "keycloak-mariadb-access"
    namespace = kubernetes_namespace.keycloak.metadata[0].name
  }

  data = {
    "username" = local.db_user
    "database" = local.db_name
    "password" = random_password.mariadb-password.result
  }
}

resource "helm_release" "mariadb" {
  chart = "mariadb"
  namespace = kubernetes_namespace.keycloak.metadata[0].name
  name = "keycloak-mariadb"
  repository = local.helm_repository_bitnami

  values = [
    file("helm/mariadb.yaml")
  ]

  set {
    name = "db.user"
    value = local.db_user
  }

  set {
    name = "db.name"
    value = local.db_name
  }

  set {
    name = "db.password"
    value = random_password.mariadb-password.result
  }
}

resource "random_password" "keycloak-user" {
  length = 16
  special = false
}

resource "kubernetes_secret" "keycloak-user" {
  metadata {
    name = "keycloak-user"
    namespace = kubernetes_namespace.keycloak.metadata[0].name
  }

  data = {
    "password" = random_password.keycloak-user.result
  }
}

resource "kubernetes_secret" "keycloak-realm-local" {
  metadata {
    name = "keycloak-realm-local"
    namespace = kubernetes_namespace.keycloak.metadata[0].name
  }

  data = {
    "realm-local.json" = file("realm/realm-local.json")
  }
}

resource "helm_release" "keycloak" {
  depends_on = [
    helm_release.mariadb
  ]

  chart = "keycloak"
  name = "keycloak"
  version = "8.3.0"
  namespace = kubernetes_namespace.keycloak.metadata[0].name
  repository = local.helm_repository_codecentric

  values = [
    file("helm/keycloak.yaml")
  ]

  set {
    name = "keycloak.persistence.dbName"
    value = local.db_name
  }
}
