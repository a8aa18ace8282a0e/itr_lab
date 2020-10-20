dockerTask.sh - скрипт выполняет первую часть задания, запускается от рута

загружает образ с nginx'ом с хаба, создаёт необходимые директории, запускает контейнер с пробросом 80-го порта и монтированием созданной директории в контейнер и даёт 30 секунд на проверку работоспособности в браузере, после чего останавливает и удаляет контейнер.

```bash
sudo chmod +x dockerTask.sh
sudo ./dockerTask.sh
```

или

```bash
sudo sh dockerTask.sh
```


Dockerfile описывает, как собирать контейнер на основе образа ubuntu:bionic с установленными пакетами apache2, ntp и jdk

собирается командой:

```
sudo docker build -t bxvsscdc/apache2 .
```

запуск:

```
sudo docker run -p 8081:80 --name apache bxvsscdc/apache2
```

после чего, перейдя по ссылке http://localhost:8081 мы увидим дефолтную страницу апача

ссылка на репозиторий и докерхабе: https://hub.docker.com/repository/docker/bxvsscdc/apache2
