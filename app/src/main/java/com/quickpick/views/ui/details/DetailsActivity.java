package com.quickpick.views.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.quickpick.R;
import com.quickpick.presenter.payment.PaypalImplementation;
import com.quickpick.views.ui.BaseActivity;
import com.quickpick.views.ui.customviews.CustomDialog;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.quickpick.presenter.payment.PaypalImplementation.PAYPAL_REQUEST_CODE;

/**
 * Created by Rajesh Kumar on 16-03-2018.
 */

public class DetailsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.txt_item)
    TextView txt_item;
    @Bind(R.id.txt_price)
    TextView txt_price;
    @Bind(R.id.txt_minus)
    TextView txt_minus;
    @Bind(R.id.txt_plus)
    TextView txt_plus;
    @Bind(R.id.ed_txt_items)
    EditText ed_txt_items;
    @Bind(R.id.txt_pay)
    TextView txt_pay;
    @Bind(R.id.txt_header)
    TextView txt_header;
@Bind(R.id.txt_pay_not)
        TextView txt_pay_not;
    String item_name = "", item_price = "";

//ashok
    @Override
    protected int getLayoutResourceId() {
        return R.layout.details_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        txt_header.setText("Your Oreder Summary");
        txt_header.setTextColor(getResources().getColor(R.color.black));
        drawerToggle.setDrawerIndicatorEnabled(false);
        item_name = getIntent().getExtras().getString("item_name");
        item_price = getIntent().getExtras().getString("item_price");
        txt_item.setText(item_name);
        txt_price.setText("₹" + item_price);
        txt_pay.setText("Checkout 1 item for ₹"+item_price);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fetchListeners();

        ed_txt_items.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String value = trimLeadingZeros(charSequence.toString());
                if (value.length() > 0) {
                    int item = Integer.parseInt(value);
                    if (item > 0) {
                        txt_pay.setVisibility(View.VISIBLE);
                        txt_pay_not.setVisibility(View.GONE);
                        int item_basic_price = Integer.parseInt(item_price);
                        int resultent_price = (item * item_basic_price);
                        txt_price.setText("₹" + resultent_price);
                        txt_pay.setText("Checkout "+item +" item for ₹"+resultent_price);
                    } else {
                        txt_price.setText("₹0");
                        ed_txt_items.setText("");
                        txt_pay.setVisibility(View.GONE);
                        txt_pay_not.setVisibility(View.VISIBLE);
                        txt_pay_not.setText("Checkout "+item +" item for ₹"+0);
                    }
                } else {
                    txt_price.setText("₹0");
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void fetchListeners() {
        txt_minus.setOnClickListener(this);
        txt_plus.setOnClickListener(this);
        txt_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_minus:
                String item_value = ed_txt_items.getText().toString();
                try {
                    if (item_value.length() != 0) {
                        int item = Integer.parseInt(item_value);
                        if (item > 0) {
                            item--;
                            ed_txt_items.setText("" + item);
//                            int resultent_price = Integer.parseInt(txt_price.getText().toString().replace("₹",""))-Integer.parseInt(item_price);
                            int resultent_price = (item * Integer.parseInt(item_price));
                            txt_price.setText("₹" + resultent_price);
                            if(item==0) {
                                txt_pay.setText("Checkout " + item + " item for ₹" + resultent_price);
                                txt_pay.setVisibility(View.GONE);
                                txt_pay_not.setVisibility(View.VISIBLE);
                                txt_pay_not.setText("Checkout " + item + " item for ₹" + resultent_price);
                            }
                            else {
                                txt_pay.setVisibility(View.VISIBLE);
                                txt_pay_not.setVisibility(View.GONE);
                                txt_pay.setText("Checkout " + item + " item for ₹" + resultent_price);
                                txt_pay_not.setText("Checkout " + item + " item for ₹" + resultent_price);
                            }


                        } else {
                            Toast.makeText(DetailsActivity.this, "minus values not accepted", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txt_plus:
                String item_value_pluse = ed_txt_items.getText().toString();
                try {
                    if (item_value_pluse.length() != 0) {
                        int item = Integer.parseInt(item_value_pluse);
                        if (item >= 0) {
                            item++;
                            ed_txt_items.setText("" + item);
//                            int resultent_price = Integer.parseInt(txt_price.getText().toString().replace("₹",""))+Integer.parseInt(item_price);
                            int resultent_price = (item * Integer.parseInt(item_price));
                            txt_price.setText("₹" + resultent_price);
                            txt_pay.setText("Checkout "+item +" item for ₹"+resultent_price);
                        } else {
                            Toast.makeText(DetailsActivity.this, "minus values not accepted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ed_txt_items.setText("" + 1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txt_pay:
//                CustomDialog.getInstance().showCategory_Dialog(this, new CustomDialog.getpaymentType() {
//                    @Override
//                    public void getpayment() {
////                        Toast.makeText(DetailsActivity.this, "coming to activity", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(DetailsActivity.this, PayPalService.class);
//
//                        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PaypalImplementation.getInstance().getPPConfigrationInstance());
//
//                        startService(intent);
//
//                        getPayment(txt_price.getText().toString().replaceAll("₹","").trim());
//                    }
//                });
                startActivity(new Intent(DetailsActivity.this,Payment.class));
                break;

        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }

    public String trimLeadingZeros(String str) {
        if (str == null) {
            return null; //if null return null
        }
        char[] c = str.toCharArray();
        int i = 0;

        for (; i < c.length; i++) {
            if (c[i] == '0') {
                continue;
            } else if (c[i] >= '1' && c[i] <= '9') {
                break;
            } else {
                return str;
            }
        }
        if (i == 0 || i == c.length) {
            return str;
        } else {
            char[] temp = new char[c.length - i];
            int index = 0;
            for (int j = i; j < c.length; j++) {
                temp[index++] = c[j];
            }
            return new String(temp);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.e("paymentExample", "payment detials "+paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
//                        startActivity(new Intent(this, ConfirmationActivity.class)
//                                .putExtra("PaymentDetails", paymentDetails)
//                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void getPayment(String payment_amount) {
        //Getting the amount from editText
//      String  paymentAmount = editTextAmount.getText().toString();

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(payment_amount)), "USD", "Item price",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PaypalImplementation.getInstance().getPPConfigrationInstance());

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
}
