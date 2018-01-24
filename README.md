# Kubernetes Demo

This repo contains 2 Hello world applications one built in java, the other built with Node

the kube repository contains the kubernetes deployment descriptors to deploy the apps and make them available

# Prerequisite
You'll need to install : 
* docker ( `brew cask install docker` on MacOS)
* kubectl (`brew install kubernetes-cli` on MacOS )
* gcloud (`brew cask install google-cloud-sdk` on MacOS)

# Building apps and docker images

## Build the Node application : 

in the `hello-node` directory, run :

```
docker build -t hello-node:0.0.2 .
```

you should now be able to run `docker run --rm -p 8080:8080 hello-node:0.0.2` and `curl {dockerIp}:8080` should respond with something like this :

```
<html>
<body>
Hello Node !<br/>
Pod name is undefined<br/>
Env Variable is : undefined<br/>
Env secret Variable is : undefined<br/>
</body>
</html>

```


## Build the Java application 


in the `hello-java` directory, run :
```
./mvnw install dockerfile:build
```

This will compile the application and build an image with the version matching the version in `pom.xml`


you should now be able to run `docker run --rm -p 8080:8080 maximede/hello-java:0.1.2` and `curl {dockerIp}:8080` should respond with something like this :

```
<html><body>Hello Java! <br/>
Pod name is
<br/>Env Variable is my default variable <br/>
secret is my default secret variable%

```


# Deploying to a kubernetes cluster

## Init gcloud 

```
gcloud init
```



## Tag and push the images using gcr registry

```
docker tag hello-node:0.0.2 gcr.io/test-kubernetes-maximede/hello-node:0.0.2
gcloud docker -- push us.gcr.io/test-kubernetes-maximede/hello-node:0.0.2

docker tag maximede/hello-java:0.1.2 gcr.io/test-kubernetes-maximede/hello-java:0.1.2
gcloud docker -- push gcr.io/test-kubernetes-maximede/hello-java:0.1.2
```


## Create cluster 

### gcloud

1. Create the cluster
```
gcloud container clusters  create dev-cop-cluster
```

2. Configure authentication 
```
gcloud auth application-default login
```

3. Create a gcloud compute adress to allow the ingress controller to be exposed on a fixed ip
```
gcloud compute addresses create kubernetes-ingress --global
```

4. Connect to the kubernetes dashboard
* run `kubectl proxy`
* go to http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/
* You can skip authentiaction as you are connecting through the proxy



### Minikube (https://github.com/kubernetes/minikube)

1. Install minikube ( on MacOS)
```
brew cask install minikube
```

2. Start minikube
```
minikube start
```

3. (Optional) Connect to the docker daemon running on minikube
```
eval $(minikube docker-env)
```

4. Configure minikube permissions to access google cloud registry
see https://ryaneschinger.com/blog/using-google-container-registry-gcr-with-minikube/

5. Connect to the kubernetes dashboard

```
minikube dashboard
```


## Secrets

### Create new certificate Using letsencrypt 

```
sudo certbot certonly --dns-cloudflare --dns-cloudflare-credentials .cloudflare/cloudflare.ini -d minikube.uw.deravet.eu
```

### Deploy secretes to kubernetes cluster

```
kubectl create configmap my-config --from-literal=MY_VAR="My config map var"

kubectl create secret generic my-secret-config --from-literal=MY_SECRET_VAR="My secret var from kubernetes"

kubectl create secret tls minikube --cert /etc/letsencrypt/live/minikube.uw.deravet.eu/cert.pem --key /etc/letsencrypt/live/minikube.uw.deravet.eu/privkey.pem
```


##Deploy Apps to kubernetes cluster :

```
kubectl apply -f kube/hello-node-deployement.yaml
kubectl apply -f kube/hello-java-deployement.yaml
kubectl apply -f kube/ingress-deployement.yaml
```



