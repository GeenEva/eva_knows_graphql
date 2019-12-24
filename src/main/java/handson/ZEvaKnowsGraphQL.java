package handson;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createSphereClient;
import static handson.EvaVariant.requestOfSkusEvaProducts;
import static handson.EvaProduct.getAllEvaProducts;


public class ZEvaKnowsGraphQL {
    private static final Logger LOG = LoggerFactory.getLogger(ZEvaKnowsGraphQL.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        try (final SphereClient client = createSphereClient()) {

            SphereRequest<List<EvaProduct>> allEvaProducts = getAllEvaProducts();

            CompletionStage<List<EvaProduct>> listCompletionStage = client.execute((allEvaProducts));

            List<EvaProduct> evaProducts1 = listCompletionStage.toCompletableFuture().get();

            for(EvaProduct evaProduct: evaProducts1) {
                System.out.println(evaProduct.toString());
            }
        }
    }
}
