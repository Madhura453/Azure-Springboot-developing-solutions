package com.docker.dockercomposemysqlapplication.notes;

public class NotesOne {
    /*

    dc file = docker compose.yml file

  docker volumes
  ----------------------------
  docker run -d -p 3307:3306 --net networkName --name containerName(mysqldb) --env-file filename -v "/home/madhura/Desktop/db":/var/lib/mysql
  = attach volume to mysql container

  docker restart containerName(docker-compose-mysql-applicationC)
  = restart app after making changes in mysqldb


docker compose commands
---------------------------
docker-compose up --build =    build images from current directory docker-compose.yml file
, it will create images and also it will run containers from that image, also
it will create network for all images in docker.compose.yml file

docker-compose down = it will stop containers, it will remove images, network. in that docker-compose.yml

docker-compose up --scale containername(docker-compose-mysql-applicationC)=5
and also make chane in dc file change     ports:
      - 8080-8999:8080
= it will create 5 containers with random port on 8080-8999

docker-compose up --scale containername(docker-compose-mysql-applicationC)=1
= it will stop previous 4 containers and only one will run



     */
}
