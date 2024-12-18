# Cloud-Box  

Cloud-Box는 파일 및 폴더 관리 웹 애플리케이션입니다. 사용자는 폴더를 생성하고, 파일을 업로드, 다운로드, 삭제, 공유할 수 있으며, 폴더 및 파일의 위치를 변경할 수 있습니다. AWS S3와 연동하여 파일을 안전하게 저장하며, Spring Security를 이용한 권한 기반 접근 제어로 보안을 강화하였습니다. React와 Spring Boot를 기반으로 개발되었습니다.  


## 팀원 및 담당 파트  

| 팀원       | 역할          | 담당 파트                                              | 주요 기여 사항                                                                                     |
|------------|---------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| **iju42829** | 백엔드 개발 | 프로젝트 설계, 백엔드 개발, AWS 클라우드 관리 및 배포       | Spring Boot와 JPA를 사용한 RESTful API 설계 및 구현, Swagger 문서화, AWS S3/EC2/VPC 설정, Docker 배포 |
| **solar**    | 프론트엔드 개발 | 사용자 인터페이스 개발                                  | React와 Axios를 사용한 클라이언트 개발, 반응형 디자인 구현                                           |
| **DuoMine**  | 프론트엔드 개발 | 사용자 인터페이스 개발                                  | React와 Axios를 사용한 클라이언트 개발, 반응형 디자인 구현                                           |
| **kmtaegyu** | 백엔드 개발   | Nginx 리버스 프록시 설정 및 RDS 설정                    | Nginx 리버스 프록시 설정, AWS RDS 설정                                            |


## 주요 기능  

### **폴더 및 파일 관리**  
1. **폴더 생성**: 사용자가 파일 정리를 위해 폴더를 생성할 수 있습니다.  
2. **파일 업로드 및 다운로드**: AWS S3를 통해 파일 업로드 및 다운로드를 제공합니다.  
3. **파일 및 폴더 삭제**: 필요하지 않은 파일 및 폴더를 삭제할 수 있습니다.  
4. **폴더 및 파일 위치 변경**: 폴더 및 파일을 원하는 위치로 이동할 수 있습니다.  
5. **파일 공유**: 생성된 파일을 다른 사용자와 공유할 수 있습니다.  

### **보안**  
- **권한 기반 접근 제어**: Spring Security를 활용하여 사용자 인증 및 권한 기반 파일/폴더 접근 제한을 제공합니다.  


## 기술 스택  

### **프론트엔드**  
- **React**: 사용자 인터페이스 개발.  
- **Axios**: 서버와의 API 통신.  

### **백엔드**  
- **Spring Boot**: RESTful API 서버 개발.  
- **JPA**: 객체와 데이터베이스 간 매핑을 관리하는 ORM 도구.
- **PostgreSQL**: 데이터 저장 및 관리에 사용.  
- **Swagger**: RESTful API 문서화.  

### **AWS**  
- **AWS S3**: 파일 저장소로 사용.  
- **AWS RDS**: 파일 메타데이터 및 사용자 정보를 저장하는 관계형 데이터베이스.  
- **AWS EC2**: React 및 Spring Boot 서버 실행.  
- **AWS VPC**: 외부에서 RDS에 접근하지 못하도록 네트워크 격리.  

### **배포 및 네트워크**  
- **Docker**: AWS EC2에 Spring Boot를 컨테이너화하여 배포.  
- **Nginx**: 리버스 프록시를 통해 React와 Spring Boot 요청 분리. 

## 실행 방법  

아래 단계를 따라 프로젝트를 실행하세요.

### 0. AWS 설정  
- AWS EC2, RDS, S3가 필요합니다.  

### 1. **설치**  
- 프로젝트를 클론하고 디렉토리로 이동합니다.  

```bash
# 프로젝트 클론
git clone https://github.com/iju42829/jbnu-cloud-computing.git

# 프로젝트 디렉토리로 이동
cd jbnu-cloud-computing

```

### 2. EC2에 Docker 엔진 및 Docker Compose 설치

#### Docker 엔진 설치

Docker 엔진은 [공식 설치 가이드](https://docs.docker.com/engine/install/ubuntu/)를 참고하여 설치하세요.

#### Docker Compose 설치

아래 명령어를 실행하여 Docker Compose를 설치합니다.

```bash
sudo apt update
sudo apt install docker-compose
```

### 3. .env 파일 설정

애플리케이션 실행을 위해 `.env` 파일을 생성하고 아래 내용을 작성하세요. 각 항목은 실제 환경에 맞게 작성해야 합니다.


#### Spring Boot `.env` 파일 설정

```plaintext
# 서버 설정
SERVER_DOMAIN=                            # 백엔드 서버 도메인
SERVER_PORT=8080                          # 백엔드 서버 포트 번호

# 데이터베이스 설정
DB_USERNAME=                              # 데이터베이스 사용자명 
DB_PASSWORD=                              # 데이터베이스 비밀번호
DB_PORT=5432                              # 데이터베이스 포트 번호 
DB_NAME=                                  # 데이터베이스 이름 

# 데이터베이스 URL
DOCKER_DB_URL=jdbc:postgresql://[db-url]:${DB_PORT}/${DB_NAME}  # DB URL 부분 작성

# AWS S3 설정
BUCKET_NAME=                              # AWS S3 버킷 이름
```

#### Frontend `.env` 파일 설정

```plaintext
# API URL
```

### 4. Nginx 설치 및 설정

Nginx는 프론트엔드와 백엔드 간의 요청을 처리하는 리버스 프록시 역할을 합니다. 아래 단계를 따라 설치와 설정을 진행하세요.


#### Nginx 설치 및 설정

1. **Nginx 설치**  
   아래 명령어를 실행하여 Nginx를 설치합니다.

   ```bash
   sudo apt update
   sudo apt install -y nginx
   ```
2. **Nginx 설정**
   아래 설정을 `/etc/nginx/sites-available/my-app` 파일에 추가합니다.
   ```plaintext

    server {
        listen 80;
        server_name             # 도메인 이름, 없으면 localhost 또는 서버 IP를 사용

        location / {
            root /var/www/html;
            index index.html index.htm;
            try_files $uri /index.html;
        }

        location /api/ {
            proxy_pass http://localhost:8080; # Spring Boot 서버
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection 'upgrade';
            proxy_set_header Host $host;
            proxy_cache_bypass $http_upgrade;

            # 파일 업로드 크기 제한 증가
            client_max_body_size 100M;
        }
    }

   ```
3. **Nginx 실행**
    ```bash
    sudo systemctl restart nginx 
    ```
## 5. 스프링부트 실행

Spring Boot 애플리케이션을 Docker Compose를 사용하여 실행합니다. 아래 명령어를 따라 실행하세요.


### 실행 방법

1. **백엔드 디렉토리로 이동**  
   Spring Boot 애플리케이션이 위치한 디렉토리로 이동합니다.

   ```bash
   cd /backend
   ```

2. **Docker Compose로 애플리케이션 실행**
   아래 명령어를 실행하여 Spring Boot 애플리케이션을 빌드하고 실행합니다.
   ```bash
   sudo docker-compose up --build
   ```

## 6. 리액트 실행

React 애플리케이션을 빌드합니다. 아래 단계를 따라 실행하세요.


### 실행 방법

1. **프론트엔드 디렉토리로 이동**  
   React 애플리케이션이 위치한 디렉토리로 이동합니다.

   ```bash
   cd /frontend

2. **의존성 설치**
    `npm`을 사용하여 프로젝트의 필요한 의존성을 설치합니다.
    ```bash
    npm install
    ```
3. **빌드**
    ```bash
    npm run build
    ```

4. **빌드 파일 배포**
    React 빌드 파일을 `/var/www/html` 디렉토리로 복사합니다.
    ```bash
    # 기존 파일 삭제
    sudo rm -rf /var/www/html/*

    # 빌드 파일 복사
    sudo cp -r build/* /var/www/html/

    ```