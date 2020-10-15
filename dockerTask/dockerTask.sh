#!/bin/bash

mkdir -p nginx/etc
docker pull nginx
docker run -p 8080:80 -v /etc/nginx:$(pwd)/nginx/etc -d --name nginx nginx
echo "I'll stop container in 30s"
sleep 30
docker stop nginx
docker rm nginx
