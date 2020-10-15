date > /etc/vagrant_box_build_time

apt-get -y update
apt-get -y upgrade
apt-get -y install vim curl git
apt-get clean

curl -L https://www.opscode.com/chef/install.sh | sudo bash

apt-get -y install dkms
VBOX_VERSION=$(cat /home/vagrant/.vbox_version)
cd /tmp
wget http://download.virtualbox.org/virtualbox/$VBOX_VERSION/VBoxGuestAdditions_$VBOX_VERSION.iso
mount -o loop VBoxGuestAdditions_$VBOX_VERSION.iso /mnt
sh /mnt/VBoxLinuxAdditions.run
umount /mnt

rm VBoxGuestAdditions_$VBOX_VERSION.iso

wget https://packages.chef.io/files/stable/chefdk/1.3.43/ubuntu/16.04/chefdk_1.3.43-1_amd64.deb
dpkg -i chefdk_1.3.43-1_amd64.deb
mkdir cookbooks && cd cookbooks/
chef generate cookbook lemtj
echo "package 'nginx' do action :install end" >> recipes/default.rb
echo "package 'tomcat8' do action :install end" >> recipes/default.rb
echo "package 'mysql-client' do action :install end" >> recipes/default.rb
echo "package 'mysql-server' do action :install end" >> recipes/default.rb
echo "package 'openjdk-11-jdk' do action :install end" >> recipes/default.rb

chef-client -z --runlist "lemtj"

groupadd -r sudo
usermod -a -G sudo vagrant
cp /etc/sudoers /etc/sudoers.orig
sed -i -e '/Defaults\s\+env_reset/a Defaults\texempt_group=sudo' /etc/sudoers
sed -i -e 's/%sudo ALL=(ALL) ALL/%sudo ALL=NOPASSWD:ALL/g' /etc/sudoers

mkdir /home/vagrant/.ssh
chmod 700 /home/vagrant/.ssh
cd /home/vagrant/.ssh
wget --no-check-certificate 'https://raw.github.com/mitchellh/vagrant/master/keys/vagrant.pub' -O authorized_keys
chmod 600 /home/vagrant/.ssh/authorized_keys
chown -R vagrant /home/vagrant/.ssh

apt-get -y autoremove

exit
