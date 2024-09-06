# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.3/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.3/gradle-plugin/packaging-oci-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#web)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)



## 데이터 베이스 설정 정보

### 사용자 테이블 스페이스 설정 정보

1) 아래 폴더 생성

   >D:\darackbang\darackbangdb


2) 인텔리제이 데이터베이스 설정에

   관리자 로그인

   >사용자명:system

   >패스워드: 본인이 설정한 패스워드

   접속후 sql 콘솔 오픈후 3~5작업을 한다.


3) 데이플 스페이스 생성

   >CREATE TABLESPACE darackbangdb
   DATAFILE 'D:\darackbang\darackbangdb\darackbangdb.dbf'
   SIZE 10M AUTOEXTEND ON NEXT 5M MAXSIZE 50M;

4) 사용자 계정에서 # 제거
   >alter session set "_ORACLE_SCRIPT"=true;

5) 사용자 계정 설정

   >CREATE USER darackbangadmin
   IDENTIFIED BY darackbangadmin
   DEFAULT TABLESPACE darackbangdb TEMPORARY TABLESPACE temp;

   > GRANT CONNECT, RESOURCE, DBA TO darackbangadmin;

   >GRANT CREATE VIEW, CREATE SYNONYM TO darackbangadmin;

   >GRANT UNLIMITED TABLESPACE TO darackbangadmin;

   >ALTER USER darackbangadmin ACCOUNT UNLOCK;

