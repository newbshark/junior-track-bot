package mybot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotMain {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            MyBot bot = new MyBot();

            try {
                botsApi.registerBot(bot);
                System.out.println("Bot started successfully!");
            } catch (TelegramApiException e) {
                if (e.getMessage().contains("Error removing old webhook")) {
                    System.out.println("Webhook cleared successfully (or no webhook was set)");
                    // Try registering again
                    botsApi.registerBot(bot);
                } else {
                    throw e;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}