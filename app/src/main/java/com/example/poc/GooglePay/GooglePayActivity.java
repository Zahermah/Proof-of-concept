package com.example.poc.GooglePay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poc.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

public class GooglePayActivity extends AppCompatActivity {


    private static final String TAG = GooglePayNetwork.class.getName();

    private PaymentsClient paymentsClient;
    private GooglePayNetwork googlePayNetwork;
    Button googlePayButton;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.google_pay_activity);
        super.onCreate(savedInstanceState);


        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST).build();

        paymentsClient= Wallet.getPaymentsClient(this,walletOptions);
        Toast.makeText(this, "Started Gpay Activity", Toast.LENGTH_LONG).show();
        possiblyShowGooglePaybutton();

    }


    private void possiblyShowGooglePaybutton() {
        final Optional<JSONObject> isReadyToPayJson = GooglePayNetwork.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }
        final IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        if (request == null) {
            return;
        }
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                try {
                    if(task !=null ){

                    }
                    boolean result = task.getResult(ApiException.class);
                    if (result) {
                        googlePayButton = findViewById(R.id.payWithGpay);
                        googlePayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar.make(v, "Clicked", Snackbar.LENGTH_LONG).show();
                                requestPayment(v);

                            }
                        });
                        googlePayButton.setVisibility(View.VISIBLE);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestPayment(View view) {
        Optional<JSONObject> paymentDataRequestJson = GooglePayNetwork.getPaymentDataRequest();
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }
        PaymentDataRequest paymentDataRequest = PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
        if (paymentDataRequest != null) {
            AutoResolveHelper.resolveTask(paymentsClient.loadPaymentData(paymentDataRequest), this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        String json = paymentData.toJson();
                        try {
                            JSONObject paymentMethodData = new JSONObject(json).getJSONObject("paymentMethodData");
                            String paymentToken = paymentMethodData.getJSONObject("tokenizationData").getString("token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        Log.d(TAG, status.toString());
                        break;
                    default:
                }
                break;
            default:
        }
    }
}
