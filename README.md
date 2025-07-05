## 🚀 Kafka-Producer & Streams Demo

**Kafka-Producer** 는 Spring Boot 3 기반으로 KafkaTemplate·Kafka Streams·커스텀 Serde를 활용해
① 광고 시청 로그(adLog) ② 구매 로그(purchaseLog)를 발행하고, 실시간 KStream/KTable 조인으로 광고 효과를 분석( AdEvaluationComplete 토픽) 하는 학습용 예제입니다.

---

## 🔧 주요 특징

✅ 멀티 Producer 팩토리

- 광고·구매·기본 메시지용 KafkaTemplate을 각각 등록하여 서로 다른 Serializer와 Bootstrap 서버를 분리 관리

✅ Kafka Streams 파이프라인

- 10 초 윈도우 KStream 조인 (left/right → joinTopic) KTable ↔ KTable 조인으로 광고 시청 ≥ 10 초 & 가격 < 1 백만인 건만 매칭하여 효과 분석

✅ REST 엔드포인트 /message

- 단순 텍스트를 testTopic 으로 발행해 빠르게 동작 확인

✅ 커스텀 JSON Serde

- PurchaseLog, WatchingAdLog, EffectOrNot 등 VO 별 Serializer/Deserializer 구현·등록

✅ 랜덤 샘플 데이터 제너레이터

- AdEvaluationService#sendNewMsg()가 자동으로 예시 데이터를 계속 생성·전송

---

## 📁 프로젝트 구조

```
kafka-producer/
└── src/
├── main/
│   ├── java/com/kafka/kafkaproducer/
│   │   ├── config/          # Kafka 설정 & 다중 ProducerFactory
│   │   ├── controller/      # REST → Kafka 발행
│   │   ├── service/         # Producer·Streams·Ad 평가 로직
│   │   ├── util/            # 커스텀 Serializer
│   │   └── vo/              # 도메인 VO(로그/평가 결과)
│   └── resources/
│       └── application.properties
├── test/…                   # 기본 Context 테스트
└── build.gradle, settings.gradle, gradle/
```

--- 

## 🛠️ 컴포넌트별 설명

| 모듈                  | 설명                                                           |
|---------------------|--------------------------------------------------------------|
| kafkaConfig         | ProducerFactory 3종 + Streams Config 등록, @EnableKafkaStreams  |
| ProducerService     | sendMsgForWatchingAdLog / sendMsgForPurchaseLog 등 토픽별 발행 메서드 |
| ProducerController  | /message?msg=hello → testTopic 전송                            |
| StreamService       | leftTopic·rightTopic 10 초 조인, 결과를 joinTopic으로                |
| AdEvaluationService | 광고 KTable ↔ 상품별 구매 KTable 조인으로 효과 분석 → AdEvaluationComplete  |

--- 

## 📚 기술 스택
| 범주        | 사용기술                                     | 
|-----------|------------------------------------------|
| Backend   | Java 17, Spring Boot 3, Spring Kafka     |
| Streaming | Kafka Streams API                        |
| Messaging | Apache Kafka                             |
| Test      | Spring Boot Test, Kafka Embedded(Test)   |

---

## ⚠️ 참고 / 한계
본 리포지토리는 학습·프로토타이핑 용도로 작성되었습니다.

실서비스 투입 시 보안(SSL, ACL), 스키마 관리(Avro/Schema Registry), 에러 핸들링·리트라이, 모니터링/메트릭스 등을 추가로 고려하세요.

예제 IP(15.164.*, 54.180.*, 3.37.*)는 퍼블릭 클러스터 데모용입니다. 사내망 혹은 로컬 Kafka로 교체를 권장합니다.

