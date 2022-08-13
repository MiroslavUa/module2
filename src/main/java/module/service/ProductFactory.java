package module.service;

import module.model.product.Product;
import module.model.product.ScreenType;
import module.model.product.Telephone;
import module.model.product.Television;
import java.util.Map;
import java.util.Optional;

public class ProductFactory {
    public static Optional<Product> createProduct(Map<String, Object> hashMap){
        String product = (String) hashMap.get("type");
        switch (product) {
            case "Telephone" -> {return Optional.of(new Telephone (
                    (String) hashMap.get("series"),
                    ScreenType.valueOf((String) hashMap.get("screen type")),
                    Double.parseDouble(hashMap.get("price").toString()),
                    (String) hashMap.get("model"))
                );
            }
            case "Television" -> {return Optional.of(new Television (
                    (String) hashMap.get("series"),
                    ScreenType.valueOf((String) hashMap.get("screen type")),
                    Double.parseDouble(hashMap.get("price").toString()),
                    Double.parseDouble(hashMap.get("diagonal").toString()),
                    (String) hashMap.get("country"))
                );
            }
            default -> {
                return Optional.empty();
            }
        }
    }
}
