https://aws.amazon.com/msk/what-is-kafka/
https://docs.aws.amazon.com/msk/latest/developerguide/getting-started.html

https://docs.aws.amazon.com/msk/latest/developerguide/produce-consume.html

https://www.onooks.com/connection-to-msk-using-spring-kafka/

https://stackoverflow.com/questions/59935284/connection-to-msk-using-spring-kafka


Looking at the configuration, you might have to update the brokers and zookeeper connect strings to the values for the Amazon MSK cluster.
Using the aws cli:
aws kafka list-clusters (to get the list of clusters, copy the ClusterArn for the cluster you want to connect to)
aws kafka describe-cluster --cluster-arn <copied cluster arn>|grep Zookeeper (to get the zookeeper connect string)
aws kafka get-bootstrap-brokers --cluster-arn <copied cluster arn> (to get the bootstrap brokers)
