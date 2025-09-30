FROM quay.io/keycloak/keycloak:26.3.3

COPY ./realm-export.json /opt/keycloak/data/import/realm-export.json

ENV KC_HTTP_PORT=8888
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false
ENV KC_BOOTSTRAP_ADMIN_USERNAME=admin
ENV KC_BOOTSTRAP_ADMIN_PASSWORD=admin
ENV KC_HEALTH_ENABLED=true

EXPOSE 8888

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]