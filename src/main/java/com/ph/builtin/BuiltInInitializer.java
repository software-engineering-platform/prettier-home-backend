package com.ph.builtin;

import com.ph.domain.entities.*;
import com.ph.domain.enums.KeyType;
import com.ph.domain.enums.StatusForAdvert;
import com.ph.repository.*;
import com.ph.security.role.Role;
import com.ph.utils.GeneralUtils;
import com.ph.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BuiltInInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdvertTypeRepository advertTypeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryPropertyKeyRepository propertyKeyRepository;
    private final CityRepository cityRepository;
    private final DistrictsRepository districtsRepository;
    private final CountriesRepository countriesRepository;
    private final AdvertRepository advertRepository;
    private final ImageRepository imageRepository;



    @Override
    public void run(String... args) throws Exception {
        // Initialize built-in data
        initializeUsers();
        initializeAdvertTypes();
        initializeCategories();
        initializePropertyKeys();
        if(advertRepository.count() == 0) {
            initializeDefaultAdvert();
        }

    }

    private void initializeUsers() {
        if (!userRepository.existsBy()) {
            User admin = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("admin@gmail.com")
                    .phone("(123) 456-7890")
                    .passwordHash(passwordEncoder.encode("admin123!"))
                    .role(Role.ADMIN)
                    .builtIn(true)
                    .build();
            userRepository.save(admin);

            User manager = User.builder()
                    .firstName("Jane")
                    .lastName("Doe")
                    .email("manager@gmail.com")
                    .phone("(123) 456-7891")
                    .passwordHash(passwordEncoder.encode("manager123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .build();
            userRepository.save(manager);

            User customer = User.builder()
                    .firstName("Adam")
                    .lastName("Born")
                    .email("adamd.born@gmail.com")
                    .phone("(123) 456-7892")
                    .passwordHash(passwordEncoder.encode("customer123!"))
                    .role(Role.CUSTOMER)
                    .builtIn(true)
                    .build();
            userRepository.save(customer);


        }
    }

    private void initializeAdvertTypes() {
        if (!advertTypeRepository.existsBy()) {
            advertTypeRepository.save(AdvertType.builder().title("Rent").builtIn(true).build());
            advertTypeRepository.save(AdvertType.builder().title("Sale").builtIn(true).build());
        }
    }

    private void initializeCategories() {
        if (!categoryRepository.existsBy()) {
            List<Category> categories = List.of(
                    Category.builder().title("House").icon("fa-solid fa-house").slug("house" + "-" + System.currentTimeMillis()).seq(1).active(true).builtIn(true).build(),
                    Category.builder().title("Apartment").icon("fa-solid fa-building").slug("apartment" + "-" + System.currentTimeMillis()).seq(2).active(true).builtIn(true).build(),
                    Category.builder().title("Office").icon("fa-solid fa-house-laptop").slug("office" + "-" + System.currentTimeMillis()).seq(3).active(true).builtIn(true).build(),
                    Category.builder().title("Villa").icon("fa-solid fa-place-of-worship").slug("villa" + "-" + System.currentTimeMillis()).seq(4).active(true).builtIn(true).build(),
                    Category.builder().title("Land").icon("fa-solid fa-mountain-sun").slug("land" + "-" + System.currentTimeMillis()).seq(5).active(true).builtIn(true).build(),
                    Category.builder().title("Shop").icon("fa-solid fa-store").slug("shop" + "-" + System.currentTimeMillis()).seq(6).active(true).builtIn(true).build()
            );
            categoryRepository.saveAll(categories);
        }
    }

    private void initializePropertyKeys() {
        if (!propertyKeyRepository.existsBy()) {
            //For House
            List<CategoryPropertyKey> houseProperties = List.of(
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Size").keyType(KeyType.NUMBER).prefix(null).suffix("m²").builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Bedrooms").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Bathrooms").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Garage").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Year of Build").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Furniture").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Maintenance Fee").keyType(KeyType.NUMBER).prefix("$").suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(1L).get()).name("Terrace").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build());

            propertyKeyRepository.saveAll(houseProperties);

            //For Apartment
            List<CategoryPropertyKey> apartmentProperties = List.of(
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Size").keyType(KeyType.NUMBER).prefix(null).suffix("m²").builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Bedrooms").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Bathrooms").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Parking Space").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Year of Build").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Furniture").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Maintenance Fee").keyType(KeyType.NUMBER).prefix("$").suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Balcony").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(2L).get()).name("Elevator").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build());

            propertyKeyRepository.saveAll(apartmentProperties);


            //For Office
            List<CategoryPropertyKey> officeProperties = List.of(
                    CategoryPropertyKey.builder().category(categoryRepository.findById(3L).get()).name("Size").keyType(KeyType.NUMBER).prefix(null).suffix("m²").builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(3L).get()).name("Parking Space").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(3L).get()).name("Year of Build").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(3L).get()).name("Furniture").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(3L).get()).name("Maintenance Fee").keyType(KeyType.NUMBER).prefix("$").suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(3L).get()).name("Elevator").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build());

            propertyKeyRepository.saveAll(officeProperties);

            //For Villa
            List<CategoryPropertyKey> villaProperties = List.of(
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Size").keyType(KeyType.NUMBER).prefix(null).suffix("m²").builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Bedrooms").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Bathrooms").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Garage").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Land Area").keyType(KeyType.NUMBER).prefix(null).suffix("m²").builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Year of Build").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Furniture").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("Swimming Pool").keyType(KeyType.TEXT).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(4L).get()).name("View").keyType(KeyType.TEXT).prefix(null).suffix(null).builtIn(true).build());

            propertyKeyRepository.saveAll(villaProperties);

            //For Land
            List<CategoryPropertyKey> landProperties = List.of(
                    CategoryPropertyKey.builder().category(categoryRepository.findById(5L).get()).name("Size").keyType(KeyType.NUMBER).prefix(null).suffix("m²").builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(5L).get()).name("Zoning").keyType(KeyType.TEXT).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(5L).get()).name("Access Road").keyType(KeyType.TEXT).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(5L).get()).name("Legal Status").keyType(KeyType.TEXT).prefix(null).suffix(null).builtIn(true).build());

            propertyKeyRepository.saveAll(landProperties);

            //For Shop
            List<CategoryPropertyKey> shopProperties = List.of(
                    CategoryPropertyKey.builder().category(categoryRepository.findById(6L).get()).name("Size").builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(6L).get()).name("Parking Space").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(6L).get()).name("Year of Build").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(6L).get()).name("Furniture").keyType(KeyType.BOOLEAN).prefix(null).suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(6L).get()).name("Maintenance Fee").keyType(KeyType.NUMBER).prefix("$").suffix(null).builtIn(true).build(),
                    CategoryPropertyKey.builder().category(categoryRepository.findById(6L).get()).name("Elevator").keyType(KeyType.NUMBER).prefix(null).suffix(null).builtIn(true).build());

            propertyKeyRepository.saveAll(shopProperties);
        }
    }

    private void initializeDefaultAdvert() {
        String[] images = new String[]{"static/house.jpg", "static/apartment.jpg", "static/office.jpg", "static/villa.jpg", "static/land.jpg", "static/shop.jpg"};
        List<Category> allCategories = categoryRepository.findAll();
        String[] titles = new String[]{"House", "Apartment", "Office", "Villa", "Land", "Shop"};
        Double[] prices = new Double[]{100000.0, 200000.0, 300000.0, 400000.0, 500000.0, 60000.0};
        Long[] cities = new Long[]{34L, 10L, 38L, 6L, 72L, 35L};
        Long[] districts = new Long[]{126L, 831L, 490L, 565L, 453L, 139L};
        Double[] lat = new Double[]{41.10500496452611, 39.91349760621756, 38.62218565477382, 39.97437614009851, 37.75093476161285, 38.46207669619371};
        Double[] lng = new Double[]{28.788342747629066, 28.14135381537319, 35.15306199396324, 32.86918909133035, 41.40976722322277, 27.17709951048468};
        String[] descriptions = {
                "Beautifully designed and spacious.",
                "Located in a prime neighborhood.",
                "Modern amenities for your comfort.",
                "Stunning views from every room.",
                "Close to schools, parks, and shopping centers.",
                "Secure and peaceful environment.",
                "Perfect for family living.",
                "High-quality construction and finishes.",
                "Easy access to public transportation.",
                "A great investment opportunity."
        }; // Generate a random 10-sentence description


        AdvertType advertType = advertTypeRepository.getReferenceById(1L);

        if (advertType != null && !allCategories.isEmpty()) {
            for (int i = 0; i < images.length; i++) {
                String imageFileName = images[i];
                String title = titles[i];
                Category category = allCategories.get(i);
                Double price = prices[i];
                Long city = cities[i];
                Long district = districts[i];
                Double latitude = lat[i];
                Double longitude = lng[i];
                String description = descriptions[i];


                Resource resource = new ClassPathResource(imageFileName);
                Image defaultImage = null;

                try {
                    InputStream inputStream = resource.getInputStream();
                    byte[] imageData = Files.readAllBytes(Paths.get(resource.getURI()));

                    defaultImage = Image.builder()
                            .data(ImageUtil.compressImage(imageData))
                            .name(title)
                            .type("image/jpg")
                            .featured(true)
                            .build();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String slug = GeneralUtils.generateSlug(title);

                Advert defaultAdvert = Advert.builder()
                        .title(title)
                        .description(description)
                        .slug(slug)
                        .price(price)
                        .statusForAdvert(StatusForAdvert.ACTIVATED)
                        .builtIn(true)
                        .isActive(true)
                        .viewCount(0)
                        .location(new Location(latitude, longitude))
                        .advertType(advertType)
                        .category(category)
                        .country(countriesRepository.findById(1L).get())
                        .city(cityRepository.findById(city).get())
                        .district(districtsRepository.findById(district).get())
                        .images(List.of(defaultImage))
                        .user(userRepository.findByEmail("admin@gmail.com").orElse(null))
                        .build();

                Advert savedAdvert = advertRepository.save(defaultAdvert);

                Image savedImage = savedAdvert.getImages().stream().findFirst().orElse(null);
                if (savedImage != null) {
                    savedImage.setAdvert(savedAdvert);
                    imageRepository.save(savedImage);
                }
            }
        }
    }




}
