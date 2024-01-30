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
import java.util.*;

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
    private final CountryRepository countriesRepository;
    private final AdvertRepository advertRepository;
    private final ImageRepository imageRepository;


    @Override
    public void run(String... args) throws Exception {
        // Initialize built-in data
        initializeUsers();
        initializeAdvertTypes();
        initializeCategories();
        initializePropertyKeys();
        initializeDefaultAdvert();
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
                    .enabled(true)
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
                    .enabled(true)
                    .build();
            userRepository.save(manager);

            User managerUfuk = User.builder()
                    .firstName("Ufuk")
                    .lastName("Kochisar")
                    .email("kochisarufuk@gmail.com")
                    .phone("(532) 789-7890")
                    .passwordHash(passwordEncoder.encode("managerUfuk123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerUfuk);

            User managerBilal = User.builder()
                    .firstName("Bilal")
                    .lastName("Guney")
                    .email("matematikcimbilal@gmail.com")
                    .phone("(532) 789-7891")
                    .passwordHash(passwordEncoder.encode("managerBilal123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerBilal);

            User managerGulsum = User.builder()
                    .firstName("Gulsum")
                    .lastName("Satic")
                    .email("gulsum.satic@gmail.com")
                    .phone("(532) 789-7892")
                    .passwordHash(passwordEncoder.encode("managerGulsum123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerGulsum);

            User managerEbubekir = User.builder()
                    .firstName("Ebubekir")
                    .lastName("Can")
                    .email("ebubekirc442@gmail.com")
                    .phone("(532) 789-7893")
                    .passwordHash(passwordEncoder.encode("managerEbubekir123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerEbubekir);

            User managerDogan = User.builder()
                    .firstName("Dogan")
                    .lastName("Merdan")
                    .email("adamd.born@gmail.com")
                    .phone("(532) 789-7894")
                    .passwordHash(passwordEncoder.encode("managerDogan123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerDogan);

            User managerZahide = User.builder()
                    .firstName("Zahide")
                    .lastName("Sarp")
                    .email("sarpzahide@gmail.com")
                    .phone("(532) 789-7895")
                    .passwordHash(passwordEncoder.encode("managerZahide123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerZahide);

            User managerZeynep = User.builder()
                    .firstName("Zeynep")
                    .lastName("Nune")
                    .email("zeynepnune@gmail.com")
                    .phone("(532) 789-7896")
                    .passwordHash(passwordEncoder.encode("managerZeynep123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerZeynep);

            User managerYasemin = User.builder()
                    .firstName("Yasemin")
                    .lastName("Dogan")
                    .email("yasemin@gmail.com")
                    .phone("(532) 789-7897")
                    .passwordHash(passwordEncoder.encode("managerYasemin123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerYasemin);

            User managerAbdurrahman = User.builder()
                    .firstName("Abdurrahman")
                    .lastName("Sahin")
                    .email("abdurrahman@gmail.com")
                    .phone("(532) 789-7898")
                    .passwordHash(passwordEncoder.encode("managerAbdurrahman123!"))
                    .role(Role.MANAGER)
                    .builtIn(true)
                    .enabled(true)
                    .build();
            userRepository.save(managerAbdurrahman);



            User customer = User.builder()
                    .firstName("Adam")
                    .lastName("Born")
                    .email("adamd.born@gmail.com")
                    .phone("(123) 456-7892")
                    .passwordHash(passwordEncoder.encode("customer123!"))
                    .role(Role.CUSTOMER)
                    .builtIn(true)
                    .enabled(true)
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
        if (!advertRepository.existsBy()) {
            List<Map<String, Object>> adverts = new ArrayList<>(Arrays.asList(
                    Map.of("title", "House",
                            "description", "Beautifully designed and spacious.",
                            "price", 100000.0,
                            "ccd", new Long[]{223L, 4160L, 48762L},
                            "address", "Sakura Cad. No: 15",
                            "location", new Double[]{41.10500496452611, 28.788342747629066},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "admin@gmail.com"),

                    Map.of("title", "Apartment",
                            "description", "Located in a prime neighborhood.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 3866L, 42218L},
                            "address", "Sakura Cad. No: 16",
                            "location", new Double[]{39.91349760621756, 28.14135381537319},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "admin@gmail.com"),

                    Map.of("title", "Office",
                            "description", "Modern amenities for your comfort.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 3264L, 38248L},
                            "address", "Sakura Cad. No: 17",
                            "location", new Double[]{38.62218565477382, 35.15306199396324},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "admin@gmail.com"),

                    Map.of("title", "Villa",
                            "description", "Stunning views from every room.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 1278L, 17726L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{39.97437614009851, 32.86918909133035},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "admin@gmail.com"),

                    Map.of("title", "Land",
                            "description", "Close to schools, parks, and shopping centers.",
                            "price", 500000.0,
                            "ccd", new Long[]{223L, 1359L, 19014L},
                            "address", "Sakura Cad. No: 19",
                            "location", new Double[]{37.75093476161285, 41.40976722322277},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "admin@gmail.com"),

                    Map.of("title", "Shop",
                            "description", "Secure and peaceful environment.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 3885L, 42218L},
                            "address", "Sakura Cad. No: 20",
                            "location", new Double[]{38.46207669619371, 27.17709951048468},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "admin@gmail.com"),

                    /////////////////////////////////////////////////////

                    Map.of("title", "Scenic Spacious Garden House",
                            "description", "A spacious and modern-designed house with a scenic view of nature in Çayyolu.",
                            "price", 56715.0,
                            "ccd", new Long[]{223L, 4127L, 48385L},
                            "address", "Çayyolu Mah. 456. St. No: 7",
                            "location", new Double[]{39.874185, 32.674029},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "kochisarufuk@gmail.com"),

                    Map.of("title", "Modern Apartment in Central Location",
                            "description", "A renovated and modern-designed apartment in a central location in Kızılay",
                            "price", 37375.0,
                            "ccd", new Long[]{223L, 4127L, 48385L},
                            "address", "Kızılay Mah. 789. Ave. No: 10",
                            "location", new Double[]{39.919838, 32.852380},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "kochisarufuk@gmail.com"),

                    Map.of("title", "Modern Office Space",
                            "description", "A spacious and user-friendly office space with modern infrastructure in Çankaya.",
                            "price", 43280.0,
                            "ccd", new Long[]{223L, 4127L, 48385L},
                            "address", "Çankaya Mah. 123. St. No: 5",
                            "location", new Double[]{39.8853321, 32.8554966},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "kochisarufuk@gmail.com"),

                    Map.of("title", "Luxury Villa With Pool",
                            "description", "A luxurious villa with a pool, nestled in nature in Gölbaşı.",
                            "price", 550000.0,
                            "ccd", new Long[]{223L, 4127L, 48390L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{39.7925289, 32.8066652},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "kochisarufuk@gmail.com"),

                    Map.of("title", "Suitable Land for Investment",
                            "description", "A completed infrastructure land suitable for investment in Etimesgut.",
                            "price", 75715.0,
                            "ccd", new Long[]{223L, 4127L, 48388L},
                            "address", "Etimesgut Mah. 7890. St. No: 12",
                            "location", new Double[]{39.9489423, 32.6620792},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "kochisarufuk@gmail.com"),

                    Map.of("title", "Shop on the Main Street",
                            "description", "A shop with high customer potential located on the main street in Mamak.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 4127L, 48397L},
                            "address", "Sakura Cad. No: 20",
                            "location", new Double[]{39.9329813, 32.9082154},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "kochisarufuk@gmail.com"),

                    ///////////////////////////////////////////////////////

                    Map.of("title", "Serene Family Home in İzmit",
                            "description", "Escape to this tranquil family home nestled in the heart of İzmit.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 4172L, 48925L},
                            "address", "İzmit Mah. 456. St. No: 7",
                            "location", new Double[]{40.7718611, 29.9498078},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "matematikcimbilal@gmail.com"),

                    Map.of("title", "Urban Oasis: Stylish Apartment in Gebze",
                            "description", "Discover this stylish apartment offering an urban oasis in Gebze.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 4172L, 48923L},
                            "address", "Gebze Mah. 789. Ave. No: 10",
                            "location", new Double[]{40.8006696, 29.431767},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "matematikcimbilal@gmail.com"),

                    Map.of("title", "Prime Office Space in İzmit Center",
                            "description", "Experience the convenience of this prime office space located in İzmit center.",
                            "price", 350000.0,
                            "ccd", new Long[]{223L, 4172L, 48925L},
                            "address", "İzmit Mah. 123. St. No: 5",
                            "location", new Double[]{40.7718611, 29.9498078},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "matematikcimbilal@gmail.com"),

                    Map.of("title", "Exclusive Retreat: Seaview Villa in Başiskele",
                            "description", "Indulge in luxury with this exclusive retreat boasting panoramic sea views in Başiskele.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 4172L, 48918L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{40.64592185, 29.951563320964866},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "matematikcimbilal@gmail.com"),

                    Map.of("title", "Investment Opportunity: Land in Darıca",
                            "description", "Don't miss this investment opportunity with this land located in Darıca.",
                            "price", 500000.0,
                            "ccd", new Long[]{223L, 4172L, 48920L},
                            "address", "Darıca Mah. 7890. St. No: 12",
                            "location", new Double[]{40.7574799, 29.3840563},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "matematikcimbilal@gmail.com"),

                    Map.of("title", "Commercial Hub: Shop in Körfez",
                            "description", "Become part of the bustling commercial hub with this shop in Körfez.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 4172L, 48929L},
                            "address", "Körfez Mah. 4567. Ave. No: 20",
                            "location", new Double[]{40.760756, 29.7839402},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "matematikcimbilal@gmail.com"),

                    /////////////////////////////////////////////////////////

                    Map.of("title", "Tranquil Retreat: Family Home in Batman Center",
                            "description", "Escape to this serene family home located in the heart of Batman Center.",
                            "price", 250000.0,
                            "ccd", new Long[]{223L, 4134L, 48477L},
                            "address", "Batman Center Mah. 456. St. No: 7",
                            "location", new Double[]{37.7874104, 41.2573924},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "gulsum.satic@gmail.com"),

                    Map.of("title", "Urban Comfort: Stylish Apartment in Beşiri",
                            "description", "Experience urban comfort in this stylish apartment nestled in Beşiri.",
                            "price", 150000.0,
                            "ccd", new Long[]{223L, 4134L, 48478L},
                            "address", "Beşiri Mah. 789. Ave. No: 10",
                            "location", new Double[]{37.9162455, 41.2927679},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "gulsum.satic@gmail.com"),

                    Map.of("title", "Central Workspace: Modern Office in Batman Center",
                            "description", "Discover this modern office space situated at the heart of Batman Center.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 4134L, 48477L},
                            "address", "Batman Center Mah. 123. Ave. No: 5",
                            "location", new Double[]{37.7874104, 41.2573924},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "gulsum.satic@gmail.com"),

                    Map.of("title", "Exclusive Haven: Secluded Villa in Gercüş",
                            "description", "Indulge in luxury with this exclusive villa offering seclusion in Gercüş.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 4134L, 48479L},
                            "address", "Gercüş Mah. 101. St. No: 3",
                            "location", new Double[]{37.5683088, 41.3852365},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "gulsum.satic@gmail.com"),

                    Map.of("title", "Investment Opportunity: Land in Hasankeyf",
                            "description", "Don't miss this investment opportunity with this land located in Hasankeyf.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 4134L, 48480L},
                            "address", "Hasankeyf Mah. 7890. St. No: 12",
                            "location", new Double[]{37.7304689, 41.4160863},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "gulsum.satic@gmail.com"),

                    Map.of("title", "Prime Location: Shop on Main Street in Kozluk",
                            "description", "Grab this prime location shop situated on the main street in Kozluk.",
                            "price", 180000.0,
                            "ccd", new Long[]{223L, 4134L, 48481L},
                            "address", "Kozluk Mah. 4567. Ave. No: 20",
                            "location", new Double[]{38.1933398, 41.4886025},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "gulsum.satic@gmail.com"),

                    //////////////////////////////////////////////////////

                    Map.of("title", "Tranquil Haven: Family Residence in Historic Kastamonu",
                            "description", "Discover serenity in this charming family residence nestled in the historic heart of Kastamonu.",
                            "price", 280000.0,
                            "ccd", new Long[]{223L, 4166L, 48867L},
                            "address", "Kastamonu Center Mah. 456. St. No: 7",
                            "location", new Double[]{41.3680217, 33.7619177},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "ebubekirc442@gmail.com"),

                    Map.of("title", "Coastal Retreat: Modern Apartment in Scenic İnebolu",
                            "description", "Escape to coastal bliss in this modern apartment set amidst the scenic beauty of İnebolu.",
                            "price", 150000.0,
                            "ccd", new Long[]{223L, 4166L, 48866L},
                            "address", "İnebolu Mah. 789. Ave. No: 10",
                            "location", new Double[]{41.9785763, 33.7599031},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "ebubekirc442@gmail.com"),

                    Map.of("title", "Urban Hub: Contemporary Office Space in Vibrant Kastamonu",
                            "description", "Experience urban sophistication in this contemporary office space located in the vibrant heart of Kastamonu.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 4166L, 48867L},
                            "address", "Kastamonu Center Mah. 123. Ave. No: 5",
                            "location", new Double[]{41.3680217, 33.7619177},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "ebubekirc442@gmail.com"),

                    Map.of("title", "Secluded Gem: Luxurious Villa Escape in Idyllic Cide",
                            "description", "Indulge in luxury at this secluded gem, offering a luxurious villa escape in the idyllic surroundings of Cide.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 4166L, 48859L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{41.8915711, 33.003712},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "ebubekirc442@gmail.com"),

                    Map.of("title", "Promising Investment: Vast Land in Tranquil Araç",
                            "description", "Explore investment opportunities with this vast land nestled in the tranquil surroundings of Araç.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 4166L, 48856L},
                            "address", "Araç Mah. 7890. St. No: 12",
                            "location", new Double[]{41.2412014, 33.324933},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "ebubekirc442.com"),

                    Map.of("title", "Prime Corner: Bustling Shop in Central Tosya",
                            "description", "Secure your spot at the prime corner with this bustling shop located in central Tosya.",
                            "price", 180000.0,
                            "ccd", new Long[]{223L, 4166L, 48873L},
                            "address", "Tosya Mah. 4567. Ave. No: 20",
                            "location", new Double[]{41.0164957, 34.0386079},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "ebubekirc442@gmail.com"),

                    //////////////////////////////////////////////////////

                    Map.of("title", "Tranquil Haven: Exquisite Family Residence in the Heart of Bustling Izmir",
                            "description", "Step into a realm of tranquility with this exquisite family residence, a sanctuary nestled amidst the vibrant energy of bustling Izmir. Embrace the harmonious blend of modern luxury and timeless elegance, where every corner tells a story of comfort and sophistication. This haven offers not just a home, but an experience of serenity amidst the dynamic pulse of city life.",
                            "price", 320000.0,
                            "ccd", new Long[]{223L, 4161L, 48814L},
                            "address", "Izmir City Center Mah. 456. St. No: 7",
                            "location", new Double[]{38.4185042, 27.1290835},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "adamd.born@gmail.com"),

                    Map.of("title", "Seaside Serenity: Luxurious Coastal Apartment in Alsancak's Prestigious Enclave",
                            "description", "Indulge in the epitome of coastal living with this luxurious apartment nestled in the heart of Alsancak's prestigious enclave. Offering panoramic views of the azure waters and pristine coastline, this residence exudes elegance and charm. Immerse yourself in the gentle embrace of sea breeze and the soothing rhythm of the waves, where every moment is a celebration of serenity and luxury.",
                            "price", 275000.0,
                            "ccd", new Long[]{223L, 4161L, 48814L},
                            "address", "Alsancak Mah. 789. Ave. No: 10",
                            "location", new Double[]{38.4185042, 27.1290835},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "adamd.born@gmail.com"),

                    Map.of("title", "Elevated Productivity: State-of-the-Art Office Space in Konak's Business District",
                            "description", "Experience a new era of elevated productivity with this state-of-the-art office space situated in the heart of Konak's bustling business district. Designed to inspire creativity and foster innovation, this modern workspace offers the perfect environment for growth and success. Elevate your business to new heights in this dynamic hub of entrepreneurship and opportunity.",
                            "price", 350000.0,
                            "ccd", new Long[]{223L, 4161L, 48814L},
                            "address", "Konak Mah. 123. Ave. No: 5",
                            "location", new Double[]{38.4185042, 27.1290835},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "adamd.born@gmail.com"),

                    Map.of("title", "Elegant Escape: Secluded Villa Retreat in Urla's Idyllic Countryside",
                            "description", "Embark on an elegant escape to this secluded villa retreat, nestled amidst the idyllic countryside of Urla. Surrounded by lush greenery and tranquil landscapes, this exquisite sanctuary offers a haven of peace and serenity. Immerse yourself in the timeless beauty of nature, where every moment is a celebration of luxury and relaxation. Experience the epitome of refined living in this enchanting villa retreat.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 4161L, 48823L},
                            "address", "Urla Mah. 101. St. No: 3",
                            "location", new Double[]{38.3228184, 26.7672998},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "adamd.born@gmail.com"),

                    Map.of("title", "Unlimited Potential: Expansive Land Parcel in Bornova's Flourishing Landscape",
                            "description", "Unlock endless possibilities with this expansive land parcel nestled in the heart of Bornova's flourishing landscape. Spanning vast stretches of fertile terrain, this prime investment opportunity offers unlimited potential for development and growth. Whether envisioning a residential haven, a commercial venture, or a recreational retreat, seize the chance to shape your dreams into reality in this land of boundless opportunity.",
                            "price", 480000.0,
                            "ccd", new Long[]{223L, 4161L, 48800L},
                            "address", "Bornova Mah. 7890. St. No: 12",
                            "location", new Double[]{38.4660651, 27.2190721},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "adamd.born.com"),

                    Map.of("title", "Retail Excellence: Prime Corner Shop in Karşıyaka's Vibrant Retail Hub",
                            "description", "Position your brand for retail excellence with this prime corner shop located in the heart of Karşıyaka's vibrant retail hub. Boasting high foot traffic and excellent visibility, this strategic location offers unparalleled exposure for your business. Whether launching a new venture or expanding your presence, capitalize on this unique opportunity to make a lasting impression in the retail landscape.",
                            "price", 150000.0,
                            "ccd", new Long[]{223L, 4161L, 48810L},
                            "address", "Karşıyaka Mah. 4567. Ave. No: 20",
                            "location", new Double[]{38.503445150000005, 27.113455434284667},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "adamd.born@gmail.com"),

                    //////////////////////////////////////////////////////

                    Map.of("title", "House6",
                            "description", "Beautifully designed and spacious.",
                            "price", 100000.0,
                            "ccd", new Long[]{223L, 4160L, 48762L},
                            "address", "Sakura Cad. No: 15",
                            "location", new Double[]{41.10500496452611, 28.788342747629066},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "sarpzahide@gmail.com"),

                    Map.of("title", "Apartment6",
                            "description", "Located in a prime neighborhood.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 3866L, 42218L},
                            "address", "Sakura Cad. No: 16",
                            "location", new Double[]{39.91349760621756, 28.14135381537319},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "sarpzahide@gmail.com"),

                    Map.of("title", "Office6",
                            "description", "Modern amenities for your comfort.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 3264L, 38248L},
                            "address", "Sakura Cad. No: 17",
                            "location", new Double[]{38.62218565477382, 35.15306199396324},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "sarpzahide@gmail.com"),

                    Map.of("title", "Villa6",
                            "description", "Stunning views from every room.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 1278L, 17726L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{39.97437614009851, 32.86918909133035},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "sarpzahide@gmail.com"),

                    Map.of("title", "Land6",
                            "description", "Close to schools, parks, and shopping centers.",
                            "price", 500000.0,
                            "ccd", new Long[]{223L, 1359L, 19014L},
                            "address", "Sakura Cad. No: 19",
                            "location", new Double[]{37.75093476161285, 41.40976722322277},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "sarpzahide@gmail.com"),

                    Map.of("title", "Shop6",
                            "description", "Secure and peaceful environment.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 3885L, 42218L},
                            "address", "Sakura Cad. No: 20",
                            "location", new Double[]{38.46207669619371, 27.17709951048468},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "sarpzahide@gmail.com"),

                    //////////////////////////////////////////////////////

                    Map.of("title", "House7",
                            "description", "Beautifully designed and spacious.",
                            "price", 100000.0,
                            "ccd", new Long[]{223L, 4160L, 48762L},
                            "address", "Sakura Cad. No: 15",
                            "location", new Double[]{41.10500496452611, 28.788342747629066},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "yasemin@gmail.com"),

                    Map.of("title", "Apartment7",
                            "description", "Located in a prime neighborhood.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 3866L, 42218L},
                            "address", "Sakura Cad. No: 16",
                            "location", new Double[]{39.91349760621756, 28.14135381537319},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "yasemin@gmail.com"),

                    Map.of("title", "Office7",
                            "description", "Modern amenities for your comfort.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 3264L, 38248L},
                            "address", "Sakura Cad. No: 17",
                            "location", new Double[]{38.62218565477382, 35.15306199396324},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "yasemin@gmail.com"),

                    Map.of("title", "Villa7",
                            "description", "Stunning views from every room.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 1278L, 17726L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{39.97437614009851, 32.86918909133035},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "yasemin@gmail.com"),

                    Map.of("title", "Land7",
                            "description", "Close to schools, parks, and shopping centers.",
                            "price", 500000.0,
                            "ccd", new Long[]{223L, 1359L, 19014L},
                            "address", "Sakura Cad. No: 19",
                            "location", new Double[]{37.75093476161285, 41.40976722322277},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "yasemin@gmail.com"),

                    Map.of("title", "Shop7",
                            "description", "Secure and peaceful environment.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 3885L, 42218L},
                            "address", "Sakura Cad. No: 20",
                            "location", new Double[]{38.46207669619371, 27.17709951048468},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "yasemin@gmail.com"),

                    //////////////////////////////////////////////////////


                    Map.of("title", "House8",
                            "description", "Beautifully designed and spacious.",
                            "price", 100000.0,
                            "ccd", new Long[]{223L, 4160L, 48762L},
                            "address", "Sakura Cad. No: 15",
                            "location", new Double[]{41.10500496452611, 28.788342747629066},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "zeynepnune@gmail.com"),

                    Map.of("title", "Apartment8",
                            "description", "Located in a prime neighborhood.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 3866L, 42218L},
                            "address", "Sakura Cad. No: 16",
                            "location", new Double[]{39.91349760621756, 28.14135381537319},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "zeynepnune@gmail.com"),

                    Map.of("title", "Office8",
                            "description", "Modern amenities for your comfort.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 3264L, 38248L},
                            "address", "Sakura Cad. No: 17",
                            "location", new Double[]{38.62218565477382, 35.15306199396324},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "zeynepnune@gmail.com"),

                    Map.of("title", "Villa8",
                            "description", "Stunning views from every room.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 1278L, 17726L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{39.97437614009851, 32.86918909133035},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "zeynepnune@gmail.com"),

                    Map.of("title", "Land8",
                            "description", "Close to schools, parks, and shopping centers.",
                            "price", 500000.0,
                            "ccd", new Long[]{223L, 1359L, 19014L},
                            "address", "Sakura Cad. No: 19",
                            "location", new Double[]{37.75093476161285, 41.40976722322277},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "zeynepnune@gmail.com"),

                    Map.of("title", "Shop8",
                            "description", "Secure and peaceful environment.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 3885L, 42218L},
                            "address", "Sakura Cad. No: 20",
                            "location", new Double[]{38.46207669619371, 27.17709951048468},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "zeynepnune@gmail.com"),

                    //////////////////////////////////////////////////////


                    Map.of("title", "House9",
                            "description", "Beautifully designed and spacious.",
                            "price", 100000.0,
                            "ccd", new Long[]{223L, 4160L, 48762L},
                            "address", "Sakura Cad. No: 15",
                            "location", new Double[]{41.10500496452611, 28.788342747629066},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/house.jpg",
                            "user", "abdrrahman@gmail.com"),

                    Map.of("title", "Apartment9",
                            "description", "Located in a prime neighborhood.",
                            "price", 200000.0,
                            "ccd", new Long[]{223L, 3866L, 42218L},
                            "address", "Sakura Cad. No: 16",
                            "location", new Double[]{39.91349760621756, 28.14135381537319},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/apartment.jpg",
                            "user", "abdrrahman@gmail.com"),

                    Map.of("title", "Office9",
                            "description", "Modern amenities for your comfort.",
                            "price", 300000.0,
                            "ccd", new Long[]{223L, 3264L, 38248L},
                            "address", "Sakura Cad. No: 17",
                            "location", new Double[]{38.62218565477382, 35.15306199396324},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/office.jpg",
                            "user", "abdrrahman@gmail.com"),

                    Map.of("title", "Villa9",
                            "description", "Stunning views from every room.",
                            "price", 400000.0,
                            "ccd", new Long[]{223L, 1278L, 17726L},
                            "address", "Sakura Cad. No: 18",
                            "location", new Double[]{39.97437614009851, 32.86918909133035},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/villa.jpg",
                            "user", "abdrrahman@gmail.com"),

                    Map.of("title", "Land9",
                            "description", "Close to schools, parks, and shopping centers.",
                            "price", 500000.0,
                            "ccd", new Long[]{223L, 1359L, 19014L},
                            "address", "Sakura Cad. No: 19",
                            "location", new Double[]{37.75093476161285, 41.40976722322277},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/land.jpg",
                            "user", "abdrrahman@gmail.com"),

                    Map.of("title", "Shop9",
                            "description", "Secure and peaceful environment.",
                            "price", 600000.0,
                            "ccd", new Long[]{223L, 3885L, 42218L},
                            "address", "Sakura Cad. No: 20",
                            "location", new Double[]{38.46207669619371, 27.17709951048468},
                            "category", 1L,
                            "advertType", 1L,
                            "image", "static/shop.jpg",
                            "user", "abdrrahman@gmail.com")

            ));

            for (Map<String, Object> advert : adverts) {

                String title = advert.get("title").toString();
                Image image = buildImage(advert.get("image").toString(), title);

                Advert builtInAdvert = Advert.builder()
                        .title(title)
                        .description(advert.get("description").toString())
                        .slug(GeneralUtils.generateSlug(title))
                        .price((Double) advert.get("price"))
                        .statusForAdvert(StatusForAdvert.ACTIVATED)
                        .builtIn(true)
                        .isActive(true)
                        .viewCount(0)
                        .location(new Location(((Double[]) advert.get("location"))[0], ((Double[]) advert.get("location"))[1]))
                        .advertType(advertTypeRepository.findById((Long) advert.get("advertType")).get())
                        .category(categoryRepository.findById((Long) advert.get("category")).get())
                        .country(countriesRepository.findById(((Long[]) advert.get("ccd"))[0]).get())
                        .city(cityRepository.findById(((Long[]) advert.get("ccd"))[1]).get())
                        .district(districtsRepository.findById(((Long[]) advert.get("ccd"))[2]).get())
                        .address(advert.get("address").toString())
                        .images(List.of(image))
                        .user(userRepository.findByEmail(advert.get("user").toString()).get())
                        .build();

                Advert savedAdvert = advertRepository.save(builtInAdvert);

                Image savedImage = savedAdvert.getImages().stream().findFirst().orElse(null);
                if (savedImage != null) {
                    savedImage.setAdvert(savedAdvert);
                    imageRepository.save(savedImage);
                }
            }
        }
    }

    private Image buildImage(String fileName, String title) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(new ClassPathResource(fileName).getURI()));
            return Image.builder()
                    .data(ImageUtil.compressImage(data))
                    .name(title)
                    .type("image/jpg")
                    .featured(true)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}