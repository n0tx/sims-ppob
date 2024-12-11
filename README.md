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

#### 3. **Membuat Database dan Import Data menggunakan pgAdmin4**

**Membuat Database**:

`Nama Database: simsppob`

![image](https://github.com/user-attachments/assets/23095696-748c-42c8-8971-a8bc2b9337da)

![image](https://github.com/user-attachments/assets/7d747dcd-f07c-4a89-ae2d-e41a27426ccd)


  
**Import Data**:

`Lokasi SQL script: sims-ppob/src/main/resources/simsppob.sql`

![image](https://github.com/user-attachments/assets/2d5eca54-b38b-4b42-af97-07237c44e363)

![image](https://github.com/user-attachments/assets/5caa6e5d-80bf-46c9-9825-18b3d6709ca0)

![image](https://github.com/user-attachments/assets/9b028c35-f056-499c-83c9-ed87a8eddeb3)

#### 4. **Mengatur Spring Active Profiles**

**application.properties**:

```
spring.profiles.active=dev
```

**application-dev.properties**:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/simsppob
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=springboot
spring.datasource.password=springboot
```

**Keterangan**:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/{{nama_database}}
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username={{username_database}}
spring.datasource.password={{password_database}}

Sesuaikan dengan pengaturan database anda
```

#### 5. **Menjalankan Aplikasi**

```
./mvnw spring-boot:run
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
└─$ ./mvnw spring-boot:run
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------------< com.riki:sims-ppob >-------------------------
[INFO] Building API Contract SIMS PPOB 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------

--- SKIPPED ---

INFO 11883 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
INFO 11883 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
INFO 11883 --- [  restartedMain] c.r.s.ApiContractSimsPpobApplication     : Started ApiContractSimsPpobApplication in 6.647 seconds (process running
```
