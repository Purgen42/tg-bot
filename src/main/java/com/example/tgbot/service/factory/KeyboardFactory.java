package com.example.tgbot.service.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardFactory {

    public InlineKeyboardMarkup getInlineKeyboard(
            List<String> text,
            List<Integer> configuration,
            List<String> data
    ) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        int index = 0;
        for (Integer rowCount : configuration) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < rowCount; i++) {
                row.add(InlineKeyboardButton.builder()
                        .text(text.get(index))
                        .callbackData(data.get(index))
                        .build());
                index++;
            }
            keyboard.add(row);
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

    public ReplyKeyboardMarkup getReplyKeyboard(
            List<String> text,
            List<Integer> configuration
    ) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        int index = 0;
        for (Integer rowCount : configuration) {
            KeyboardRow row = new KeyboardRow();
            for (int i = 0; i < rowCount; i++) {
                row.add(KeyboardButton.builder()
                        .text(text.get(index))
                        .build());
                index++;
            }
            keyboard.add(row);
        }
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

}
