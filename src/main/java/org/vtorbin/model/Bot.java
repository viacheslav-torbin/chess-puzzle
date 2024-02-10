package org.vtorbin.model;

import lombok.Getter;
import okhttp3.*;
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
                    System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
                    if (message.getText().trim().startsWith("/gen")) {
                        String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR";
                        String encodedFen = URLEncoder.encode(fen, "UTF-8");
                        String imageUrl = "https://fen2image.chessvision.ai/rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1?turn=black&pov=black";  // Note: Added .png extension
                        String destinationFile = "downloaded_chessboard.png";

                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(imageUrl)
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (response.isSuccessful()) {
                                try (ResponseBody body = response.body(); FileOutputStream outputStream = new FileOutputStream(destinationFile)) {
                                    if (body != null) {
                                        outputStream.write(body.bytes());
                                    }
                                }
                                System.out.println("Image downloaded successfully.");
                            } else {
                                System.out.println("HTTP request failed with response code: " + response.code());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
