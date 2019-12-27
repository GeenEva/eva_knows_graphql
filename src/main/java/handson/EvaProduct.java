package handson;

import apple.laf.JRSUIConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductIdentifiable;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.*;


public class EvaProduct extends Base implements Versioned<Product>, ProductIdentifiable {

    private final Long version;
    private final String id;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;
    private final Map<?,?> masterdata;
    private final String name;
    private final Map<?,?> productType;
    private final String description;
    private List<Category> categories;
    private LinkedHashMap<?,?> evaVariants = new LinkedHashMap<>();



    //Constructor
    @JsonCreator
    public  EvaProduct(@JsonProperty("version") Long version,
                       @JsonProperty("id")  String id,
                       @JsonProperty("createdAt") LocalDateTime createdAt,
                       @JsonProperty("lastModifiedAt") LocalDateTime lastModifiedAt,
                       @JsonProperty("masterData") Map<?,?> masterdata,
                       @JsonProperty("productType") Map<?,?> productType,
                       @JsonProperty("categories") List<Category> categories,
                       @JsonProperty("evaVariants") LinkedHashMap<?,?> evaVariant) {

        this.version = version;
        this.id = id;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.masterdata = masterdata;
        this.name = ((String)((Map)masterdata.get("current")).get("name"));
        this.productType = productType;
        this.description = (String) productType.get("description");
        this.categories = (List<Category>)((Map)masterdata.get("current")).get("categories");
        ((Map)masterdata.get("current")).get("masterVariant");
        this.evaVariants = (LinkedHashMap<?,?>)((Map)masterdata.get("current")).get("masterVariant");
    }

    public String getId() {
        return id;
    }

    @Override
    public Long getVersion() { return null; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public Map<?, ?> getMasterdata() { return masterdata; }

    public String getName(){
        return name ;
    }

    public Map<?, ?> getProductType() { return productType; }

    static SphereRequest<List<EvaProduct>> getAllEvaProducts(){
        return new GetAllEvaProducts();
    }


    @Override
    public String toString() {
        return "\nEvaProduct{" +
                "\nversion= " + version +
                ", \nid='" + id + '\'' +
                ", \ncreatedAt= " + createdAt +
                ", \nlastModifiedAt= " + lastModifiedAt +
                ", \nmasterdata= " + masterdata +
                ", \nname= " + name +
                ", \ndescription= " + description +
                ", \ncategories= " + categories +
                ". \nvariants=" + evaVariants +
                "\n}";
    }

    private static class GetAllEvaProducts extends Base implements SphereRequest<List<EvaProduct>> {

        public static final int MAX = 2;

        @Nullable
        @Override
        public List<EvaProduct> deserialize(HttpResponse httpResponse) {

            final JsonNode results = SphereJsonUtils.parse(httpResponse.getResponseBody())
                    .get("data").get("products").get("results");

            List<EvaProduct> evaProducts = SphereJsonUtils.readObject(results, new TypeReference<List<EvaProduct>>() { });

            return evaProducts;
        }

        @Override
        public HttpRequestIntent httpRequestIntent() {
            final String queryString = String.format("query product {\n" +
                    "       products(limit: %d) {\n" +
                    "           results {\n" +
                    "               productType { \n " +
                    "                   description\n" +
                    "                   }\n" +
                    "               version\n" +
                    "               id\n" +
                    "               createdAt\n" +
                    "               lastModifiedAt\n" +
                    "               masterData{\n" +
                    "                   current{\n" +
                    "                      masterVariant {\n" +
                    "                           sku \n" +
                    "                           }\n" +
                    "                      name(locale: \"en\")" +
                    "                      categories{\n" +
                    "                          id\n" +
                    "                        }\n"+
                    "                   }\n" +
                    "               }\n" +
                    "           }\n" +
                    "       }\n" +
                    "   }", MAX);
            final String body =String.format(
                    "{\n" +
                            "   \"query\": \"%s\"" +
                            "}", queryString.replace("\n", "\\n").replace("\"", "\\\""));
            return HttpRequestIntent.of(HttpMethod.POST, "/graphql", body);
        }
    }

}
