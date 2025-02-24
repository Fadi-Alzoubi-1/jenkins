docker network create mysql-network

docker run --name mysql-server --network mysql-network -e MYSQL_ROOT_PASSWORD=root -d mysql:8

docker run --rm -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix --network mysql-network mysql-workbench
