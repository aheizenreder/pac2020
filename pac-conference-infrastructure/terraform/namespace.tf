# definition of kubernetes namespace for
# pac conference application
resource "kubernetes_namespace" "conference" {
  metadata {
    name = var.application_namespace
  }
}

# definition of kubernetes namespace for
# conference backend
resource "kubernetes_namespace" "backend" {
  metadata {
    name = var.backend_namespace
  }
}

# definition of kubernetes namespace for
# conference frontend
resource "kubernetes_namespace" "frontend" {
  metadata {
    name = var.frontend_namespace
  }
}