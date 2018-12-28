package com.sun.rabbitMq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

	
	public static final String MIAOSHAQUEUE="miaoshaqueue";
	
	@Bean
	public Queue queue() {
		return new Queue(MIAOSHAQUEUE, true);
	}
	/*public static final String QUEUE="queue";
	public static final String TOPIC_QUEUE1="topic_queue1";
	public static final String TOPIC_QUEUE2="topic_queue2";
	public static final String TOPICEXCHANGE="topicexchange";
	
	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}
	
	@Bean
	public Queue topic_queue1() {
		return new Queue(TOPIC_QUEUE1, true);
	}
	
	@Bean
	public Queue topic_queue2() {
		return new Queue(TOPIC_QUEUE2, true);
	}
	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(TOPICEXCHANGE);
	}
	
	@Bean
	public Binding topicbinding1() {
		return BindingBuilder.bind(topic_queue1()).to(topicExchange()).with("key.1");
	}
	
	@Bean
	public Binding topicbinding2() {
		return BindingBuilder.bind(topic_queue2()).to(topicExchange()).with("key.#");
	}*/
	
}
