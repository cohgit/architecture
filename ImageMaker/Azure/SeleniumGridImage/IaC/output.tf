output rg_name {
  value       = azurerm_resource_group.rg.name
}

output "public_ip" {
  value = azurerm_public_ip.public_ip.ip_address
}