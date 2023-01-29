package com.ead.authuser.publishers;

import com.ead.authuser.dto.UserEventDto;
import com.ead.authuser.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${api.broker.exchange.userEvent}")
    private String userEventExchange;


    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserEvent(UserEventDto userEventDto, ActionType actionType) {
        userEventDto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(userEventExchange, "", userEventDto);
    }

}
