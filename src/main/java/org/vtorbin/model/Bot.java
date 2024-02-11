package org.vtorbin.model;

import lombok.Getter;
import okhttp3.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.vtorbin.util.Parser;
import org.vtorbin.util.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Getter
public class Bot extends TelegramLongPollingBot {
    private final String botToken = System.getenv("BOT_TOKEN");
    private final String botUsername = System.getenv("BOT_USERNAME");

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                Message message = update.getMessage();
                Chat chat = message.getChat();
                if (message.hasText()) {
                    System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
                    if (message.getText().trim().startsWith("/gen")) {
                        //TODO: verify correctness of elo
                        int elo = Integer.parseInt(message.getText().split(" ")[1]);
                        Reader reader = new Reader();
                        String puzzle = reader.findByDifficulty(elo);
                        Parser parser = new Parser(puzzle);
                        String fen = parser.parseFen();
                        String color = parser.parseColor();
                        String baseUrl = "https://fen2image.chessvision.ai/" + fen;
                        HttpUrl.Builder urlBuilder
                                = HttpUrl.parse(baseUrl).newBuilder();
                        urlBuilder.addQueryParameter("pov", color);
                        urlBuilder.addQueryParameter("turn", color);
                        String destinationFile = "image.png";
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(urlBuilder.build().toString())
                                .build();
                        try (Response response = client.newCall(request).execute()) {
                            if (response.isSuccessful()) {
                                try (ResponseBody body = response.body();
                                     FileOutputStream outputStream = new FileOutputStream(destinationFile)) {
                                    if (body != null) {
                                        outputStream.write(body.bytes());
                                    }
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException("Can't write to file on path " + destinationFile, e);
                                } catch (IOException e) {
                                    throw new RuntimeException("Error while writing to file", e);
                                }
                                System.out.println("Image downloaded successfully.");
                            } else {
                                System.out.println("HTTP request failed with response code: " + response.code());
                            }
                        } catch (IOException e) {
                            throw new RuntimeException("Error while executing a request", e);
                        }
                        SendPhoto sendPhoto = new SendPhoto(
                                String.valueOf(chat.getId()),
                                null,
                                new InputFile(new File("image.png")),
                                color.toUpperCase()
                                        + " to move. "
                                        + parser.parseMoveCount()
                                        + " move(s)",
                                false,
                                null,
                                null,
                                null,
                                null,
                                false,
                                false,
                                false,
                                null
                        );
                        execute(sendPhoto);
                        String text =  "Solution: ||" + parser.parseSolution() + "|| \n" +
                                "Elo: " + elo;
                        execute(new SendMessage(
                                String.valueOf(chat.getId()),
                                null,
                                text,
                                "MarkdownV2",
                                true,
                                false,
                                null,
                                null,
                                null,
                                false,
                                false,
                                null,
                                null)
                        );
                    }
                }
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
