package com.ph.controller;

import com.ph.payload.response.AdvertTypeResponse;
import com.ph.security.config.TokenProperties;
import com.ph.security.service.TokenService;
import com.ph.service.AdvertTypeService;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdvertTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdvertTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertTypeService advertTypeService;

    @MockBean
    private MessageUtil messageUtil;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenProperties tokenProperties;

    @Test
    void create_returnsOk() throws Exception {
        when(advertTypeService.create(org.mockito.Mockito.any()))
                .thenReturn(ResponseEntity.ok(new AdvertTypeResponse()));

        mockMvc.perform(post("/advert-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Rent\"}"))
                .andExpect(status().isOk());
    }
}
