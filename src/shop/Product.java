package shop;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class Product {
    private final UUID productId;
    private final String productName;

    public Product(@NotNull String productName) {
        this.productName = productName;
        productId = java.util.UUID.randomUUID();
    }

    public String name() {
        return productName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Product product = (Product) object;

        if (!productId.equals(product.productId)) return false;
        return productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + productName.hashCode();
        return result;
    }
}
