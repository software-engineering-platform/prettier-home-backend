package com.ph.service;

import com.ph.domain.entities.ConfirmationToken;
import com.ph.domain.entities.User;
import com.ph.exception.customs.ValuesNotMatchException;
import com.ph.payload.mapper.UserMapper;
import com.ph.payload.request.ChangePasswordRequest;
import com.ph.payload.request.ResetPasswordRequest;
import com.ph.payload.request.UserSaveRequest;
import com.ph.repository.FavoritesRepository;
import com.ph.repository.UserRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ProfilePhotoService profilePhotoService;

    @Mock
    private FavoritesRepository favoritesRepository;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @InjectMocks
    private UserService userService;

    @Test
    void saveUser_hashesPasswordAndSendsConfirmation() {
        UserSaveRequest request = org.mockito.Mockito.mock(UserSaveRequest.class);
        User user = new User();
        user.setPasswordHash("raw");
        user.setEmail("user@example.com");
        user.setPhone("(123) 456-7890");

        when(request.get()).thenReturn(user);
        when(passwordEncoder.encode("raw")).thenReturn("hashed");
        when(userRepository.existsByPhone(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.saveUser(request);

        ArgumentCaptor<User> saved = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(saved.capture());
        assertThat(saved.getValue().getPasswordHash()).isEqualTo("hashed");
        verify(confirmationTokenService).saveConfirmationToken(any(ConfirmationToken.class));
        verify(emailService).sendConfirmationMail(eq("user@example.com"), anyString(), any());
    }

    @Test
    void resetPassword_updatesPasswordHash() {
        User user = new User();
        user.setPasswordHash("old");
        when(userRepository.findByResetPasswordCode("code")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("new")).thenReturn("hashed");

        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setCode("code");
        request.setPassword("new");
        userService.resetPassword(request);

        verify(userRepository).save(user);
        assertThat(user.getPasswordHash()).isEqualTo("hashed");
    }

    @Test
    void changeAuthenticatedUserPassword_throwsOnMismatch() {
        User user = new User();
        user.setPasswordHash("hashed");
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("wrong");
        request.setNewPassword("new");
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);
        when(messageUtil.getMessage("error.user.password.invalid")).thenReturn("invalid");

        assertThatThrownBy(() -> userService.changeAuthenticatedUserPassword(request, user))
                .isInstanceOf(ValuesNotMatchException.class);
    }
}
