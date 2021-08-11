package my.rinat.tatbash.welcomebot.welcome;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import my.rinat.tatbash.welcomebot.infrastructure.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@ExtendWith(MockitoExtension.class)
class WelcomeServiceTest {

  @Mock
  private AppConfig config;

  @InjectMocks
  private WelcomeService service;

  @Test
  void shouldBeNullSafe() {
    // given:
    final var update = new Update();
    update.setMessage(new Message());

    // when:
    update.getMessage().setText(null);
    update.getMessage().setNewChatMembers(new ArrayList<>());

    // then:
    assertThat(service.hasResponse(update)).isFalse();

    // when:
    update.getMessage().setNewChatMembers(null);

    // then:
    assertThat(service.hasResponse(update)).isFalse();

    // when message is null, then:
    assertThat(service.hasResponse(null)).isFalse();
  }

  @Test
  void shouldReturnTrueWhenContainsSignalWord() {
    // given:
    final var update = new Update();
    update.setMessage(new Message());
    when(config.getKeyWord()).thenReturn("example");

    // when:
    update.getMessage().setText("example");

    // then:
    assertThat(service.hasResponse(update)).isTrue();
  }

  @Test
  void shouldReturnTrueWhenNewMembersExist() {
    // given:
    final var update = new Update();
    update.setMessage(new Message());

    // when:
    update.getMessage().setNewChatMembers(singletonList(new User()));

    // then:
    assertThat(service.hasResponse(update)).isTrue();
  }

  @Test
  void shouldReturnFalseWhenDoesNotContainSignalWordOrAnyNewMember() {
    // given:
    final var update = new Update();
    update.setMessage(new Message());
    when(config.getKeyWord()).thenReturn("example");

    // when:
    update.getMessage().setText("no key word");

    // then:
    assertThat(service.hasResponse(update)).isFalse();
  }
}
