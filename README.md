# Java RPG Game - 풀스택 웹 기반 턴제 전투 게임

Java RPG Game은 Spring Boot 백엔드와 React 프론트엔드로 구성된 웹 기반 턴제 RPG 게임입니다. Eclipse와 VS Code를 활용한 풀스택 개발 환경에서 제작되었으며, Docker 컨테이너화와 AWS 클라우드 배포를 통해 확장 가능한 아키텍처를 구현합니다.

## 🎮 Live Demo (배포 예정)
- **Frontend**: https://rpg-game.your-domain.com (예정)
- **Backend API**: https://api.rpg-game.your-domain.com (예정)
- **API Documentation**: https://api.rpg-game.your-domain.com/swagger-ui.html

## 📊 현재 개발 현황

- **개발 단계**: 초기 개발 중 (v0.1.0-alpha)
- **구현된 기능**: 
  - ✅ 백엔드 프로젝트 구조 설계
  - ✅ 프론트엔드 프로젝트 구조 설계
  - ✅ Git 통합 관리 체계 구축
  - 🔄 Entity 클래스 구현 (진행 중)
- **목표 완성도**: MVP (Minimum Viable Product) - 4주 예정
- **개발 도구**: Eclipse (백엔드), VS Code (프론트엔드)

## 목차

- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [시스템 아키텍처](#시스템-아키텍처)
- [설치 및 실행](#설치-및-실행)
- [개발 환경 설정](#개발-환경-설정)
- [Git 워크플로우](#git-워크플로우)
- [게임 메커니즘](#게임-메커니즘)
- [프로젝트 구조](#프로젝트-구조)
- [API 엔드포인트](#api-엔드포인트)
- [데이터 모델](#데이터-모델)
- [배포 가이드](#배포-가이드)
- [개발 로드맵](#개발-로드맵)
- [기여 가이드](#기여-가이드)
- [라이선스](#라이선스)

## 주요 기능

### 1. **턴제 전투 시스템**
   - 플레이어 vs 적 1:1 전투
   - 공격/방어 액션 선택
   - 랜덤 데미지 시스템 (크리티컬 포함)
   - 실시간 전투 로그

### 2. **캐릭터 성장 시스템**
   - 경험치 획득 및 레벨업
   - 레벨별 능력치 자동 증가
   - 플레이어 통계 추적 (승률, 총 전투 수)

### 3. **다양한 적 타입**
   - 플레이어 레벨에 따른 적 생성
   - 4가지 적 타입: 고블린, 오크, 스켈레톤, 다크엘프
   - 적별 특성화된 능력치

### 4. **게임 세션 관리**
   - Redis 기반 실시간 세션 관리
   - 게임 상태 자동 저장
   - 배틀 히스토리 기록

### 5. **리더보드 시스템**
   - 상위 플레이어 랭킹
   - 승률 기반 순위
   - 실시간 통계 업데이트

## 기술 스택

### 백엔드
- **프레임워크**: Spring Boot 3.2.x
- **언어**: Java 17
- **빌드 도구**: Maven
- **ORM**: Spring Data JPA (Hibernate)
- **데이터베이스**: PostgreSQL 15
- **캐시**: Redis 7
- **API 문서**: Swagger/OpenAPI 3.0
- **개발 도구**: Eclipse IDE

### 프론트엔드
- **프레임워크**: React 18
- **언어**: JavaScript (ES6+)
- **상태 관리**: Redux Toolkit
- **HTTP 클라이언트**: Axios
- **스타일링**: Styled Components
- **빌드 도구**: Create React App
- **개발 도구**: VS Code

### DevOps & 인프라
- **컨테이너화**: Docker, Docker Compose
- **CI/CD**: GitHub Actions
- **클라우드**: AWS (ECS, RDS, ECR, ALB)
- **IaC**: Terraform
- **모니터링**: AWS CloudWatch

### 버전 관리
- **VCS**: Git
- **저장소**: GitHub
- **브랜치 전략**: Git Flow
- **통합 관리**: Monorepo (단일 저장소)

## 시스템 아키텍처

```
┌─────────────────────────────────────────────────────────────────┐
│                         사용자 (브라우저)                          │
└────────────────────────────┬────────────────────────────────────┘
                             │ HTTPS
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    AWS Application Load Balancer                 │
└──────────────┬───────────────────────────────────┬───────────────┘
               │                                   │
               ▼                                   ▼
┌──────────────────────────┐          ┌──────────────────────────┐
│  React Frontend          │          │  Spring Boot Backend     │
│  (ECS Fargate)           │          │  (ECS Fargate)          │
│  - Game UI               │◄────────►│  - REST API             │
│  - Redux State           │   HTTP   │  - Game Logic           │
│  - Styled Components     │          │  - Business Rules       │
└──────────────────────────┘          └──────────┬───────────────┘
                                                 │
                          ┌──────────────────────┼──────────────────┐
                          ▼                      ▼                  ▼
                  ┌──────────────┐      ┌──────────────┐  ┌──────────────┐
                  │ PostgreSQL   │      │    Redis     │  │  CloudWatch  │
                  │ (AWS RDS)    │      │  (Session)   │  │ (Monitoring) │
                  │ - Players    │      │  - Cache     │  │ - Logs       │
                  │ - Battles    │      │              │  │ - Metrics    │
                  └──────────────┘      └──────────────┘  └──────────────┘
```

### 데이터 흐름

```
사용자 액션 → React Component → Redux Action → Axios → Spring Controller
     ↓                                                           ↓
  UI 업데이트                                            Service Layer
     ↑                                                           ↓
Redux Store ← JSON Response ← REST API ← Repository ← PostgreSQL
                                              ↓
                                         Redis Cache
```

## 설치 및 실행

### 사전 요구사항

```
필수 설치 항목:
☐ Java JDK 17
☐ Node.js 18+
☐ Docker Desktop
☐ Git
☐ Eclipse IDE
☐ VS Code

선택 설치 항목:
☐ PostgreSQL (로컬 개발 시)
☐ Redis (로컬 개발 시)
```

### 1. 프로젝트 클론

```bash
git clone https://github.com/your-username/java-rpg-game.git
cd java-rpg-game
```

### 2. 환경 변수 설정

```bash
# 프로젝트 루트에 .env 파일 생성
cp .env.example .env
```

**.env 파일 내용:**
```bash
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=rpgdb
DB_USER=rpguser
DB_PASSWORD=your_secure_password

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# Application
SERVER_PORT=8080
FRONTEND_URL=http://localhost:3000
```

### 3. Docker로 데이터베이스 실행

```bash
# PostgreSQL 시작
docker run -d \
  --name rpg-postgres \
  -e POSTGRES_DB=rpgdb \
  -e POSTGRES_USER=rpguser \
  -e POSTGRES_PASSWORD=rpgpass \
  -p 5432:5432 \
  postgres:15

# Redis 시작
docker run -d \
  --name rpg-redis \
  -p 6379:6379 \
  redis:7-alpine

# 실행 확인
docker ps
```

### 4. 백엔드 실행 (Eclipse)

```
1. Eclipse 실행
2. File → Import → Existing Maven Projects
3. C:\java-rpg-game\rpg-backend 선택
4. RpgBackendApplication.java 우클릭
5. Run As → Spring Boot App
6. Console에서 "Started RpgBackendApplication" 확인
```

**또는 명령줄로:**
```bash
cd rpg-backend
./mvnw spring-boot:run
```

### 5. 프론트엔드 실행 (VS Code)

```bash
cd rpg-frontend
npm install
npm start
```

**접속 확인:**
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

### 6. Docker Compose로 전체 실행 (선택)

```bash
# 전체 스택 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f

# 중지
docker-compose down
```

## 개발 환경 설정

### Eclipse 설정 (백엔드)

**필수 플러그인:**
- Spring Tools 4
- Lombok
- STS (Spring Tool Suite)

**Eclipse 설정:**
```
1. Window → Preferences
2. Java → Compiler → Compiler compliance level: 17
3. Maven → User Settings: settings.xml 확인
4. General → Workspace → Text file encoding: UTF-8
```

### VS Code 설정 (프론트엔드)

**필수 확장 프로그램:**
```
☑ ES7+ React/Redux/React-Native snippets
☑ ESLint
☑ Prettier - Code formatter
☑ Auto Rename Tag
☑ GitLens
☑ Git Graph
```

**VS Code 설정 (settings.json):**
```json
{
  "editor.formatOnSave": true,
  "editor.defaultFormatter": "esbenp.prettier-vscode",
  "eslint.autoFixOnSave": true,
  "prettier.singleQuote": true,
  "prettier.trailingComma": "es5"
}
```

## Git 워크플로우

### 브랜치 전략

```
main                     # 프로덕션 배포 브랜치
└── develop              # 개발 통합 브랜치
    ├── feature/player   # 플레이어 시스템
    ├── feature/battle   # 전투 시스템
    ├── feature/ui       # UI 컴포넌트
    └── bugfix/xxx       # 버그 수정
```

### 커밋 메시지 컨벤션

```
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 수정
style: 코드 포맷팅
refactor: 코드 리팩토링
test: 테스트 추가/수정
chore: 빌드 업무 수정

예시:
feat: Player 엔티티 및 공격 메소드 구현
fix: 방어 시 데미지 계산 오류 수정
docs: API 엔드포인트 문서 업데이트
```

### 통합 관리 (Monorepo)

**단일 저장소에서 백엔드와 프론트엔드 함께 관리:**

```bash
# Eclipse에서 백엔드 작업
# Player.java 수정

# VS Code로 전환 (Alt+Tab)
# GameBoard.jsx 수정

# VS Code Git 패널에서 함께 커밋
git add .
git commit -m "feat: 플레이어 공격 기능 구현

- backend: Player.attack() 메소드 추가
- frontend: 공격 버튼 UI 연동
- 데미지 계산 로직 통합"

git push origin feature/player-attack
```

## 게임 메커니즘

### 전투 시스템

**턴 흐름:**
```
1. 플레이어 턴
   ├─ 공격 선택 → 데미지 계산 → 적 체력 감소
   └─ 방어 선택 → 방어 상태 활성화

2. 적 턴 (자동)
   └─ 공격 실행 → 데미지 계산 → 플레이어 체력 감소
      (방어 중이면 데미지 50% 감소)

3. 승패 판정
   ├─ 적 체력 0 → 플레이어 승리 → 경험치 획득
   └─ 플레이어 체력 0 → 패배
```

### 데미지 계산 공식

**플레이어 공격:**
```
기본 데미지 범위 = attackPower × 0.75 ~ attackPower × 1.25
크리티컬 확률 = 10%
크리티컬 데미지 = 기본 데미지 × 1.5

최종 데미지 = RANDOM(기본 데미지 범위)
만약 크리티컬이면:
  최종 데미지 = 최종 데미지 × 1.5
```

**적 공격:**
```
기본 데미지 범위 = attackPower × 0.7 ~ attackPower × 1.3
최종 데미지 = RANDOM(기본 데미지 범위)

플레이어가 방어 중이면:
  최종 데미지 = 최종 데미지 / 2
```

### 레벨업 시스템

**경험치 공식:**
```
다음 레벨까지 필요 경험치 = 100 + (현재 레벨 × 50)

예시:
Level 1 → 2: 150 exp
Level 2 → 3: 200 exp
Level 3 → 4: 250 exp
```

**레벨업 보상:**
```
maxHealth += 10
attackPower += 5
defensePower += 3
health = maxHealth (완전 회복)
```

### 적 생성 로직

**플레이어 레벨별 적 타입:**
```
Level 1-3:   고블린 (Goblin)
Level 4-6:   고블린, 오크 (Orc)
Level 7-10:  오크, 스켈레톤 (Skeleton)
Level 11+:   스켈레톤, 다크엘프 (Dark Elf)
```

**적 능력치 계산:**
```
health = 80 + (playerLevel × 10)
attackPower = 15 + (playerLevel × 5)
experienceReward = 50 + (playerLevel × 10)
```

## 프로젝트 구조

```
📦java-rpg-game
 ┣ 📂rpg-backend                        # Spring Boot 백엔드
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┣ 📂java/com/rpggame
 ┃ ┃ ┃ ┃ ┣ 📂config                    # 설정 클래스
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CorsConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜RedisConfig.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┣ 📂entity                    # JPA 엔티티
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Player.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Enemy.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Battle.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BattleLog.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜GameSession.java
 ┃ ┃ ┃ ┃ ┣ 📂dto                       # 데이터 전송 객체
 ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜StartGameRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BattleActionRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CreatePlayerRequest.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┃   ┣ 📜GameStateResponse.java
 ┃ ┃ ┃ ┃ ┃   ┣ 📜PlayerResponse.java
 ┃ ┃ ┃ ┃ ┃   ┣ 📜EnemyResponse.java
 ┃ ┃ ┃ ┃ ┃   ┗ 📜LeaderboardResponse.java
 ┃ ┃ ┃ ┃ ┣ 📂controller                # REST 컨트롤러
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GameController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PlayerController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BattleController.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜LeaderboardController.java
 ┃ ┃ ┃ ┃ ┣ 📂service                   # 비즈니스 로직
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GameService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PlayerService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BattleService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EnemyService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜SessionService.java
 ┃ ┃ ┃ ┃ ┣ 📂repository                # 데이터 접근
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PlayerRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EnemyRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BattleRepository.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜BattleLogRepository.java
 ┃ ┃ ┃ ┃ ┣ 📂util                      # 유틸리티
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GameUtils.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DamageCalculator.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜EnemyGenerator.java
 ┃ ┃ ┃ ┃ ┣ 📂exception                 # 예외 처리
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GameNotFoundException.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜InvalidActionException.java
 ┃ ┃ ┃ ┃ ┣ 📂constant                  # 상수
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ActionType.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EnemyType.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜GameStatus.java
 ┃ ┃ ┃ ┃ ┗ 📜RpgBackendApplication.java
 ┃ ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃   ┣ 📜application.yml
 ┃ ┃ ┃   ┣ 📜application-dev.yml
 ┃ ┃ ┃   ┣ 📜application-prod.yml
 ┃ ┃ ┃   ┗ 📜data.sql
 ┃ ┃ ┗ 📂test                          # 테스트 코드
 ┃ ┣ 📜Dockerfile
 ┃ ┣ 📜pom.xml
 ┃ ┗ 📜.gitignore
 ┃
 ┣ 📂rpg-frontend                       # React 프론트엔드
 ┃ ┣ 📂public
 ┃ ┃ ┣ 📜index.html
 ┃ ┃ ┗ 📂assets
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂components                    # React 컴포넌트
 ┃ ┃ ┃ ┣ 📂Game
 ┃ ┃ ┃ ┃ ┣ 📜GameBoard.jsx
 ┃ ┃ ┃ ┃ ┣ 📜GameStart.jsx
 ┃ ┃ ┃ ┃ ┣ 📜GameOver.jsx
 ┃ ┃ ┃ ┃ ┣ 📜PlayerStatus.jsx
 ┃ ┃ ┃ ┃ ┣ 📜EnemyStatus.jsx
 ┃ ┃ ┃ ┃ ┣ 📜BattleActions.jsx
 ┃ ┃ ┃ ┃ ┗ 📜BattleLog.jsx
 ┃ ┃ ┃ ┣ 📂UI
 ┃ ┃ ┃ ┃ ┣ 📜Button.jsx
 ┃ ┃ ┃ ┃ ┣ 📜HealthBar.jsx
 ┃ ┃ ┃ ┃ ┣ 📜Modal.jsx
 ┃ ┃ ┃ ┃ ┗ 📜Loading.jsx
 ┃ ┃ ┃ ┗ 📂Layout
 ┃ ┃ ┃   ┣ 📜Header.jsx
 ┃ ┃ ┃   ┗ 📜Footer.jsx
 ┃ ┃ ┣ 📂pages                         # 페이지 컴포넌트
 ┃ ┃ ┃ ┣ 📜HomePage.jsx
 ┃ ┃ ┃ ┣ 📜GamePage.jsx
 ┃ ┃ ┃ ┗ 📜LeaderboardPage.jsx
 ┃ ┃ ┣ 📂services                      # API 서비스
 ┃ ┃ ┃ ┣ 📜api.js
 ┃ ┃ ┃ ┣ 📜gameService.js
 ┃ ┃ ┃ ┗ 📜playerService.js
 ┃ ┃ ┣ 📂store                         # Redux Store
 ┃ ┃ ┃ ┣ 📜index.js
 ┃ ┃ ┃ ┗ 📂slices
 ┃ ┃ ┃   ┣ 📜gameSlice.js
 ┃ ┃ ┃   ┗ 📜playerSlice.js
 ┃ ┃ ┣ 📂hooks                         # Custom Hooks
 ┃ ┃ ┃ ┗ 📜useGame.js
 ┃ ┃ ┣ 📂utils                         # 유틸리티
 ┃ ┃ ┃ ┣ 📜constants.js
 ┃ ┃ ┃ ┗ 📜helpers.js
 ┃ ┃ ┣ 📂styles                        # 스타일
 ┃ ┃ ┃ ┣ 📜GlobalStyles.js
 ┃ ┃ ┃ ┗ 📜theme.js
 ┃ ┃ ┣ 📜App.jsx
 ┃ ┃ ┗ 📜index.js
 ┃ ┣ 📜Dockerfile
 ┃ ┣ 📜nginx.conf
 ┃ ┣ 📜package.json
 ┃ ┗ 📜.gitignore
 ┃
 ┣ 📂infrastructure                     # Terraform 인프라
 ┃ ┣ 📂modules
 ┃ ┃ ┣ 📂vpc
 ┃ ┃ ┣ 📂ecs
 ┃ ┃ ┣ 📂rds
 ┃ ┃ ┗ 📂alb
 ┃ ┣ 📜main.tf
 ┃ ┣ 📜variables.tf
 ┃ ┗ 📜outputs.tf
 ┃
 ┣ 📂.github
 ┃ ┗ 📂workflows
 ┃   ┣ 📜backend-ci.yml
 ┃   ┣ 📜frontend-ci.yml
 ┃   ┗ 📜deploy.yml
 ┃
 ┣ 📜docker-compose.yml
 ┣ 📜.env.example
 ┣ 📜.gitignore
 ┣ 📜README.md
 ┗ 📜LICENSE
```

## API 엔드포인트

### 게임 관련

**게임 시작**
```http
POST /api/game/start
Content-Type: application/json

Request:
{
  "playerName": "홍길동"
}

Response:
{
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "player": {
    "id": 1,
    "name": "홍길동",
    "health": 100,
    "maxHealth": 100,
    "attackPower": 20,
    "level": 1
  },
  "enemy": {
    "id": 1,
    "name": "고블린 전사",
    "health": 80,
    "attackPower": 15
  },
  "currentTurn": 1,
  "isPlayerTurn": true,
  "battleLogs": [],
  "isGameOver": false
}
```

**액션 수행**
```http
POST /api/game/action/{sessionId}
Content-Type: application/json

Request:
{
  "actionType": "ATTACK"  // 또는 "DEFEND"
}

Response:
{
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "player": {
    "health": 85,
    "isDefending": false
  },
  "enemy": {
    "health": 58
  },
  "currentTurn": 2,
  "battleLogs": [
    "홍길동이(가) 22의 데미지로 공격합니다!",
    "고블린 전사이(가) 22의 데미지를 받았습니다!",
    "고블린 전사이(가) 15의 데미지로 공격합니다!",
    "홍길동이(가) 15의 데미지를 받았습니다!"
  ],
  "isGameOver": false,
  "winner": null
}
```

**게임 상태 조회**
```http
GET /api/game/state/{sessionId}

Response:
{
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "player": { ... },
  "enemy": { ... },
  "isGameOver": false,
  "winner": null
}
```

### 플레이어 관련

**플레이어 정보 조회**
```http
GET /api/players/{playerName}

Response:
{
  "id": 1,
  "name": "홍길동",
  "level": 5,
  "experience": 230,
  "totalBattles": 10,
  "wins": 7,
  "losses": 3,
  "winRate": 70.0
}
```

**배틀 히스토리**
```http
GET /api/players/{id}/battles

Response:
{
  "battles": [
    {
      "id": 1,
      "enemyName": "고블린 전사",
      "result": "WIN",
      "totalTurns": 5,
      "playerFinalHealth": 65,
      "startedAt": "2025-10-11T10:30:00",
      "endedAt": "2025-10-11T10:32:15"
    },
    ...
  ]
}
```

### 리더보드 관련

**상위 플레이어 랭킹**
```http
GET /api/leaderboard?limit=10

Response:
{
  "leaderboard": [
    {
      "rank": 1,
      "name": "홍길동",
      "level": 10,
      "wins": 50,
      "winRate": 85.5
    },
    ...
  ]
}
```

## 데이터 모델

### Player (플레이어)

```java
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    
    private Integer health = 100;
    private Integer maxHealth = 100;
    private Integer attackPower = 20;
    private Integer defensePower = 10;
    private Integer level = 1;
    private Integer experience = 0;
    private Integer totalBattles = 0;
    private Integer wins = 0;
    private Integer losses = 0;
    private Boolean isDefending = false;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

### Enemy (적)

```java
@Entity
@Table(name = "enemies")
public class Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private Integer health;
    private Integer maxHealth;
    private Integer attackPower;
    
    @Enumerated(EnumType.STRING)
    private EnemyType enemyType;  // GOBLIN, ORC, SKELETON, DARK_ELF
    
    private Integer experienceReward;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

### Battle (전투 기록)

```java
@Entity
@Table(name = "battles")
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    
    @ManyToOne
    @JoinColumn(name = "enemy_id")
    private Enemy enemy;
    
    @Enumerated(EnumType.STRING)
    private GameStatus status;  // ONGOING, PLAYER_WIN, PLAYER_LOSE
    
    private Integer totalTurns;
    private Integer playerFinalHealth;
    
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    
    @OneToMany(mappedBy = "battle", cascade = CascadeType.ALL)
    private List<BattleLog> battleLogs;
}
```

### BattleLog (전투 로그)

```java
@Entity
@Table(name = "battle_logs")
public class BattleLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "battle_id")
    private Battle battle;
    
    private Integer turnNumber;
    private String actorType;  // PLAYER, ENEMY
    
    @Enumerated(EnumType.STRING)
    private ActionType actionType;  // ATTACK, DEFEND
    
    private Integer damage;
    private String message;
    
    private LocalDateTime timestamp;
}
```

### GameSession (Redis 캐시)

```java
public class GameSession implements Serializable {
    private String sessionId;
    private Long playerId;
    private Long enemyId;
    private Integer playerHealth;
    private Integer enemyHealth;
    private Integer currentTurn;
    private Boolean isPlayerTurn;
    private GameStatus gameStatus;
    private List<String> battleLogs;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;  // TTL: 30분
}
```

## 배포 가이드

### Docker Compose 배포 (로컬/개발)

**docker-compose.yml:**
```yaml
version: '3.8'

services:
  db:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: rpgdb
      POSTGRES_USER: rpguser
      POSTGRES_PASSWORD: rpgpass
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  backend:
    build: ./rpg-backend
    ports:
      - "8080:8080"
    depends_on:
      - db
      - redis
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/rpgdb
      - SPRING_REDIS_HOST=redis

  frontend:
    build: ./rpg-frontend
    ports:
      - "3000:80"
    depends_on:
      - backend

volumes:
  postgres_data:
```

**실행:**
```bash
docker-compose up -d
```

### AWS ECS 배포 (프로덕션)

**사전 준비:**
```bash
# AWS CLI 설치 및 설정
aws configure

# Terraform 설치
brew install terraform  # Mac
choco install terraform  # Windows
```

**1. 인프라 구축:**
```bash
cd infrastructure
terraform init
terraform plan
terraform apply
```

**2. Docker 이미지 빌드 및 푸시:**
```bash
# ECR 로그인
aws ecr get-login-password --region ap-northeast-2 | \
  docker login --username AWS --password-stdin \
  YOUR_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com

# 백엔드 이미지
cd rpg-backend
docker build -t rpg-backend .
docker tag rpg-backend:latest \
  YOUR_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/rpg-backend:latest
docker push YOUR_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/rpg-backend:latest

# 프론트엔드 이미지
cd rpg-frontend
docker build -t rpg-frontend .
docker tag rpg-frontend:latest \
  YOUR_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/rpg-frontend:latest
docker push YOUR_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/rpg-frontend:latest
```

**3. ECS 서비스 업데이트:**
```bash
aws ecs update-service \
  --cluster rpg-game-cluster \
  --service rpg-backend-service \
  --force-new-deployment

aws ecs update-service \
  --cluster rpg-game-cluster \
  --service rpg-frontend-service \
  --force-new-deployment
```

### GitHub Actions CI/CD

**자동 배포 설정:**

1. GitHub Repository → Settings → Secrets 추가:
   - `AWS_ACCESS_KEY_ID`
   - `AWS_SECRET_ACCESS_KEY`
   - `DB_PASSWORD`

2. Push to main 브랜치 → 자동 배포 실행

**배포 프로세스:**
```
Git Push → GitHub Actions
  ↓
테스트 실행 (Backend + Frontend)
  ↓
Docker 이미지 빌드
  ↓
ECR에 푸시
  ↓
ECS 서비스 업데이트
  ↓
헬스 체크
  ↓
배포 완료 알림 (Slack)
```

## 개발 로드맵

### Phase 1: MVP (4주) - 현재 진행 중

**Week 1: 백엔드 핵심 구현**
- ✅ 프로젝트 구조 설계
- 🔄 Entity 클래스 구현 (진행 중)
- ⬜ Repository 인터페이스 작성
- ⬜ Service 계층 비즈니스 로직
- ⬜ Controller REST API 구현

**Week 2: 프론트엔드 핵심 구현**
- ⬜ Redux Store 설정
- ⬜ API 서비스 연동
- ⬜ Game UI 컴포넌트
- ⬜ 전투 화면 구현
- ⬜ 리더보드 화면

**Week 3: 통합 및 테스트**
- ⬜ 백엔드-프론트엔드 연동
- ⬜ 전투 시스템 테스트
- ⬜ 버그 수정
- ⬜ UI/UX 개선

**Week 4: Docker & 배포**
- ⬜ Docker 컨테이너화
- ⬜ docker-compose 설정
- ⬜ AWS 인프라 구축
- ⬜ CI/CD 파이프라인
- ⬜ 프로덕션 배포

### Phase 2: 기능 확장 (4주)

**추가 기능:**
- ⬜ 아이템 시스템 (포션, 무기, 방어구)
- ⬜ 인벤토리 관리
- ⬜ 스킬 시스템
- ⬜ 다양한 스테이지
- ⬜ 업적 시스템
- ⬜ 친구 대전 모드

### Phase 3: 고도화 (4주)

**고급 기능:**
- ⬜ 실시간 멀티플레이어 (WebSocket)
- ⬜ 길드 시스템
- ⬜ PvP 아레나
- ⬜ 랭킹 시즌제
- ⬜ 일일 퀘스트
- ⬜ 이벤트 시스템

## 성능 최적화

### 백엔드 최적화

**데이터베이스 인덱싱:**
```sql
CREATE INDEX idx_player_name ON players(name);
CREATE INDEX idx_battle_player ON battles(player_id);
CREATE INDEX idx_battle_timestamp ON battles(started_at);
CREATE INDEX idx_battlelog_battle ON battle_logs(battle_id);
```

**JPA N+1 문제 해결:**
```java
@Query("SELECT b FROM Battle b " +
       "JOIN FETCH b.player " +
       "JOIN FETCH b.enemy " +
       "LEFT JOIN FETCH b.battleLogs " +
       "WHERE b.player.id = :playerId")
List<Battle> findBattlesByPlayerWithDetails(@Param("playerId") Long playerId);
```

**Redis 캐싱 전략:**
```java
@Cacheable(value = "leaderboard", key = "'top10'")
public List<PlayerResponse> getTop10Players() {
    return playerRepository.findTop10ByOrderByWinsDesc();
}
```

### 프론트엔드 최적화

**React.memo로 불필요한 리렌더링 방지:**
```javascript
const PlayerStatus = React.memo(({ player, isActive }) => {
    // 컴포넌트 구현
});
```

**Code Splitting:**
```javascript
const LeaderboardPage = React.lazy(() => import('./pages/LeaderboardPage'));

<Suspense fallback={<Loading />}>
    <LeaderboardPage />
</Suspense>
```

**이미지 최적화:**
```javascript
// WebP 포맷 사용
import playerImage from './assets/player.webp';

// 지연 로딩
<img src={playerImage} loading="lazy" alt="Player" />
```

## 보안 고려사항

### 백엔드 보안

**1. SQL Injection 방지:**
```java
// ✅ 안전: JPA Repository 사용
playerRepository.findByName(playerName);

// ❌ 위험: 직접 쿼리 작성 시 파라미터 바인딩 필수
```

**2. CORS 설정:**
```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("https://rpg-game.your-domain.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);
            }
        };
    }
}
```

**3. Rate Limiting:**
```java
@RateLimiter(name = "gameApi", fallbackMethod = "rateLimitFallback")
@PostMapping("/api/game/start")
public ResponseEntity<GameStateResponse> startGame(@RequestBody StartGameRequest request) {
    // ...
}
```

### 프론트엔드 보안

**1. XSS 방지:**
```javascript
// React는 기본적으로 XSS 방지
// dangerouslySetInnerHTML 사용 금지

// ✅ 안전
<div>{playerName}</div>

// ❌ 위험
<div dangerouslySetInnerHTML={{__html: userInput}} />
```

**2. API 키 보호:**
```javascript
// .env 파일 사용
REACT_APP_API_URL=https://api.rpg-game.your-domain.com

// .gitignore에 추가
.env
.env.local
```

## 테스트

### 백엔드 테스트

**단위 테스트:**
```java
@SpringBootTest
class PlayerServiceTest {
    
    @Autowired
    private PlayerService playerService;
    
    @Test
    void testPlayerLevelUp() {
        Player player = new Player("테스트플레이어");
        player.gainExperience(150);
        
        assertEquals(2, player.getLevel());
        assertEquals(110, player.getMaxHealth());
    }
}
```

**통합 테스트:**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testStartGame() {
        StartGameRequest request = new StartGameRequest("홍길동");
        ResponseEntity<GameStateResponse> response = 
            restTemplate.postForEntity("/api/game/start", request, GameStateResponse.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getSessionId());
    }
}
```

### 프론트엔드 테스트

**컴포넌트 테스트:**
```javascript
import { render, screen, fireEvent } from '@testing-library/react';
import GameStart from './GameStart';

test('플레이어 이름 입력 및 게임 시작', () => {
    const mockOnStart = jest.fn();
    render(<GameStart onStart={mockOnStart} />);
    
    const input = screen.getByPlaceholderText('플레이어 이름을 입력하세요');
    fireEvent.change(input, { target: { value: '홍길동' } });
    
    const button = screen.getByText('게임 시작');
    fireEvent.click(button);
    
    expect(mockOnStart).toHaveBeenCalledWith('홍길동');
});
```

**E2E 테스트 (Cypress):**
```javascript
describe('RPG Game E2E', () => {
    it('전체 게임 플레이', () => {
        cy.visit('http://localhost:3000');
        
        cy.get('input[placeholder="플레이어 이름을 입력하세요"]')
          .type('홍길동');
        cy.get('button').contains('게임 시작').click();
        
        cy.contains('고블린 전사').should('be.visible');
        cy.get('button').contains('공격하기').click();
        
        cy.contains('데미지').should('be.visible');
    });
});
```

## 트러블슈팅

### 자주 발생하는 문제

**1. PostgreSQL 연결 실패**
```
Error: Connection refused

해결:
1. Docker 컨테이너 실행 확인: docker ps
2. 포트 확인: 5432 포트가 다른 프로세스에서 사용 중인지 확인
3. application.yml의 datasource URL 확인
```

**2. CORS 에러**
```
Access to XMLHttpRequest has been blocked by CORS policy

해결:
1. CorsConfig.java에서 allowedOrigins 확인
2. 프론트엔드 URL이 정확히 일치하는지 확인
3. credentials: true 설정
```

**3. Redis 연결 실패**
```
Unable to connect to Redis

해결:
1. Redis 컨테이너 실행: docker start rpg-redis
2. application.yml의 redis host 확인
3. 포트 6379 확인
```

**4. React 빌드 실패**
```
npm run build 실패

해결:
1. node_modules 삭제 후 재설치: rm -rf node_modules && npm install
2. package-lock.json 삭제 후 재설치
3. Node.js 버전 확인: node -v (18+ 필요)
```

## 기여 가이드

### 개발 환경 설정

```bash
# 1. Fork 저장소
# 2. 클론
git clone https://github.com/your-username/java-rpg-game.git

# 3. 브랜치 생성
git checkout -b feature/your-feature-name

# 4. 개발 및 커밋
git add .
git commit -m "feat: 새로운 기능 추가"

# 5. Push
git push origin feature/your-feature-name

# 6. Pull Request 생성
```

### 코드 스타일

**Java (백엔드):**
- Google Java Style Guide 준수
- Lombok 적극 활용
- 주석은 Javadoc 형식

**JavaScript (프론트엔드):**
- Airbnb JavaScript Style Guide 준수
- ESLint + Prettier 사용
- 함수형 컴포넌트 우선

### Pull Request 체크리스트

```
☐ 코드가 빌드되고 테스트가 통과함
☐ 새로운 기능에 대한 테스트 추가
☐ 커밋 메시지가 컨벤션을 따름
☐ README 업데이트 (필요시)
☐ 변경사항에 대한 상세 설명
```

## 라이선스

MIT License

Copyright (c) 2025 Java RPG Game Team

자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 연락처 및 지원

- **GitHub Issues**: [프로젝트 이슈](https://github.com/your-username/java-rpg-game/issues)
- **Pull Requests**: [기여하기](https://github.com/your-username/java-rpg-game/pulls)
- **Email**: your-email@example.com

---

**Java RPG Game**은 풀스택 개발의 모든 과정을 경험할 수 있는 학습용 프로젝트입니다. Spring Boot와 React를 활용한 현대적인 웹 애플리케이션 개발, Docker를 통한 컨테이너화, AWS 클라우드 배포, 그리고 CI/CD 파이프라인 구축까지 실전 개발 환경을 그대로 체험할 수 있습니다.

**⭐ 프로젝트가 도움이 되었다면 Star를 눌러주세요!**