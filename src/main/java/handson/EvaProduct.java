package handson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.xml.internal.org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EvaProduct {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private LocalizedString name;
//    private LocalizedString description;
//    private Set<Category> categories;
//    private Set<EvaVariant> veloVariants = new HashSet<>();


    @JsonCreator
    EvaProduct(@JsonProperty("id")  String id,
               @JsonProperty("createdAt") LocalDateTime createdAt,
               @JsonProperty("lastModifiedAt") LocalDateTime lastModifiedAt,
               @JsonProperty("name") LocalizedString name ){

        this.id = id;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public LocalizedString getName(){
        return name;
    }

    static SphereRequest<List<EvaProduct>> getAllEvaProducts(){
        return new GetAllEvaProducts();
    }


    @Override
    public String toString() {
        return "EvaProduct{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", name=" + name +
                '}';
    }

    private static class GetAllEvaProducts extends Base implements SphereRequest<List<EvaProduct>> {

        public static final int MAX = 5;


        @Nullable
        @Override
        public List<EvaProduct> deserialize(HttpResponse httpResponse) {
            final JsonNode rootJsonNode = SphereJsonUtils.parse(httpResponse.getResponseBody());

            System.out.println("========= ROOTJSONNODE: " + rootJsonNode + "\n");
            final JsonNode results = rootJsonNode.get("data").get("products").get("results");
            System.out.println("========= RESULTS : " + results +"\n");

            List<EvaProduct> evaProducts = SphereJsonUtils.readObject(results, new TypeReference<List<EvaProduct>>() { });

            for (EvaProduct evaProduct : evaProducts){
                System.out.println("========= EVAPRODUCTS : " + evaProduct.toString());
            }
            return evaProducts;
        }

        @Override
        public HttpRequestIntent httpRequestIntent() {
            final String queryString = String.format("query product {\n" +
                    "       products(limit: %d) {\n" +
                    "           results {\n" +
                    "               id\n" +
                    "               createdAt\n" +
                    "               lastModifiedAt\n" +
                    "               masterData{\n" +
                    "                   current{\n" +
                    "                   name(locale: \"en\")" +
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
