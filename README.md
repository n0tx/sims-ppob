![image](https://github.com/user-attachments/assets/8fb6981e-122d-44c1-9d8c-2f6d130bcaac)# API Contract SIMS PPOB


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

#### 3. **Membuat Database dan Import Data Menggunakan pgAdmin4**

**Membuat Database**:

```
Nama Database: simsppob
```

![image](https://github.com/user-attachments/assets/23095696-748c-42c8-8971-a8bc2b9337da)

![image](https://github.com/user-attachments/assets/7d747dcd-f07c-4a89-ae2d-e41a27426ccd)

**Import Data**:

```
SQL Script: sims-ppob/src/main/resources/simsppob.sql
```

#### 4. **Mengatur Spring Active Profile**
**application.properties**:
**application-dev.properties**:
**Keterangan**:
