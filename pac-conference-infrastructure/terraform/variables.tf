# definition of variables used in terraform scripts

# kubernetes context to use
variable "kubernetes_context" {
  description = "Kubernetes context to use"
}

# kubernetes cluster to use
variable "kubernetes_cluster" {
  description = "Kubernetes cluster to use"
}

# kubernetes namespace to use
variable "application_namespace" {
  description = "Namespace for application"
}

# kubernates namespace for backend
variable "backend_namespace" {
  description = "Namespace for backend"
}

# kubernates namespace for frontend
variable "frontend_namespace" {
  description = "Namespace for frontend"
}

# namespace for keycloak
variable "keycloak_namespace" {
  description = "Namespace for keycloak"
}

# namespace for neo4j
variable "neo4j_namespace" {
  description = "Namespace for neo4j"
}

# number of neo4j instances
variable "neo4j_standalone" {
  description = "Flag if neo4j has to run in stand alone mode"
  default = false
}

# default persistence volume size for neo4j
variable "neo4j_persistence_volume_size" {
  description = "Persistence volume size for neo4j"
}

variable "conference_host" {
  description = "Host for ingress of conference app."
}