# kafka-span-receiver

Ce projet permet de récuperer les traces en provenance d'un conteneur BWCE et de les afficher
dans un front VueJS en passant par un collector jaeger (et un server prometheus) un kafka en guise de buffer pour être 
pour ensuite récuperer dans un backend Java Spring.

### @Authors

| Prénom   | Nom        |           Job          |  Last Update  |
|----------|------------|------------------------|---------------|
| Mohammed | EL ASSOURI | Développeur Full Stack | 21/04/2020    |

### Prérequis
- [docker](https://www.docker.com/products/docker-desktop)
- [mongodb](https://www.mongodb.com/download-center/community)
- [zookeeper](http://zookeeper.apache.org/releases.html)
- [kafka](http://kafka.apache.org/downloads.html)
- [sdk 1.8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- [jre 1.8](https://www.oracle.com/java/technologies/javase-jre8-downloads.html)
- [tibco bwce studio](https://www.tibco.com/resources/product-download/tibco-businessworks-container-edition-trial-download-businessstudio)
- [bwce-runtime](https://edelivery.tibco.com/storefront/eval/tibco-businessworks-container-edition/prod11654.html)
- [jaeger collector](https://www.jaegertracing.io/docs/1.17/deployment/#kafka)
- [prometheus-jar](https://github.com/TIBCOSoftware/bw-tooling/tree/master/prometheus-integration)
- [prometheus-kafka-adapter](https://github.com/Telefonica/prometheus-kafka-adapter)

### Zookeeper Config

Dans le repertoir **C:\zookeeper-version\conf** remplacer:
   
-  **zoo_sample.cfg** par **zoo.cfg**

Remplacer dans **zoo.cfg**: 

-  **dataDir=/tmp/zookeeper** par **C:\zookeeper-version\bin**

Dans les varriables d'environnement ajouter: 
-   **ZOOKEEPER_HOME**=C:\zookeeper-version

-   **PATH** => %ZOOKEEPER_HOME%\bin

Zookeeper est maitenant disponible dans un terminal:   
-       zkserver

### Kafka Config

Dans le repertoir **C:\kafka_version\config** remplacer dans **server.properties**:
**log.dirs=/tmp/kafka-logs** par **log.dir= C:\kafka_version\kafka-logs** 

Kafka est maitenant disponible dans un terminal dans  le repertoir **C:\kafka_version**:  

-       .\bin\windows\kafka-server-start.bat .\config\server.properties

### Tibco BusinessWork Container Edition 

Installer le studio 
        
- [TIBCO Business Studio Container Edition](https://www.tibco.com/resources/product-download/tibco-businessworks-container-edition-trial-download-businessstudio) 

Télécharger le runtime docker avec version correspondant à la version utilisée

-   [bwce-runtime-version.zip](https://edelivery.tibco.com/storefront/eval/tibco-businessworks-container-edition/prod11654.html)


Copier l'archive dans avec version correspondant à la version utilisée

-   **C:\Program Files\tibco\bwce\bwce\version\docker\resources\bwce-runtime**

Télécharger le jar prometheus 
-   [prometheus-jar](https://github.com/TIBCOSoftware/bw-tooling/tree/master/prometheus-integration)

Copier le jar dans 
-   **C:\Program Files\tibco\bwce\bwce\2.5\docker\resources\addons\jars**

Se placer dans le repertoire **C:\Program Files\tibco\bwce\bwce\2.5\docker** et construire l'image tibco
-       docker build -t tibco/bwce:latest .


### HelloWorld BWCE 
-   Creer un network docker 

-       docker network create -d bridge monitoring-net

##### Jaeger Collector Config
-   Creer un jaeger-collector avec ${KAFKA} correspondant a l'adresse du broker kafka

-       docker run -d --network monitoring-net --rm --name jaeger-collector -e SPAN_STORAGE_TYPE=kafka -e KAFKA_PRODUCER_BROKERS=${KAFKA} -e KAFKA_PRODUCER_ENCODING=json jaegertracing/jaeger-collector:latest --log-level=debug

##### Kafka Prometheus Adapter Config 
-   Créer un prometheus-kafka-adapter avec ${KAFKA} correspondant a l'adresse du broker kafka

-       docker run -d --network monitoring-net --rm --name prometheus-kafka-adapter -e KAFKA_BROKER_LIST=${KAFKA} telefonica/prometheus-kafka-adapter:1.6.0

##### Prometheus Config
-   Créer un serveur prometheus avec ${PATH} correspondant au chemin vers le fichier de config **prometheus.yml** (dans le repertoire docker du projet)      
-       docker run -d --network monitoring-net --rm --name prometheus -p 9090:9090 -v ${PATH}\prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml

##### HelloWorld BWCE
-   Se placer dans le repertoire **docker** à la racine du **projet**  **kafka-span-receiver** et construire l'image

-       docker build -t bwce/helloworld:latest . 

-   Créer un conteneur **helloworld**

-       docker run -d --rm --name helloworld -p 7575:8080 -p 9095:9095 –e BW_PROMETHEUS_ENABLE=true –e BW_JAVA_OPTS=”-Dbw.engine.opentracing.enable=true” –e JAEGER_ENDPOINT=http://jaeger-collector:14268/api/traces -e JAEGER_SAMPLER_TYPE=const -e JAEGER_SAMPLER_PARAM=1 bwce/helloworld:latest


-   Connecter le conteneur 

-       docker network connect monitoring-net helloworld

### MongoDB

Installer un serveur mongoDB

-   [MongoDB](https://www.mongodb.com/download-center/community)

-   [UI MongoDB](https://robomongo.org/) (Optionnel)

Créer une base de données et la nommer

-       kafka 


### kafka span receiver
-   démarrer le back
-   démarrer le front 

Le projet comprend un **consumer kafka** et une **websocket**. Pour chaque insert en base de données 
le backend envoie le message "update" à travers la websocket et informe le front que des nouvelles 
données sont à récuperer. Le front affiche les **Traces** en provenance du **helloworld** dans un unique composant.
