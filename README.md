# kotlin-movie

# 3단계 - 영화 예매(데이터베이스)

## 기능 목록

### DB 환경설정

- [x] Connection 제공
- [ ] 앱 시작 시, 테이블 생성 및 초기값 설정
    - 이미 존재하는 경우 Skip

### Schema

- [ ] MovieTable
    - id, 제목, 상영 길이, 상영 기간
- [ ] ScreeningRoomTable
    - id, 이름, 운영 시간
- [ ] ScreeningTable
    - id, 영화 id, 상영관 id, 시작 시간
- [ ] ScreeningRoomSeatTable
    - 상영관id, 행, 열
- [ ] ReservationTable
    - id, 상영 id
- [ ] ReservationSeatTable
    - 예약 id, 행, 열

### Repository 구현

### 연동
