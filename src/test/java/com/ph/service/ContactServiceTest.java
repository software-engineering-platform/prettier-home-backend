package com.ph.service;

import com.ph.domain.entities.Contact;
import com.ph.exception.customs.ValuesNotMatchException;
import com.ph.payload.mapper.UserMapper;
import com.ph.payload.response.ContactResponse;
import com.ph.repository.ContactRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ContactService contactService;

    @Test
    void getAllContact_throwsOnInvalidDateRange() {
        when(messageUtil.getMessage("error.report.date")).thenReturn("invalid");

        assertThatThrownBy(() -> contactService.getAllContact("q", true, LocalDate.now(), LocalDate.now().minusDays(1), 0, 20, "createdAt", "ASC"))
                .isInstanceOf(ValuesNotMatchException.class);
    }

    @Test
    void toggleMessageStatus_flipsStatus() {
        Contact contact = new Contact();
        contact.setStatus(true);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(userMapper.toContactResponse(contact)).thenReturn(new ContactResponse());

        contactService.toggleMessageStatus(1L);

        assertThat(contact.isStatus()).isFalse();
    }
}
