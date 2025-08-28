FROM quay.io/keycloak/keycloak:latest
COPY realm-export.json /opt/keycloak/data/import/
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start-dev", "--http-port=8888", "--import-realm"]