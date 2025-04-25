package com.kafka.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StreamService {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    public void buildPipeline(StreamsBuilder sb) {
        //        KStream<String, String> stream = streamsBuilder.stream("testTopic", Consumed.with(STRING_SERDE, STRING_SERDE));
        //        stream.print(Printed.toSysOut());
        //        stream.filter((key, value) -> value.toLowerCase().contains("hello")).to("helloTopic");

        KStream<String, String> left = sb.stream("leftTopic", Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, String> right = sb.stream("rightTopic", Consumed.with(Serdes.String(), Serdes.String()));

        // 2) 값 조인 로직 정의
        ValueJoiner<String, String, String> joiner = (l, r) -> "[JOIN] " + l + " ~ " + r;
        ValueJoiner<String, String, String> outerJoiner = (l, r) -> "[OUTER] " + l + " * " + r;

        // 3) 조인 윈도우 (1분, 지연 허용 없음)
        JoinWindows window = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1));

        // 4) 정규 조인
        KStream<String, String> joined = left.join(right, joiner, window);
        // 5) 외부(Outer) 조인
        KStream<String, String> outerJoined = left.outerJoin(right, outerJoiner, window);

        // 6) 로그로 출력 (디버깅용)
        joined.print(Printed.<String, String>toSysOut().withLabel("JOINED"));
        outerJoined.print(Printed.<String, String>toSysOut().withLabel("OUTER"));

        // 7) 결과를 토픽에도 보내기
        joined.to("joinTopic");
        outerJoined.to("joinTopic");

    }

}
