output "public_ip_vm_demo" {
  value = azurerm_windows_virtual_machine.vm_demo.public_ip_address
}
output "public_ip_vm_sql_demo" {
  value = azurerm_windows_virtual_machine.vm_sql_demo.private_ip_address
}