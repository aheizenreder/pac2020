# deployment of neo4j
# by helm
resource "kubernetes_namespace" "neo4j" {
  metadata {
    name = var.neo4j_namespace
  }
}
# definition a random password
resource "random_password" "neo4j" {
  length = 12
  special = true
  override_special = "_%@"
}

resource "kubernetes_secret" "neo4j-access" {
  metadata {
    name = "neo4j-access"
    namespace = kubernetes_namespace.neo4j.metadata[0].name
  }

  data = {
    "password" = random_password.neo4j.result
  }
}

# definition of neo4j deployment
resource "helm_release" "neo4j" {
  name = "neo4j"
  chart = "https://github.com/neo4j-contrib/neo4j-helm/releases/download/4.1.0-1/neo4j-4.1.0-1.tgz"

  #set namespace
  namespace = kubernetes_namespace.neo4j.metadata[0].name

  values = [
    file("helm/neo4j.yaml")
  ]

  # reduce volume size to 1Gi
  set {
    name = "core.persistentVolume.size"
    value = var.neo4j_persistence_volume_size
  }

  # set standalone
  set {
    name = "core.standalone"
    value = var.neo4j_standalone
  }

  # password
  set {
    name = "neo4jPassword"
    value = random_password.neo4j.result
  }

  # set read replicas autoscaling
  set {
    name = "readReplica.autoscaling.enabled"
    value = true
  }
}

resource "kubernetes_ingress" "neo4j_ingress" {
  metadata {
    name = "neo4j"
    namespace = kubernetes_namespace.neo4j.metadata[0].name
    annotations = {
      "nginx.ingress.kubernetes.io/rewrite-target" = "/"
    }
    labels = {
      app = "neo4j"
    }
  }
  spec {
    rule {
      host = "neo4j.minikube"
      http {
        path {
          path = "/"
          backend {
            service_name = "neo4j-neo4j"
            service_port = "http"
          }
        }
      }
    }
  }
}