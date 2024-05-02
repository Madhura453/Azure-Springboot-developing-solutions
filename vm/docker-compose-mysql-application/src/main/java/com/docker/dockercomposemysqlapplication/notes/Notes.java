package com.docker.dockercomposemysqlapplication.notes;

public class Notes {
    /*
     running mysql docker container
 ----------------------------------------------------------------
 1) https://hub.docker.com/_/mysql
   run command for mysql
   docker run -p 3306:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=test mysql
   docker logs mysqldb= to see the logs
   .=current directory   , anywhere tag-name optional by default it is latest

   run command for spring boot
   --------------
   docker build -t imagename:tagname .    = creating image

   docker run -p 8080:8080 --name containerName -e MYSQL_HOST=mysqldb -e MYSQL_PORT=3306 -e MYSQL_USER=root  MYSQL_PASSWORD=root image-name
   = run container based on image

    docker run -p 8080:8080 --name docker-compose-mysql-applicationC -e MYSQL_HOST=mysqldb -e MYSQL_PORT=3306 -e MYSQL_USER=root  MYSQL_PASSWORD=root docker-compose-mysql-applicationim

  the above command will failed because mysqldb and springboot need to be in same network

  So network configuration need to do

  docker network ls  = docker network list

  docker network create networkname   = create network

  docker network connect networkname containername  = attach network to container

  docker container inspect containername
       = we can see all information of container . also see whether network connected to container or not

 attach network to spring boot application

 docker run -p 8080:8080 --name containername  -- net networkname -e MYSQL_HOST=mysqldb
 -e MYSQL_PORT=3306 -e MYSQL_USER=root MYSQL_PASSWORD=root imagename


setting environments with file

docker run -p 8080:8080 --name containername --net networkname --env-file filename imagename

--env-file= environments file name


     */
}
