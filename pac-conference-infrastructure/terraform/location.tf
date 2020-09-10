
resource "kubernetes_secret" "location-neo4j-access" {
  metadata {
    name = "location-neo4j-access"
    namespace = kubernetes_namespace.backend.metadata[0].name
  }

  data = {
    "username" = "neo4j"
    "password" = random_password.neo4j.result
  }
}

resource "helm_release" "location" {
  chart = "./helm/location"
  name = "location"
  namespace = kubernetes_namespace.backend.metadata[0].name

  depends_on = [
    helm_release.neo4j,
    helm_release.keycloak
  ]

  values = [
    file("helm/location.yaml")
  ]

  set {
    name = "ingress.hosts[0].host"
    value = var.conference_host
  }

}