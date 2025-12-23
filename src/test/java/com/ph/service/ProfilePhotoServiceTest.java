package com.ph.service;

import com.ph.domain.entities.ProfilePhoto;
import com.ph.payload.mapper.ImageMapper;
import com.ph.repository.ProfilePhotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfilePhotoServiceTest {

    @Mock
    private ProfilePhotoRepository profilePhotoRepository;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ProfilePhotoService profilePhotoService;

    @Test
    void updateProfilePhoto_overwritesFields() {
        ProfilePhoto existing = new ProfilePhoto();
        existing.setName("old");
        existing.setType("old/type");
        existing.setData(new byte[] {1});

        ProfilePhoto incoming = new ProfilePhoto();
        incoming.setName("new");
        incoming.setType("new/type");
        incoming.setData(new byte[] {2, 3});

        MultipartFile multipartFile = org.mockito.Mockito.mock(MultipartFile.class);
        when(imageMapper.toProfilePhoto(multipartFile)).thenReturn(incoming);
        when(profilePhotoRepository.save(existing)).thenReturn(existing);

        ProfilePhoto result = profilePhotoService.updateProfilePhoto(existing, multipartFile);

        assertThat(result.getName()).isEqualTo("new");
        assertThat(result.getType()).isEqualTo("new/type");
        assertThat(result.getData()).isEqualTo(new byte[] {2, 3});
        verify(profilePhotoRepository).save(existing);
    }
}
