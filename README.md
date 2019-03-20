### 2019 카카오 사전과제 : 3. 주택 금융 서비스 API 개발


> 시작하며
* 스프링부트, JPA, Restful 모두 처음 접해보는 기술

  => '처음배우는 스프링부트 2' 책을 구매하여 정독후 한번 도전해보기로 함
---------------------------------------


> 환경설정
* Java 1.8
* gradle
* Spring boot 2.1.3
* Lombok 1.16.18
* poi 4.0.1
* gson 2.8.0
* h2
* boot-starter-data-jpa
---------------------------------------


> 문제해결전략


**엔티티구성**

=> BANK (idx, bankCode, bankName)

=> HOUSELOAN (idx, bankCode, yyyy, mm, amt)

**필수문제**


* 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발

=> openCSV를 고려했으나 Apache POI로 CSV 파일을 읽는방법으로 구현

=> 주어진 CSV 파일에 "###,###" 형식의 데이터가 포함되어 있어 ','구분자로 데이터 읽어올때 예외처리

* 주택 금융 공급 금융기관(은행) 목록을 출력하는 API 를 개발

=> Repository를 통한 전체조회 

* 연도별 각 금융기관의 지원금액 합계를 출력하는 API 개발

=> 전체리스트 조회 후 연도별총합계, 연도별은행별합계를 만든 후 각각을 재조합하여 출력함

* 각 연도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발

=> 전체리스트 조회 후 연도별은행별합계를 만든 후 오름차순 정렬하여 최대값 출력함

* 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발

=> 은행명을 파라메터로 받아서 전체리스트 조회 후 입력한 은행의 평균금액을 만든 후 오름차순 정렬하여 최대값, 최소값 출력함

---------------------------------------


> 실행방법

**접속**

http://localhost:80/api/bank

**API Specifications**

[POST] /api/banks/q1

[GET] /api/banks/q2

[GET] /api/banks/q3

[GET] /api/banks/q4

[GET] /api/banks/q5/외환은행



