package org.vtorbin.model;

import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
public class Bot extends TelegramLongPollingBot {
    private final String botToken = System.getenv("BOT_TOKEN");
    private final String botUsername = System.getenv("BOT_USERNAME");

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                execute(new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
