FROM ubuntu:bionic
ENV TZ=Europe/Minsk
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt update && apt upgrade -y && apt -y install apache2 ntp openjdk-11-jdk
WORKDIR /var/www/site
EXPOSE 80
CMD /usr/sbin/apache2ctl -D FOREGROUND
