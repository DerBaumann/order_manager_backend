#!/usr/bin/env bash

TOKEN=$(http --form POST http://localhost:8080/realms/order_manager/protocol/openid-connect/token \
  client_id=order_manager_api \
  username=test \
  password=test \
  grant_type=password | jq -r .access_token)