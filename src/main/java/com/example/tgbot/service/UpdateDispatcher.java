package com.example.tgbot.service;

import com.example.tgbot.entity.user.User;
import com.example.tgbot.repository.UserRepository;
import com.example.tgbot.service.handler.CallbackQueryHandler;
import com.example.tgbot.service.handler.CommandHandler;
import com.example.tgbot.service.handler.MessageHandler;
import com.example.tgbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UpdateDispatcher {
    final MessageHandler messageHandler;
    final CommandHandler commandHandler;
    final CallbackQueryHandler callbackQueryHandler;

    @Autowired
    public UpdateDispatcher(MessageHandler messageHandler,
                            CommandHandler commandHandler,
                            CallbackQueryHandler callbackQueryHandler) {
        this.messageHandler = messageHandler;
        this.commandHandler = commandHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                if (message.getText().charAt(0) == '/') {
                    return commandHandler.answer(message, bot);
                }
            }
            return messageHandler.answer(message, bot);
        }
        log.warn("Unsupported update:" + update);
        return null;
    }
}
