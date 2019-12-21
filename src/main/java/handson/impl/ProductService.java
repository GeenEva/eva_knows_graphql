package handson.impl;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddToCategory;
import io.sphere.sdk.products.commands.updateactions.Publish;

import java.util.concurrent.CompletionStage;

public class ProductService  extends AbstractService  {

    public ProductService(SphereClient client) {
        super(client);
    }

    public ProductUpdateCommand addCategoryCommand (final Product product, Category category) {

        return null;
    }

    public CompletionStage<Product> publish (final Product product) {

        return null;
    }


}
