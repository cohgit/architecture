variable rg_name {
  type        = string
  default     = ""
  description = "Nombre del Grupo de Recursos"
}
variable rg_location {
  type        = string
  default     = ""
  description = "Ubicaciópn del Grupo de Recursos"
}

variable tags {
  description = "etiquetas de los recursos"
}

variable app_name {
  type        = string
  description = "nombre de app que se usará para packer"
}

variable pass_length {
  description = "largo de la password"
}

variable pass_special {
  description = "caracteres especiales"
}

variable pass_override_special {
  type        = string
  description = "caracteres especiales"
}

