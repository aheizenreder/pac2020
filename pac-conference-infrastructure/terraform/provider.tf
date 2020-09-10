# definition of kubernetes cluster to use.
# the information to authentication
# and name of cluster.
provider "kubernetes" {
  config_context_auth_info = var.kubernetes_context
  config_context_cluster = var.kubernetes_cluster
}
# random provider for generation of passwords and secrets
provider "random" {

}

# helm provider
provider "helm" {
    kubernetes {
      config_context = var.kubernetes_context
    }
}
