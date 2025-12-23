package com.ph.service;

import com.ph.domain.entities.AdvertType;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.BuiltInFieldException;
import com.ph.exception.customs.InvalidTitleException;
import com.ph.payload.mapper.AdvertMapper;
import com.ph.payload.request.AdvertTypeRequest;
import com.ph.repository.AdvertTypeRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvertTypeServiceTest {

    @Mock
    private AdvertTypeRepository repository;

    @Mock
    private AdvertMapper mapper;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private AdvertTypeService service;

    @Test
    void create_throwsOnDuplicateTitle() {
        AdvertTypeRequest request = org.mockito.Mockito.mock(AdvertTypeRequest.class);
        when(request.getTitle()).thenReturn("Rent");
        when(repository.existsByTitleIgnoreCase("Rent")).thenReturn(true);
        when(messageUtil.getMessage("error.advert.type.exist")).thenReturn("exists");

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    void create_throwsOnInvalidTitle() {
        AdvertTypeRequest request = org.mockito.Mockito.mock(AdvertTypeRequest.class);
        when(request.getTitle()).thenReturn("Rent123");
        when(repository.existsByTitleIgnoreCase("Rent123")).thenReturn(false);
        when(messageUtil.getMessage("error.advert.type.invalid.title")).thenReturn("invalid");

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(InvalidTitleException.class);
    }

    @Test
    void delete_throwsWhenBuiltIn() {
        AdvertType advertType = new AdvertType();
        advertType.setBuiltIn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(advertType));
        when(messageUtil.getMessage("error.advert.type.not.delete")).thenReturn("built-in");

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(BuiltInFieldException.class);
    }

    @Test
    void update_throwsOnInvalidTitle() {
        AdvertTypeRequest request = org.mockito.Mockito.mock(AdvertTypeRequest.class);
        when(request.getTitle()).thenReturn("Rent123");
        AdvertType existing = new AdvertType();
        existing.setBuiltIn(false);
        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.existsByTitleIgnoreCase("Rent123")).thenReturn(false);
        when(messageUtil.getMessage("error.advert.type.invalid.title")).thenReturn("invalid");

        assertThatThrownBy(() -> service.update(2L, request))
                .isInstanceOf(InvalidTitleException.class);
    }
}
