package com.ph.service;


import com.ph.aboutexcel.ExcelWriteService;
import com.ph.domain.entities.Advert;
import com.ph.payload.mapper.excelMapper.AdvertExcelMapper;
import com.ph.payload.resource.AdvertResource;
import com.ph.repository.AdvertRepository;
import com.ph.repository.AdvertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AdvertRepository advertRepository;
    private final AdvertExcelMapper advertExcelMapper;
    private final ExcelWriteService excelWriteService;



    // Not: get most popular properties


    public ResponseEntity<?> getMostPopularProperties(Integer amount) {

       List<Advert> popularAdverts = advertRepository.findPopularAdverts(amount);

       List<AdvertResource> advertResources =  popularAdverts.stream().map(advertExcelMapper::mapToResource).toList();

       excelWriteService.writeToExcel(advertResources,"MostPopularProperties.xlsx","MostPopularProperties");

       return  ResponseEntity.ok(advertResources);

    }







}
