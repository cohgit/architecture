## Backup de mongodb desde kubernetes

export WORK_NAMESPACE=prod
export WORK_SECRET_NAME=mongo-turtle-db-prod-mongodb
export WORK_SERVICE_MONGO=pod/mfe-turtle-prod-59864cdd66-zxbsw
export MONGODB_ROOT_PASSWORD=$(kubectl get secret --namespace $WORK_NAMESPACE $WORK_SECRET_NAME -o jsonpath="{.data.mongodb-root-password}" | base64 --decode)
kubectl port-forward --namespace $WORK_NAMESPACE $WORK_SERVICE_MONGO 27017:27017 &
mongodump -u root -p $MONGODB_ROOT_PASSWORD -o .