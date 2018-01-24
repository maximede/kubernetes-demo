Build Node : 

docker build -t hello-node:0.0.1 .

Build Java 

./mvnw install dockerfile:build



Deploy :

kubectl apply -f kube/hello-node-deployement.yaml
kubectl apply -f kube/hello-java-deployement.yaml
kubectl apply -f kube/ingress-deployement.yaml


kubectl create configmap my-config --from-literal=MY_VAR="My config map var"

kubectl create secret generic my-secret-config --from-literal=MY_SECRET_VAR="My secret var from kubernetes"

sudo certbot certonly --dns-cloudflare --dns-cloudflare-credentials .cloudflare/cloudflare.ini -d minikube.uw.deravet.eu

kubectl create secret ssl minikube.uw.deravet.eu --from-file=tls.crt=/etc/letsencrypt/live/minikube.uw.deravet.eu/cert.pem --from-file=tls.key=/etc/letsencrypt/live/minikube.uw.deravet.eu/privkey.pem