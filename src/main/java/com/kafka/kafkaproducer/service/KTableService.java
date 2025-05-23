package com.kafka.kafkaproducer.service;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KTableService {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    public void buildPipeline(StreamsBuilder sb) {

        KTable<String, String> leftTable = sb.stream("leftTopic", Consumed.with(Serdes.String(), Serdes.String())).toTable();
        KTable<String, String> rightTable = sb.stream("rightTopic", Consumed.with(Serdes.String(), Serdes.String())).toTable();

        ValueJoiner<String, String, String> joiner = (l, r) -> "[JOIN] " + l + " ~ " + r;

        KTable<String, String> joinedTable = leftTable.join(rightTable, joiner);
        joinedTable.toStream().to("joinTopic");

    }
}
