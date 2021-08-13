package my.rinat.tatbash.welcomebot.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.rinat.tatbash.welcomebot.infrastructure.config.AppConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

  private final AppConfig config;

  public boolean hasKeyWord(Message message) {
    if (message == null) {
      return false;
    }
    return StringUtils.containsIgnoreCase(message.getText(), config.getKeyWord());
  }

  public boolean hasNewChatMembers(Message message) {
    if (message == null) {
      return false;
    }
    return !CollectionUtils.isEmpty(message.getNewChatMembers());
  }

  public Message extractMessage(Update update) {
    return Optional.ofNullable(update.getMessage()).orElse(update.getEditedMessage());
  }

  public SendMessage response(Message message) {
    SendMessage response = new SendMessage();
    response.setChatId(message.getChatId().toString());
    response.setText("Hello World!");
    response.setReplyToMessageId(message.getMessageId());
    return response;
  }
}
