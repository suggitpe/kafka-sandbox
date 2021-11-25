FROM suggitpe/21.10-openjdk11-base

RUN apt-get update && \
    apt-get install -y wget supervisor && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get clean

ENV VERSION 3.6.3
ENV BINARY http://mirrors.ukfast.co.uk/sites/ftp.apache.org/zookeeper/zookeeper-${VERSION}/apache-zookeeper-${VERSION}-bin.tar.gz
ENV ZOOKEEPER_HOME /opt/zookeeper


RUN wget -q -O - ${BINARY} | tar -xzf - -C /opt && \
    echo "Download complete" && ls -alF \opt &&\
    mv /opt/apache-zookeeper-${VERSION}-bin ${ZOOKEEPER_HOME} && \
    echo "Installation complete, contents from ${BINARY} as follows ... " && ls -alF /opt/zookeeper

ADD image-content/zoo.cfg ${ZOOKEEPER_HOME}/conf
ADD image-content/start-zookeeper.sh /usr/bin/start-zookeeper.sh
ADD image-content/zookeeper-supervisord.conf /etc/supervisor/conf.d/
RUN ls -alF ${ZOOKEEPER_HOME}

EXPOSE 2181 2888 3888

WORKDIR /opt/zookeeper

CMD ["supervisord", "-n"]


