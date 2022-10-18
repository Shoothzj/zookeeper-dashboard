FROM shoothzj/base:jdk17

ENV STATIC_PATH /opt/zookeeper-dashboard/static/

COPY dist /opt/zookeeper-dashboard

WORKDIR /opt/zookeeper-dashboard

EXPOSE 10002

CMD ["/usr/bin/dumb-init", "java", "-jar", "/opt/zookeeper-dashboard/zookeeper-dashboard"]
