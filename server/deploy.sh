#!/bin/bash

IS_GREEN=$(docker ps --filter "name=^green$" --format "{{.Names}}")
DEFAULT_CONF=" /etc/nginx/nginx.conf"

if [ "$IS_GREEN" != "green"  ];then

  echo "### BLUE => GREEN ####"

  echo "1. get green image"
  docker-compose -p hapjuhasil pull green # green으로 이미지를 내려받습니다.

  echo "2. green container up"
  docker-compose -p hapjuhasil up -d green # green 컨테이너 실행

  while [ 1 = 1 ]; do
  echo "3. green health check..."
  sleep 3

  REQUEST=$(curl http://127.0.0.1:8081) # green으로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
            echo "health check success"
            break ;
            fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.green.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. blue container down"
  docker-compose -p hapjuhasil stop blue
else
  echo "### GREEN => BLUE ###"

  echo "1. get blue image"
  docker-compose -p hapjuhasil pull blue

  echo "2. blue container up"
  docker-compose -p hapjuhasil up -d blue

  while [ 1 = 1 ]; do
    echo "3. blue health check..."
    sleep 3
    REQUEST=$(curl http://127.0.0.1:8080) # blue로 request

    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break ;
    fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.blue.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. green container down"
  docker-compose -p hapjuhasil stop green
fi