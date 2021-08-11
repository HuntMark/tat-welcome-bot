package my.rinat.tatbash.welcomebot.welcome;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.springframework.util.CollectionUtils.isEmpty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.rinat.tatbash.welcomebot.infrastructure.config.AppConfig;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
@Service
public class WelcomeService {

  private final AppConfig config;

  public boolean hasResponse(Update update) {
    if (update == null) {
      return false;
    }
    Message message = update.getMessage();
    if (message == null) {
      message = update.getEditedMessage();
    }
    if (message == null) {
      return false;
    }
    return containsIgnoreCase(message.getText(), config.getKeyWord()) || !isEmpty(message.getNewChatMembers());
  }

  public SendMessage response(Update update) {
    SendMessage response = new SendMessage();
    Message message = update.getMessage();
    if (message == null) {
      message = update.getEditedMessage();
    }
    response.setChatId(message.getChatId().toString());
    response.setText("Hello World!");
    response.setReplyToMessageId(message.getMessageId());
    return response;
  }
}
