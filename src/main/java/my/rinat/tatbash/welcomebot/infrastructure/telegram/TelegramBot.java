package my.rinat.tatbash.welcomebot.infrastructure.telegram;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import my.rinat.tatbash.welcomebot.infrastructure.config.TelegramBotProperties;
import my.rinat.tatbash.welcomebot.welcome.WelcomeService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {

  private final TelegramBotProperties properties;
  private final WelcomeService service;

  @Override
  @SneakyThrows(TelegramApiException.class)
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && service.hasResponse(update.getMessage())) {
      execute(service.response(update));
    }
  }

  @PostConstruct
  @SneakyThrows({TelegramApiRequestException.class, TelegramApiException.class})
  public void selfRegister() {
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
