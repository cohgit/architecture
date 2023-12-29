output "public_ip_vm_demo" {
  value = azurerm_windows_virtual_machine.vm_demo.public_ip_address
}