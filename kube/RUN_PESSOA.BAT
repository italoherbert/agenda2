kubectl delete -f agenda-pessoa.yaml
kubectl delete -f agenda-pessoa-db.yaml

kubectl apply -f agenda-pessoa-db.yaml

kubectl describe service agenda-pessoa-db
