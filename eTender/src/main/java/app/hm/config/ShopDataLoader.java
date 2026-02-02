package app.hm.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.hm.entity.Shop;
import app.hm.repo.ShopRepository;

@Component
public class ShopDataLoader implements CommandLineRunner {

    private final ShopRepository shopRepository;

    public ShopDataLoader(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    
    @Override
    public void run(String... args) throws Exception {
        if (shopRepository.count() == 0) {
            Shop s1 = new Shop();
            s1.setLocation("Main Building - Ground Floor");
            s1.setAreaMainFloor(120.0);
            s1.setAreaMezzanineFloor(50.0);

            Shop s2 = new Shop();
            s2.setLocation("Main Building - First Floor");
            s2.setAreaMainFloor(100.0);
            s2.setAreaMezzanineFloor(40.0);

            Shop s3 = new Shop();
            s3.setLocation("Annex Building - Ground Floor");
            s3.setAreaMainFloor(80.0);
            s3.setAreaMezzanineFloor(30.0);

            Shop s4 = new Shop();
            s4.setLocation("Annex Building - First Floor");
            s4.setAreaMainFloor(90.0);
            s4.setAreaMezzanineFloor(35.0);

            Shop s5 = new Shop();
            s5.setLocation("Service Block - Mezzanine");
            s5.setAreaMainFloor(70.0);
            s5.setAreaMezzanineFloor(25.0);

            shopRepository.saveAll(java.util.List.of(s1, s2, s3, s4, s5));
            System.out.println("5 default shops loaded.");
        }
    }

}
