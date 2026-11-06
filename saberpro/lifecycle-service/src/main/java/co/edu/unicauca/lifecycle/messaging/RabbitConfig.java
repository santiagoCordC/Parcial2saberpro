package co.edu.unicauca.lifecycle.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

// Exchange, cola, binding y conversor JSON.
@Configuration
public class RabbitConfig {
    // Id del tipo de mensaje, igual en ambos servicios.
    public static final String SUBMIT_TYPE_ID = "SubmitQuestionForReview";

    @Value("${saberpro.amqp.exchange}")
    private String exchangeName;

    @Value("${saberpro.amqp.routing-key}")
    private String routingKey;

    @Value("${saberpro.amqp.queue}")
    private String queueName;

    @Bean
    public DirectExchange saberproExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue submitReviewQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding submitReviewBinding(Queue submitReviewQueue, DirectExchange saberproExchange) {
        return BindingBuilder.bind(submitReviewQueue).to(saberproExchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setIdClassMapping(Map.of(SUBMIT_TYPE_ID, SubmitQuestionForReview.class));
        typeMapper.setTrustedPackages("co.edu.unicauca.*");

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}
