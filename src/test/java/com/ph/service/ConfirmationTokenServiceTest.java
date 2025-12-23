package com.ph.service;

import com.ph.domain.entities.ConfirmationToken;
import com.ph.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenService confirmationTokenService;

    @Test
    void saveConfirmationToken_delegatesToRepository() {
        ConfirmationToken token = new ConfirmationToken();

        confirmationTokenService.saveConfirmationToken(token);

        verify(confirmationTokenRepository).save(token);
    }

    @Test
    void deleteConfirmationToken_delegatesToRepository() {
        confirmationTokenService.deleteConfirmationToken(5L);

        verify(confirmationTokenRepository).deleteById(5L);
    }

    @Test
    void findConfirmationTokenByToken_returnsRepositoryResult() {
        ConfirmationToken token = new ConfirmationToken();
        when(confirmationTokenRepository.findConfirmationTokenByConfirmationToken("abc")).thenReturn(Optional.of(token));

        Optional<ConfirmationToken> result = confirmationTokenService.findConfirmationTokenByToken("abc");

        assertThat(result).contains(token);
    }

    @Test
    void deleteConfirmationTokenByUserId_delegatesToRepository() {
        confirmationTokenService.deleteConfirmationTokenbyUserId(9L);

        verify(confirmationTokenRepository).deleteByUser_Id(9L);
    }
}
