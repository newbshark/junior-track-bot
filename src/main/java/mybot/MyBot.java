package mybot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import io.github.cdimascio.dotenv.Dotenv;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "JuniorTrackBot"; // Укажи здесь юзернейм твоего бота из BotFather
    }

    @Override
    public String getBotToken() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (userMessage.equals("/start")) {
                // Приветственное сообщение с выбором навыков
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Привет! Я помогу тебе выбрать направление в программировании.\n\nКакие навыки у тебя уже есть?");

                // Вызываем метод для добавления кнопок
                configureKeyboard(message);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (userMessage.equals("/help")) {
                // Команда /help - информация о боте
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Привет! Я помогу тебе выбрать направление в программировании.\n\nВот какие команды я поддерживаю:\n\n" +
                        "/start — Начало общения с ботом\n" +
                        "/help — Показать эту информацию");

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                // Эхо-ответ (ответ на любую команду, не связанную с /start или /help)
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Ты выбрал: " + userMessage + "\n(На следующем шаге я научусь давать совет!)");

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Метод для настройки клавиатуры с кнопками
    private void configureKeyboard(SendMessage message) {
        // Создаем клавиатуру
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);  // Это сделает кнопки удобными для пользователя

        // Создаем строки кнопок
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Строки кнопок
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Я только начинаю");
        row1.add("HTML/CSS");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Python");
        row2.add("Java");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Другое");

        // Добавляем строки в клавиатуру
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        // Устанавливаем клавиатуру для сообщения
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
    }
}
