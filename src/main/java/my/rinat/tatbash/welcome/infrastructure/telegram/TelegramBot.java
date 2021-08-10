package my.rinat.tatbash.welcome.infrastructure.telegram;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import my.rinat.tatbash.welcome.infrastructure.config.TelegramBotProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {

  private final TelegramBotProperties properties;

  @Override
  @SneakyThrows(TelegramApiException.class)
  public void onUpdateReceived(Update update) {
    SendMessage response = new SendMessage();
    response.setChatId(update.getMessage().getChatId().toString());
    response.setText("Hello World!");
    execute(response);
  }

  @PostConstruct
  @SneakyThrows(TelegramApiRequestException.class)
  public void selfRegister() throws TelegramApiException {
    new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
  }

  @Override
  public String getBotUsername() {
    return this.properties.getName();
  }

  @Override
  public String getBotToken() {
    return this.properties.getToken();
  }
}
