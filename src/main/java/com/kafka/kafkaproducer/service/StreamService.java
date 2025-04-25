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
    public void buildPipeline(StreamsBuilder streamsBuilder) {
//        KStream<String, String> stream = streamsBuilder.stream("testTopic", Consumed.with(STRING_SERDE, STRING_SERDE));
//        stream.print(Printed.toSysOut());
//        stream.filter((key, value) -> value.toLowerCase().contains("hello")).to("helloTopic");

        KStream<String, String> leftStream = streamsBuilder.stream("leftTopic", Consumed.with(STRING_SERDE, STRING_SERDE))
            .selectKey((k, v) -> v.substring(0, v.indexOf(":")));

        KStream<String, String> rightStream = streamsBuilder.stream("rightTopic", Consumed.with(STRING_SERDE, STRING_SERDE))
            .selectKey((k, v) -> v.substring(0, v.indexOf(":")));
        ;

        leftStream.print(Printed.toSysOut());
        rightStream.print(Printed.toSysOut());

        KStream<String, String> joinStream = leftStream.join(rightStream,
            (value1, value2) -> value1 + ":" + value2, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1)));

        joinStream.print(Printed.toSysOut());
        joinStream.to("joinTopic");
    }

}
