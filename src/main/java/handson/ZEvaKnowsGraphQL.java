package handson;

import handson.impl.ClientService;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonNodeSphereRequest;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.queries.TaxCategoryByKeyGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createSphereClient;
import static handson.LightweightProduct.requestOfSkus;
import static handson.EvaProduct.requestOfSkusEvaProducts;


public class ZEvaKnowsGraphQL {
    private static final Logger LOG = LoggerFactory.getLogger(ZEvaKnowsGraphQL.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        try (final SphereClient client = createSphereClient()) {

            List<String> skuus = new ArrayList<>();

            skuus.add("cookie-SKU102");
            skuus.add("cookie-SKU101");

            /*final SphereRequest<List<LightweightProduct>> listSphereRequest = requestOfSkus(skuus);

            HttpRequestIntent httpRequestIntent = listSphereRequest.httpRequestIntent();

            System.out.println("==========  =========  HttpRequestIntent: " + httpRequestIntent);

            List<LightweightProduct> lightweightProducts = client.execute(listSphereRequest)
                    .toCompletableFuture().get();

            System.out.println( lightweightProducts.get(0));*/

            final SphereRequest<List<EvaProduct>> listSphereRequest = requestOfSkusEvaProducts(skuus);
            CompletionStage<List<EvaProduct>> completionStage = client.execute(listSphereRequest);
            List<EvaProduct> evaProducts =
                   completionStage .toCompletableFuture().get();

            System.out.println( evaProducts.get(0) + "\n" + evaProducts.get(1));

        }
    }
}
