package com.example.tgbot.service.manager.task;

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

import java.util.List;

import static com.example.tgbot.service.data.CallbackData.TASK;
import static com.example.tgbot.service.data.CallbackData.TASK_CREATE;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskManager extends AbstractManager {

    final AnswerMethodFactory answerMethodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public TaskManager(AnswerMethodFactory answerMethodFactory, KeyboardFactory keyboardFactory) {
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return mainMenu(message);
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        String callbackData  = callbackQuery.getData();
        switch (callbackData) {
            case TASK -> {
                return mainMenu(callbackQuery);
            }
            case TASK_CREATE -> {
                return create(callbackQuery);
            }
        }
        return null;
    }

    private BotApiMethod<?> mainMenu(Message message) {
        return answerMethodFactory.getSendMessage(
                message.getChatId(),
                """
                    🗂 Вы можете добавить домашнее задание вашему ученику
                """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Прикрепить домашнее задание"),
                        List.of(1),
                        List.of(TASK_CREATE)
                )
        );
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                """
                    🗂 Вы можете добавить домашнее задание вашему ученику
                """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Прикрепить домашнее задание"),
                        List.of(1),
                        List.of(TASK_CREATE)
                )
        );
    }

    private BotApiMethod<?> create(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                """
                    👤 Выберите ученика, которому хотите дать домашнее задание
                """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(TASK)
                )
        );
    }
}
