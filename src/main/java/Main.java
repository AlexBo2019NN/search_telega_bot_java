import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Alex Bocharov skype bam271074
 *         <p>
 *         Project under hackaton rzdhack.ru 27-29.11.2020
 *         <p>
 *         telegram bot search txt in google
 */


public class Main extends TelegramLongPollingBot {
//public class Main {
    public static void main(String[] args)  {
        String s = "Инновационная антенная мачта";
        Document doc = null;
        try {
            doc = Jsoup.connect("http://google.com/search?q=" + s).userAgent("Mozilla").get();
        } catch (IOException e){
            e.printStackTrace();
        }
        Elements titles = doc.select("title");

        //print all titles in main page
        for (Element e : titles) {
            System.out.println("text: " + e.text());
            System.out.println("html: " + e.html());
            //model.setMain(e.text());
        }
        //print all available links on page
        Elements links = doc.select("a[href]");
        for (Element l : links) {
            System.out.println("link: " + l.attr("abs:href"));
        }
        System.out.println(s);

        ApiContextInitializer.init();
        System.out.println("Bot started.");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Main());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }


    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(text);
        try {

            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update)  {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "How can I help?");
                    break;
                case "/setting":
                    sendMsg(message, "What kind of settings?");
                    break;
                default:
                    try {
                        //sendMsg(message, Searcher.getWeather(message.getText(), model));
                        sendMsg(message, Searcher.getInfo(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(message, "information is not found!");
                    }

            }
        }

    }


    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public String getBotUsername() {
        return "good_search_bot";
    }

    public String getBotToken() {
        return "1477220808:AAFKub3f216uvWLk_-8kUWTokbaivMNIjrg";
    }
}
