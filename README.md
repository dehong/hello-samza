marmot-samza
===========

This is a learning project base on [Hello Samza](http://samza.apache.org/startup/hello-samza/0.8/).

- To access [Apache Samza](http://samza.apache.org) project.
- To ask questions about [Hello Samza](http://samza.apache.org/startup/hello-samza/0.8/).
- To access [dev list](http://samza.apache.org/community/mailing-lists.html).
- To access [Samza JIRA](https://issues.apache.org/jira/browse/SAMZA).

## Local Running Step

1.Feed Raw Data

```
bin/feed-marmot.sh
```

![](doc/img/feed-demo.png)

2.Report Aggregation

```
bin/report-marmot.sh
```

![](doc/img/report-demo.png)

|RM|NM|
|:--|:--|
|[Nodes of the cluster http://localhost:8088](http://localhost:8088)|[Nodes Information http://10.18.212.212:8042](http://10.18.212.212:8042)|
|![local_rm.png](doc/img/local_rm.png)|![local_nm.png](doc/img/local_nm.png)|

3.Close all
```
bin/done.sh
```
## Dependency Version

|Software|Version|
|:--|:--|
|Samza|0.9|
|Hadoop|2.7.0|
|Kafka|kafka_2.10-0.8.2.1|
|Zookeeper|3.4.6|
|Java|1.8|

## Cluster Running Step
0. [zookeeper cluster](doc/1.zk.md)
0. [kafka cluster](doc/2.kafka.md)
0. [yarn cluster](doc/3.yarn.md)
0. [samza deploy/run/test](doc/4.samza.md)

![samza_ha.png](doc/img/samza_ha.png)

#### yarn cluster
|Server|jps|
|:--|:--|
|yarn1.alibaba.net|6817 NameNode<br/>7172 DFSZKFailoverController<br/>7404 ResourceManager|
|yarn2.alibaba.net|5238 NameNode<br/>5387 DFSZKFailoverController<br/>5650 ResourceManager|
|yarn3.alibaba.net|7594 ResourceManager|
|yarn4.alibaba.net|6643 DataNode<br/>6773 JournalNode<br/>7049 NodeManager|
|yarn5.alibaba.net|6611 DataNode<br/>6742 JournalNode<br/>6969 NodeManager|
|yarn6.alibaba.net|6630 DataNode<br/>6755 JournalNode<br/>6994 NodeManager|

#### zk cluster
- zk1.alibaba.net
- zk2.alibaba.net
- zk3.alibaba.net

#### kafka cluster
- ka1.alibaba.net
- ka2.alibaba.net
- ka3.alibaba.net
