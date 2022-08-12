package service;

import model.product.Product;
import model.product.ScreenType;
import model.product.Telephone;
import model.product.Television;
import java.util.Map;
import java.util.Optional;

public class ProductFactory {
    public static Optional<Product> createProduct(Map<String, Object> hashMap){
        String product = (String) hashMap.get("type");
        switch (product) {
            case "Telephone" -> {return Optional.of(new Telephone (
                    (String) hashMap.get("series"),
                    ScreenType.valueOf((String) hashMap.get("screen type")),
                    (Double) hashMap.get("price"),
                    (String) hashMap.get("model"))
                );
            }
            case "Television" -> {return Optional.of(new Television (
                    (String) hashMap.get("series"),
                    ScreenType.valueOf((String) hashMap.get("screen type")),
                    (Double) hashMap.get("price"),
                    (Double) hashMap.get("diagonal"),
                    (String) hashMap.get("country"))
                );
            }
            default -> {
                return Optional.empty();
            }
        }
    }
}
