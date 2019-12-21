package handson.impl;

import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddDiscountCode;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductProjection;

import java.util.concurrent.CompletionStage;

/**
 * This class provides operations to work with {@link Cart}s.
 */
public class CartService extends AbstractService {

    public CartService(SphereClient client) {
        super(client);
    }

    /**
     * Creates a cart for the given customer.
     *
     * @param customer the customer
     * @return the customer creation completion stage
     */
    public CompletionStage<Cart> createCart(final Customer customer) {
        // TODO Task15.1. Create a cart
        // Make sure the customer has a default shipping address!!
        //




        return client.execute(
               CartCreateCommand.of( CartDraftBuilder.of(DefaultCurrencyUnits.EUR)
                .deleteDaysAfterLastModification(90)
                .customerEmail(customer.getEmail())
                .customerId(customer.getId())
                .country(customer.getDefaultShippingAddress().getCountry())
                .shippingAddress(customer.getDefaultShippingAddress())
                .inventoryMode(InventoryMode.RESERVE_ON_ORDER)
                .build()
       ));
    }

        public CompletionStage<Cart> addProductToCartBySku(final String sku, final int quantity, final Cart cart) {
            // TODO Task15.2. Add line item to a cart

            return client.execute(
                    CartUpdateCommand.of(cart,
                            AddLineItem.of(
                                    LineItemDraftBuilder.ofSku(sku,(long) quantity)
                                            .supplyChannel((Referenceable<Channel>) Channel.referenceOfId("4c68d445-7a27-47f5-b3c8-6a40bd3f1626"))
                                            .build()
                                            )
                    ));
        }

    /**
     * Adds the given product to the given cart.
     *
     * @param product the product
     * @param cart    the cart
     * @return the cart update completion stage
     */
    public CompletionStage<Cart> addProductToCart(final ProductProjection product, final Cart cart) {
        // TODO Task15.2. Add line item to a cart


       return null;
    }



    public CartUpdateCommand addProductToCartCommand(final ProductProjection product, final Cart cart) {
        // TODO 17.2. Add line item to a cart
        // Return Command!!

        return null;
    }


    /**
     * Adds the given discount code to the given cart.
     *
     * @param code the discount code
     * @param cart the cart
     * @return the cart update completion stage
     */
    public CompletionStage<Cart> addDiscountToCart(final String code, final Cart cart) {
        // TODO XXXXXXXX.1 Add discount code to cart

        return client.execute(
                CartUpdateCommand.of(cart,
                        AddDiscountCode.of(code))
        );
    }
}
