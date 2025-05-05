package com.example.tgbot.service.handler;

import com.example.tgbot.service.manager.feedback.FeedbackManager;
import com.example.tgbot.service.manager.help.HelpManager;
import com.example.tgbot.service.manager.task.TaskManager;
import com.example.tgbot.service.manager.timetable.TimetableManager;
import com.example.tgbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.example.tgbot.service.data.CallbackData.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackQueryHandler {
    final HelpManager helpManager;
    final FeedbackManager feedbackManager;
    final TimetableManager timetableManager;
    final TaskManager taskManager;

    @Autowired
    public CallbackQueryHandler(HelpManager helpManager,
                                FeedbackManager feedbackManager,
                                TimetableManager timetableManager,
                                TaskManager taskManager) {
        this.helpManager = helpManager;
        this.feedbackManager = feedbackManager;
        this.timetableManager = timetableManager;
        this.taskManager = taskManager;
    }

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();
        String keyWord = callbackData.split("_")[0];
        if (TIMETABLE.equals(keyWord)) {
            return timetableManager.answerCallbackQuery(callbackQuery, bot);
        }
        if (TASK.equals(keyWord)) {
            return taskManager.answerCallbackQuery(callbackQuery, bot);
        }
        switch (callbackData) {
            case HELP -> {
                return helpManager.answerCallbackQuery(callbackQuery, bot);
            }
            case FEEDBACK -> {
                return feedbackManager.answerCallbackQuery(callbackQuery, bot);
            }
        }
        return null;
    }
}
