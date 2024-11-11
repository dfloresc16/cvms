package com.upiita.msvc_cv.kafka;


import com.upiita.msvc_cv.dto.CVJoinFieldDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KafkaConfiguration {
    //Producer
    private Map<String, Object> producerProps(){
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092"); //broker a donde nos vamos a conectar
        props.put(ProducerConfig.RETRIES_CONFIG,0);//numero de reintentos
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,16384); //el tamañao del batch
        props.put(ProducerConfig.LINGER_MS_CONFIG,1);//el tiempo para acumular batches
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);//numero de batches max
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return props;
    }

    @Bean
    public KafkaTemplate<Integer, String> createTemplate(){
        Map<String, Object> senderProps = producerProps();
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<Integer, String>(senderProps);
        KafkaTemplate<Integer,String> template = new KafkaTemplate<>(pf);
        return template;
    }
}
