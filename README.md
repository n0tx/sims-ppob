# API Contract SIMS PPOB


## Api-Docs Swagger SIMS PPOB

![image](https://github.com/user-attachments/assets/7eb283da-5c7b-49c7-bf59-0c94272927ed)


## Storage Image Upload (Minio Object Store)

![image](https://github.com/user-attachments/assets/dfc8ec41-dec7-4272-a921-422aab948366)


## ERD Diagram

![image](https://github.com/user-attachments/assets/1ae0b55f-0dee-44c3-9b17-28bee18b86b4)

## Bagaimana Menjalankan Aplikasi

#### 1. **Clone Source Code**

```
git clone https://github.com/n0tx/sims-ppob.git
```

#### 2. **Menjalankan Minio Server**

```
docker-compose up -d
```

**Keterangan**:

```                       
┌──[~/sims-ppob]
└─$ pwd
/home/n0tx/sims-ppob

┌──[~/sims-ppob]
└─$ ls
docker-compose.yml  mvnw  mvnw.cmd  pom.xml  README.md  src

┌──[~/sims-ppob]
└─$ docker-compose up -d                  
Creating network "sims-ppob_minionetwork" with driver "bridge"
Creating sims-ppob_minio_1 ... done
```
