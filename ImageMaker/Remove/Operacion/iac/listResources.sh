
#for region in $(aws ec2 describe-regions --query 'Regions[].RegionName' --output text); do
  #echo "Buscando en la región: $region"
  #aws resourcegroupstaggingapi get-resources --output text --query 'ResourceTagMappingList[?!not_null(Tags[?Key == `proyecto`].Value)].ResourceARN' --region $region
  aws resource-explorer-2 search --query-string="tag:none" --output text
#done
