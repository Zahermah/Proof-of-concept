package com.example.poc.GooglePay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

public class GooglePayNetwork {

    public static JSONObject getRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2)
                .put("apiVersionMinor", 26);
    }


    private static JSONObject getTokenizationSpecification() throws JSONException {
        JSONObject tokenizationSpecification = new JSONObject();
        tokenizationSpecification.put("type", "PAYMENT_GATEWAY");
        tokenizationSpecification.put("parameters", new JSONObject()
                .put("gateway", "example")
                .put("gatewayMerchantId", "exampleMerchantId"));

        return tokenizationSpecification;
    }

    private static JSONArray getAllowedCardnetworks() {
        return new JSONArray()
                .put("MASTERCARD")
                .put("VISA")
                .put("AMEX");
    }

    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");
    }

    private static JSONObject getBaseCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");
        cardPaymentMethod.put(
                "parameters",
                new JSONObject()
                        .put("allowedAuthMethods", GooglePayNetwork.getAllowedCardAuthMethods()))
                .put("allowedCardNetworks", GooglePayNetwork.getAllowedCardnetworks());
        return cardPaymentMethod;

    }

    private static JSONObject getCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = GooglePayNetwork.getBaseCardPaymentMethod();
        cardPaymentMethod.put("tokenizationSpecification", GooglePayNetwork.getTokenizationSpecification());
        return cardPaymentMethod;
    }

    public static Optional<JSONObject> getIsReadyToPayRequest() {
        try{
            JSONObject isReadyToPay = GooglePayNetwork.getRequest();
            isReadyToPay.put("allowedPaymentMethods", new JSONArray().put(getBaseCardPaymentMethod()));
            return Optional.of(isReadyToPay);
        }catch (JSONException e){
            return Optional.empty();
        }
    }

    private static JSONObject getTransactionInfo() throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", "12.34");
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("currencyCode", "USD");

        return transactionInfo;
    }

    private static JSONObject getMerchantInfo() throws JSONException {
        return new JSONObject().put("merchantName", "Example Merchant");
    }

    public static Optional<JSONObject> getPaymentDataRequest() {
        try{
            JSONObject paymentDataRequest = GooglePayNetwork.getRequest();
            paymentDataRequest.put("allowedPaymentMethods", new JSONArray()
                    .put(GooglePayNetwork.getCardPaymentMethod()));
            paymentDataRequest.put("transactionInfo", GooglePayNetwork.getTransactionInfo());
            paymentDataRequest.put("merchantInfo", GooglePayNetwork.getMerchantInfo());
            return Optional.of(paymentDataRequest);
        }catch (JSONException e){
            return Optional.empty();
        }


    }
}
