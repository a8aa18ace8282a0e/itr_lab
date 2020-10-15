````ubuntu_vb.json```` - для установки виртуалки для VB
````ubuntu_qemu.json```` - для установки qemu


````scripts/postinstall.sh```` - скрипт для настройки виртуалки сразу после установки, он обновляет пакеты, устанавливает chef, генерирует cookbook, создаёт рецепты для установки nginx, mysql, tomcat, java из задания, и, соответственно, устанавливает их.

должны быть установлены: vagrant, virtualbox, packer (если ничего не забыл)

````bash
git clone https://github.com/a8aa18ace8282a0e/itr_lab
cd itr_lab/vagrantTask
packer build ubuntu_vb.json
````

теперь ожидаем, пока скачается и установится образ, доустановятся необходимые пакеты и будет сгенерирован файл packer_virtualbox-iso_virtualbox.box

добавляем виртуальную машину в вагрант, генерим вагрантфайл, добавляем в него путь к box-файлу

```bash
vagrant box add packer_virtualbox-iso_virtualbox.box
vagrant init
sed -i 's/base/packer_virtualbox-iso_virtualbox.box/g' Vagrantfile
````

запускаем, логинимся по ssh, наслаждаемся
```bash
vagrant up
vagrant ssh
```

по такому же принципу устанавливается виртуальная машина для qemu, только нужно указать соответствующий json-файл
