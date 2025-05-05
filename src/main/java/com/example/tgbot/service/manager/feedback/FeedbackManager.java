package com.example.tgbot.service.manager.feedback;

import com.example.tgbot.service.factory.AnswerMethodFactory;
import com.example.tgbot.service.factory.KeyboardFactory;
import com.example.tgbot.service.manager.AbstractManager;
import com.example.tgbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackManager extends AbstractManager {

    final AnswerMethodFactory answerMethodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public FeedbackManager(AnswerMethodFactory answerMethodFactory, KeyboardFactory keyboardFactory) {
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return answerMethodFactory.getSendMessage(
                message.getChatId(),
                """
                    📍 Ссылки для обратной связи
                    GitHub - https://github.com/pavelitel05
                    LinkedIn - https://linkedin.com/in/павел-кирсанов-62b762263
                    Telegram - https://t.me/pavelitel05
                """,
                null
        );
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                """
                    📍 Ссылки для обратной связи
                    GitHub - https://github.com/pavelitel05
                    LinkedIn - https://linkedin.com/in/павел-кирсанов-62b762263
                    Telegram - https://t.me/pavelitel05
                """,
                null
        );
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }
}
