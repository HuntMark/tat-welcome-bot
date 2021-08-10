package my.rinat.tatbash.welcomebot.welcome;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@ExtendWith(MockitoExtension.class)
class WelcomeServiceTest {

  @InjectMocks
  private WelcomeService service;

  @Test
  void shouldBeNullSafe() {
    // given:
    final var message = new Message();

    // when:
    message.setText(null);
    message.setNewChatMembers(new ArrayList<>());

    // then:
    assertThat(service.hasResponse(message)).isFalse();

    // when:
    message.setNewChatMembers(null);

    // then:
    assertThat(service.hasResponse(message)).isFalse();

    // when message is null, then:
    assertThat(service.hasResponse(null)).isFalse();
  }

  @Test
  void shouldReturnTrueWhenContainsSignalWord() {
    // given:
    final var message = new Message();

    // when:
    message.setText("example");

    // then:
    assertThat(service.hasResponse(message)).isTrue();
  }

  @Test
  void shouldReturnTrueWhenNewMembersExist() {
    // given:
    final var message = new Message();

    // when:
    message.setNewChatMembers(Collections.singletonList(new User()));

    // then:
    assertThat(service.hasResponse(message)).isTrue();
  }

  @Test
  void shouldReturnFalseWhenDoesNotContainSignalWordOrAnyNewMember() {
    // given:
    final var message = new Message();

    // when:
    message.setText("no signal word");

    // then:
    assertThat(service.hasResponse(message)).isFalse();
  }
}
