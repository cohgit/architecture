# Kubernetes on local

Estos son los pasos que seguí para poder hacer uso de kubernetes en docker

## Instalación en Docker (Windows)

Habilita kubernetes en Docker Desktop siguiendo los siguientes pasos
[Habilitar Kubernetes sobre Docker](https://docs.docker.com/desktop/kubernetes/)

Habilita Kubernetes Dashboard 
[Web UI Dashboard](https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/)

Acceso para dashboard
[Creación del acceso](https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md)


## kubectl with a proxy
curl -LO -x proxy.novalte.corp:8080 "https://dl.k8s.io/release/v1.24.3/bin/windows/amd64/kubectl.exe"
curl -LO -x proxy.novalte.corp:8080 "https://dl.k8s.io/v1.24.3/bin/windows/amd64/kubectl.exe.sha256"


dashboard-adminuser.yml
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kubernetes-dashboard

apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: admin-user
  namespace: kubernetes-dashboard
```

Aplica el deployment

```
kubectl apply -f dashboard-adminuser.yaml
```

Obtiene token 

```
kubectl -n kubernetes-dashboard create token admin-user
```

