package handson;

import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariantAvailability;
import io.sphere.sdk.products.attributes.Attribute;

import java.util.*;

public class VeloVariant {

    private Integer id;
    private String sku;
    private List<Price> prices;
    private List<Image> images;
    private ProductVariantAvailability availability;
    private Map<String, Set<Attribute>> allAttributes = new HashMap<>();


    public Map<String, Set<Attribute>> getAllAttributes() {
        return allAttributes;
    }

    public void setAllAttributes(Map<String, Set<Attribute>> allAttributes) {
        this.allAttributes = allAttributes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public ProductVariantAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(ProductVariantAvailability availability) {
        this.availability = availability;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}