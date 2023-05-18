$./config.sh --unattended --url https://dev.azure.com/ORG-CRI-GTI --auth 'PAT' --token '277wptq4c5y2dtcuai6u3pbaurxitqdgzmtxckzf6scn6gi74y7q' --pool 'lnxdesa'
$sudo ./svc.sh install azbacadmin
$sudo ./svc.sh start

$sudo ./svc.sh uninstall azbacadmin
$./config.sh remove --unattended  --auth 'PAT' --token '277wptq4c5y2dtcuai6u3pbaurxitqdgzmtxckzf6scn6gi74y7q'
