package my.rinat.tatbash.welcomebot.welcome;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.springframework.util.CollectionUtils.isEmpty;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class WelcomeService {

  public boolean hasResponse(Message message) {
    if (message == null) {
      return false;
    }
    return containsIgnoreCase(message.getText(), "example") || !isEmpty(message.getNewChatMembers());
  }

  public SendMessage response(Update update) {
    SendMessage response = new SendMessage();
    response.setChatId(update.getMessage().getChatId().toString());
    response.setText("Hello World!");
    return response;
  }
}
