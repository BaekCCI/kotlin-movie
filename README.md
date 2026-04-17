# kotlin-movie

# 3단계 - 영화 예매(데이터베이스)

## 기능 목록

### DB 환경설정

- [x] Connection 제공
- [x] 테이블 생성 및 초기값 설정
    - 이미 존재하는 경우 Skip

### Schema

- [x] MovieTable
    - id, 제목, 상영 길이, 상영 기간
- [x] ScreeningRoomTable
    - id, 이름, 운영 시간
- [x] ScreeningTable
    - id, 영화 id, 상영관 id, 시작 시간
- [x] ScreeningRoomSeatTable
    - 상영관id, 행, 열
- [x] ReservationTable
    - id, 상영 id
- [x] ReservationSeatTable
    - 예약 id, 행, 열

### Repository

- [ ] ScreeningRepository
    - [ ] 전체 상영 스케줄 조회
- [ ] ReservationRepository
    - [ ] 예약 정보 저장

### 연동
