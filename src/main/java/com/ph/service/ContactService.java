package com.ph.service;
import com.ph.domain.entities.Contact;
import com.ph.payload.mapper.UserMapper;
import com.ph.payload.request.ContactRequest;
import com.ph.payload.response.ContactResponse;
import com.ph.repository.ContactRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final MessageUtil messageUtil;
    private final UserMapper userMapper;

    // Not :J02 - Save() *************************************************************************
    public ResponseEntity<String> save(ContactRequest request) {
      Contact contact= request.get();
      contactRepository.save(contact);
        return ResponseEntity.ok(messageUtil.getMessage("success.contact.saved"));
    }

    // Not :J01 - GetAll() *************************************************************************
    public ResponseEntity<Page<ContactResponse>> getAllContact(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return ResponseEntity.ok(contactRepository.findAll(pageable).map(userMapper::toContactResponse));
    }
}
