#!/usr/bin/env bash

# No Roles
export TOKEN=$(http --form POST http://localhost:8080/realms/order_manager/protocol/openid-connect/token \
  client_id=order_manager \
  username=test \
  password=test \
  grant_type=password | jq -r .access_token)

# Read
export READ_TOKEN=$(http --form POST http://localhost:8080/realms/order_manager/protocol/openid-connect/token \
  client_id=order_manager \
  username=read \
  password=test \
  grant_type=password | jq -r .access_token)

# Update
export UPDATE_TOKEN=$(http --form POST http://localhost:8080/realms/order_manager/protocol/openid-connect/token \
  client_id=order_manager \
  username=update \
  password=test \
  grant_type=password | jq -r .access_token)

# Admin
export ADMIN_TOKEN=$(http --form POST http://localhost:8080/realms/order_manager/protocol/openid-connect/token \
  client_id=order_manager \
  username=test_admin \
  password=test \
  grant_type=password | jq -r .access_token)
