package handson;
import com.commercetools.sync.products.ProductSync;
import com.commercetools.sync.products.ProductSyncOptions;
import com.commercetools.sync.products.ProductSyncOptionsBuilder;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.utils.MoneyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static handson.impl.ClientService.createSphereClient;
import static java.lang.String.format;


public class ExerciseMoodle10 {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseMoodle10.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String inputFilePath = "/products.csv";
        final List<ProductDraft> productDrafts = processInputFile(inputFilePath);

        LOG.info("Parsed {} {}", inputFilePath, productDrafts);

        LOG.info("Starting Sync..");

        try (final SphereClient client = createSphereClient()) {

            //TODO Task10.4 Sync the product drafts

            ProductSyncOptions productSyncOptions =
                    ProductSyncOptionsBuilder.of(client)
                    .errorCallback(LOG::info)
                    .warningCallback(LOG::info)
                    .build();

            ProductSync productSync = new ProductSync(productSyncOptions);

            productSync.sync(productDrafts)
                    .thenAcceptAsync(productSyncStatistics ->
                            LOG.info(productSyncStatistics.getReportMessage()))
                    .toCompletableFuture().get();
        }
    }

    private static List<ProductDraft> processInputFile(@Nonnull final String inputFilePath) {
        final InputStream csvAsStream = ExerciseMoodle10.class.getResourceAsStream(inputFilePath);
        final BufferedReader br = new BufferedReader(new InputStreamReader(csvAsStream));

        return br.lines()
                 .skip(1) // skip the header of the csv
                 .map(ExerciseMoodle10::processLine)
                 .collect(Collectors.toList());
    }


    private static ProductDraft processLine(@Nonnull final String line) {
        final String[] splitLine = line.split(",");
        //TODO 10.1 Please replace the prefix below (with value "yourName") with your actual name.
        final String prefix = "cookie";                                         // Set prefix
        final String productTypeKey = splitLine[0];
        final String productKey = format("%s-%s", prefix, splitLine[1]);
        final String sku = format("%s-%s", prefix, splitLine[2]);
        final String variantKey = format("%s-%s", prefix, splitLine[3]);
        final String productName = format("%s-%s", prefix, splitLine[4]);
        final String productDescription = splitLine[5];
        final double basePrice = Double.parseDouble(splitLine[6]);
        final String currencyCode = splitLine[7];
        final String imageUrl = splitLine[8];


        final PriceDraft priceDraft = PriceDraftBuilder
            .of(MoneyImpl.of(BigDecimal.valueOf(basePrice), currencyCode))
            .build();

        final Image image = Image.of(imageUrl, ImageDimensions.of(100, 100));

        //TODO 10.2 Create a ProductVariantDraft.

        ProductVariantDraft productVariantDraft = ProductVariantDraftBuilder.of()
                .key(productKey)
                .price(priceDraft)
                .images(image)
                .sku(sku)
                .build();

        //TODO 10.3 Create a ProductDraft and return it.

        ProductDraft productDraft =
                ProductDraftBuilder.of(
                        ProductType.reference(productTypeKey),
                        LocalizedString.ofEnglish(productName),
                        LocalizedString.ofEnglish(productName),
                        productVariantDraft)
                .description(LocalizedString.ofEnglish(productDescription))
                .key(productKey)
                .build();

        return productDraft;
    }
}
