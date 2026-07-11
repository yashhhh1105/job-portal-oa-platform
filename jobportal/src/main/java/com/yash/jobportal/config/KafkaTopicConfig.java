package com.yash.jobportal.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    public static final String RESUME_SUBMITTED_TOPIC = "resume-submitted";
    public static final String RESUME_ANALYZED_TOPIC = "resume-analyzed";

    @Bean
    public NewTopic resumeSubmittedTopic() {
        return NewTopic.partitioned(RESUME_SUBMITTED_TOPIC, 1, (short) 1).build();
    }

    @Bean
    public NewTopic resumeAnalyzedTopic() {
        return NewTopic.partitioned(RESUME_ANALYZED_TOPIC, 1, (short) 1).build();
    }
}
