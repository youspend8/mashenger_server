package com.mashenger.server.listener;

import com.mashenger.server.constant.MessageType;
import com.mashenger.server.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = accessor.getSessionAttributes().get("username").toString();
        if (username != null) {
            System.out.println(">> User disconnected :: " + username);

            Message message = new Message();
            message.setType(MessageType.LEAVE);
            message.setUsername(username);

            messageSendingOperations.convertAndSend("/topic/public", message);
        }
    }
}
