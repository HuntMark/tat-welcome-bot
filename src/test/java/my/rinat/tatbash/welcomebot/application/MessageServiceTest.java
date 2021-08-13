package my.rinat.tatbash.welcomebot.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import my.rinat.tatbash.welcomebot.infrastructure.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

  @Mock
  private AppConfig config;

  @InjectMocks
  private MessageService service;

  @Test
  void shouldReturnFalseWhenMessageIsNull() {
    assertThat(service.hasKeyWord(null)).isFalse();
  }

  @Test
  void shouldReturnFalseWhenMessageDoesNotContainKeyWord() {
    // given:
    when(config.getKeyWord()).thenReturn("keyWord");

    // when:
    final var emptyMessage = new Message();

    // then:
    assertThat(service.hasKeyWord(emptyMessage)).isFalse();

    // when:
    final var messageWithoutKeyWord = new Message();
    messageWithoutKeyWord.setText("some text...");

    // then:
    assertThat(service.hasKeyWord(messageWithoutKeyWord)).isFalse();
  }

  @Test
  void shouldReturnTrueWhenMessageContainsKeyWord() {
    // given:
    when(config.getKeyWord()).thenReturn("keyWord");

    // when:
    final var messageWithKeyWord = new Message();
    messageWithKeyWord.setText("Some text... And keyWord...");

    // then:
    assertThat(service.hasKeyWord(messageWithKeyWord)).isTrue();
  }
}
