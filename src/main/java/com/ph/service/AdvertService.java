package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.AdvertType;
import com.ph.domain.entities.User;
import com.ph.payload.mapper.AdvertMapper;
import com.ph.payload.request.AdvertRequest;
import com.ph.payload.request.AdvertRequestForUpdateByAdmin;
import com.ph.payload.request.AdvertRequestForUpdateByCustomer;
import com.ph.payload.response.AdvertResponse;
import com.ph.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {


    // NOT:  helperMethodSlugMaker() ************************************************************

    public static String slugMaker(String title,Long id){
        return title.toLowerCase().replace(" ", "-")+System.currentTimeMillis()+id;
    }


    // NOT:  helperMethodRoles() ************************************************************

    public static List<String> roles(User  user){
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private final AdvertRepository repository;
    private final AdvertTypeService typeService;
    private final AdvertMapper mapper;
    //private final CountryService countryService;
    //private final CityService cityService;
    //private final DistrictService districtService;
    //private final CategoryService categoryService;
    //private final ImageService imageService;



    // NOT:  helperMethodGetById() ************************************************************

    public Advert getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Advert not found by id"+id));
    }


    // NOT:A02 / getAdvertsByCities() ************************************************************
    public List<Object[]> getAdvertsByCities() {
        return repository.getAdvertsByCities();

    }


    // NOT:A03 / getAdvertsByCategories() ************************************************************
    public List<Object[]> getAdvertsByCategories() {
        return repository.getAdvertsByCategories();
    }


    // NOT:A04 / getMostPopularAdverts() ************************************************************
    public Page<AdvertResponse> getMostPopularAdverts(Integer amount) {
        if (amount == null) {
            amount = 10;
        }
        Pageable pageable = PageRequest.of(0, amount);
        return repository.findPopularAdverts(pageable).map(mapper::toResponse);
    }


    // NOT:A05 / getForCustomerById() ************************************************************

    public Page<AdvertResponse> getByCustomerPage(  int page, int size, String sort, String type, UserDetails userDetails, String authorizationHeader) {
        User user = (User) userDetails;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(type.equals("desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }

        return    repository.findByUser_Id(user.getId(),pageable).map(mapper::toResponse);


    }

    // NOT:A06 / getByAdminPage() ************************************************************

    public Page<AdvertResponse> getByAdminOrAnonymousPage(String query, Long categoryId, Long advertTypeId,
                                                          Integer priceStart, Integer priceEnd,
                                                          Integer status, Pageable pageable) {
        return repository.findByAdminPage(query, categoryId, advertTypeId, priceStart, priceEnd, status, pageable)
                .map(mapper::toResponse);
    }



    // NOT:A07 / getBySlug() ************************************************************

    public ResponseEntity<AdvertResponse> getBySlug(String slug) {
        Advert advert = repository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Advert not found by slug"+slug));
        return ResponseEntity.ok(mapper.toResponse(advert));
    }


    // NOT:A08 / getForCustomer() ************************************************************

    public ResponseEntity<AdvertResponse> getByCustomer(Long id, UserDetails userDetails, String authorizationHeader) {
        User user = (User) userDetails;
        if (user.getAdverts().stream().noneMatch(adv -> adv.getId().equals(id))) {
            throw new RuntimeException("You Can Not Update This Advert , Because You Don't Have Permission To Update Advert");
        }
        Advert advert=getById(id);
        return ResponseEntity.ok(mapper.toResponse(advert));
    }


    // NOT:A09 / getForAdmin() ************************************************************
    public ResponseEntity<AdvertResponse> getByAdmin(Long id) {
        Advert advert=getById(id);
        return ResponseEntity.ok(mapper.toResponse(advert));
    }



    // NOT:A10 / save() ************************************************************

    public ResponseEntity<AdvertResponse> create(AdvertRequest request,UserDetails userDetails,String authorizationHeader) {
        User user = (User) userDetails;
        AdvertType advertType= typeService.getById(request.getAdvertTypeId());
        // Country country = countryService.getById(request.getCountryId());
        //City city = cityService.getById(request.getCityId());
        //District district = districtService.getById(request.getDistrictId());
        //Category category = categoryService.getById(request.getCategoryId());
        //List<Image> image = imageService.getById(request.getImageId());


        Advert advert= mapper.toEntity(request);
        advert.setAdvertType(advertType);
        advert.setUser(user);
        //advert.setCountry(country);
        //advert.setCity(city);
        //advert.setDistrict(district);
        //advert.setCategory(category);
        //advert.setImage(image);
        // advert.setAdvertType(advertType);


        Advert savedAdvert=repository.save(advert);
        savedAdvert.setSlug(slugMaker(advert.getTitle(),savedAdvert.getId()) );
        repository.save(savedAdvert);
        return ResponseEntity.ok(mapper.toResponse(savedAdvert));
    }



    // NOT:A11 / updateForCustomer() ************************************************************

    public ResponseEntity<AdvertResponse> updateForCustomer(Long id, AdvertRequestForUpdateByCustomer request
            , UserDetails userDetails, String authorizationHeader) {
        Advert advert=getById(id);
        if (advert.isBuiltIn()){
            throw new RuntimeException("Advert is cannot be update");
        }

        User user = (User) userDetails;

        if (user.getAdverts().stream().noneMatch(adv -> adv.getId().equals(id))) {
            throw new RuntimeException("You Can Not Update This Advert , Because You Don't Have Permission To Update Advert");
        }

        AdvertType advertType=typeService.getById(request.getAdvertTypeId());
        //Country country = countryService.getById(request.getCountryId());
        //City city = cityService.getById(request.getCityId());
        //District district = districtService.getById(request.getDistrictId());
        //Category category = categoryService.getById(request.getCategoryId());


        Advert toEntityForUpdate=mapper.toEntityForUpdateCustomer(request);
        toEntityForUpdate.setAdvertType(advertType);

        // toEntityForUpdate.setCountry(country);
        //toEntityForUpdate.setCity(city);
        //toEntityForUpdate.setDistrict(district);
        //toEntityForUpdate.setCategory(category);

        toEntityForUpdate.setUser(advert.getUser());
        toEntityForUpdate.setId(id);
        toEntityForUpdate.setSlug(slugMaker(toEntityForUpdate.getTitle(),id));
        Advert savedAdvert=repository.save(toEntityForUpdate);
        return ResponseEntity.ok(mapper.toResponse(savedAdvert));


    }


    // NOT:A12 / updateForAdmin() ************************************************************

    public ResponseEntity<AdvertResponse> update(Long id, AdvertRequestForUpdateByAdmin request) {
        Advert advert=getById(id);
        if (advert.isBuiltIn()){
            throw new RuntimeException("Advert is cannot be update");
        }
        AdvertType advertType=typeService.getById(request.getAdvertTypeId());
        //Country country = countryService.getById(request.getCountryId());
        //City city = cityService.getById(request.getCityId());
        //District district = districtService.getById(request.getDistrictId());
        //Category category = categoryService.getById(request.getCategoryId());
        //AdvertType advertType = typeService.getById(request.getAdvertTypeId());

        Advert toEntityForUpdate=mapper.toEntityForUpdate(request);
        toEntityForUpdate.setAdvertType(advertType);

        // toEntityForUpdate.setCountry(country);
        //toEntityForUpdate.setCity(city);
        //toEntityForUpdate.setDistrict(district);
        //toEntityForUpdate.setCategory(category);

        toEntityForUpdate.setUser(advert.getUser());
        toEntityForUpdate.setId(id);
        toEntityForUpdate.setSlug(slugMaker(toEntityForUpdate.getTitle(),id));
        Advert savedAdvert=repository.save(toEntityForUpdate);
        return ResponseEntity.ok(mapper.toResponse(savedAdvert));




    }


    // NOT:A13 / delete() ************************************************************

    public ResponseEntity<AdvertResponse> delete(Long id) {
        Advert advert=getById(id);
        if (advert.isBuiltIn()){
            throw new RuntimeException("Advert is cannot be delete");
        }
        repository.delete(advert);
        return ResponseEntity.ok(mapper.toResponse(advert));
    }










    // NOT:A06 / getByAdminPage() ************************************************************ (ESKİ VE EKSİK OLAN)
/*
/    public Page<AdvertResponse> getByAdminPage(Pageable pageable,String query) {
//        return
//         repository.searchAdvertByPage(query,pageable).map(mapper::toResponse);
//    }
 */


}
