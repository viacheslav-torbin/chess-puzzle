package org.vtorbin.model;

import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.vtorbin.util.Parser;
import org.vtorbin.util.Reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Map;
import java.util.HashMap;

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
                    //TODO: separate commands logic into other class
                    System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
                    if (message.getText().trim().startsWith("/gen")) {
                        try {
                            String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR";
                            String encodedFen = URLEncoder.encode(fen, "UTF-8");
                            String imageUrl = "https://www.fen-to-image.com/image/" + encodedFen + ".png";
                            String destinationFile = "downloaded_chessboard.png";

                            URL url = new URL(imageUrl);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                            try (InputStream inputStream = connection.getInputStream();
                                 FileOutputStream outputStream = new FileOutputStream(destinationFile)) {

                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                            } finally {
                                connection.disconnect();
                            }

                            System.out.println("Image downloaded successfully.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

                    //TODO: verify elo

//                        int elo = Integer.parseInt(message.getText().split(" ")[1]);
//                        Reader reader = new Reader();
//                        String puzzle = reader.findByDifficulty(elo);
//                        Parser parser = new Parser(puzzle);
//                        String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR";
//                        String color = "black";
//                        StringBuilder stringBuilder = new StringBuilder("https://www.fen-to-image.com/image/" + fen );
//                        Map<String, String> params = new HashMap<>();
//                        params.put("turn", color);
//                        params.put("pov", color);
//                        for (Map.Entry<String, String> entry : params.entrySet()) {
//                            stringBuilder.append(entry.getKey())
//                                    .append("=")
//                                    .append(entry.getValue())
//                                    .append("&");
//                        }
//                        URL url = new URL(stringBuilder.toString());
//                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                        con.setRequestMethod("GET");
//                        try (InputStream inputStream = con.getInputStream();
//                             FileOutputStream outputStream = new FileOutputStream("image.png")) {
//                            byte[] buffer = new byte[1024];
//                            int bytesRead;
//                            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                                outputStream.write(buffer, 0, bytesRead);
//                            }
//                        } finally {
//                            con.disconnect();
//                        }
//                        SendPhoto sendPhoto = new SendPhoto(
//                                String.valueOf(chat.getId()),
//                                null,
//                                new InputFile(new File("image.png")),
//                                color + "to move",
//                                false,
//                                null,
//                                null,
//                                null,
//                                null,
//                                false,
//                                false,
//                                false,
//                                null
//                        );
//                        execute(sendPhoto);
//                    }
//                }
//
//
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                throw new RuntimeException(e);
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}
