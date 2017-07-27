FROM java:8-alpine



ENV APP_RUN /var/run/app

ENV HTTP_PORT 8083

ENV DEBUG_PORT 8777

RUN mkdir -p ${APP_RUN}

COPY policy ${APP_RUN}

RUN chmod 755 ${APP_RUN}/*.sh

EXPOSE ${HTTP_PORT} ${DEBUG_PORT}

CMD ${APP_RUN}/run.sh
