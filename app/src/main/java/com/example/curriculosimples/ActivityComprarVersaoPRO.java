/*
 * Copyright (C) 2018-2021, Wíliam Gonçalves <wgp_apps@hotmail.com>
 *
 * This file is part of Easy Curriculum.
 *
 * Easy Curriculum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.example.curriculosimples;

import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import utils.IabHelper;
import utils.IabResult;
import utils.Inventory;
import utils.Purchase;

public class ActivityComprarVersaoPRO extends AppCompatActivity {

    // Componentes.
    Button btnComprar;

    // Variáveis.
    private static final String TAG = "InAppBilling";
    private String base64EncodePublicKey;
    private IabHelper mHelper;
    private static final String SKU_PRO = "SKUID";
    static final int RC_REQUEST = 10001;
    private boolean compraFeita = false;
    private boolean isPRO = false;
    String payload;

    // Preferências
    SharedPreferences preferencias;
    public static final String PREFS_CURRICULOSIMPLES = "AppPreferences";

    // Para construir o token Payload.
    private static final char[] symbols = new char[36];

    static {
        for (int idx = 0; idx < 10; ++idx)
            symbols[idx] = (char) ('0' + idx);
        for (int idx = 10; idx < 36; ++idx)
            symbols[idx] = (char) ('a' + idx - 10);
    }

    IInAppBillingService mService;

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_versao_pro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        btnComprar = (Button) findViewById(R.id.btnComprar);

        base64EncodePublicKey = "BASE64CODE";

        mHelper = new IabHelper(this, base64EncodePublicKey);

        mHelper.enableDebugLogging(false);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup Finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                    btnComprar.setEnabled(false);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomString randomString = new RandomString(36);

                // Atribuir token a string payload.
                payload = randomString.nextString();

                mHelper.launchPurchaseFlow(ActivityComprarVersaoPRO.this, SKU_PRO, RC_REQUEST,
                        mPurchaseFinishedListener, payload);
            }
        });
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                int erroUserCanceled = result.getResponse();

                if (erroUserCanceled != -1005) { // Erro 1005 é devido ao usuário cancelar a compra.
                    Toast.makeText(getApplicationContext(), "Error purchasing: " + result,
                            Toast.LENGTH_LONG).show();
                    btnComprar.setEnabled(false);
                }
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                Toast.makeText(getApplicationContext(), "Error purchasing. Authenticity verification failed.",
                        Toast.LENGTH_LONG).show();
                btnComprar.setEnabled(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_PRO)) {
                // bought the PRO upgrade!
                Log.d(TAG, "Purchase succesfully. Congratulating user.");

                btnComprar.setEnabled(false);

                // Atualizar a preferência da compra do aplicativo Curriculo Simples.
                SharedPreferences.Editor preferencias = getSharedPreferences(PREFS_CURRICULOSIMPLES, MODE_PRIVATE).edit();
                preferencias.putBoolean("proversion", true);
                preferencias.putString("playload", payload);
                preferencias.commit();

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgCompraEfetuada),
                        Toast.LENGTH_LONG).show();

                compraFeita = true;

                onBackPressed();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                //complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            // Do we have the premium upgrade?
            Purchase proPurchase = inventory.getPurchase(SKU_PRO);
            isPRO = (proPurchase != null && verifyDeveloperPayload(proPurchase));

            // Atualizar a UI.
            if (isPRO) {
                compraFeita = true;

                SharedPreferences.Editor preferencias = getSharedPreferences(PREFS_CURRICULOSIMPLES, MODE_PRIVATE).edit();
                preferencias.putBoolean("proversion", true);
                preferencias.commit();

                // Fazer backup das preferências.
                try {
                    requestBackupPreferences();
                }
                catch (Exception ex) { }

                Toast.makeText(getApplicationContext(), "The app has already been purchased.", Toast.LENGTH_LONG).show();

                onBackPressed();
            }

            // Verificar preço do item PRO.
            try {
                ArrayList skuList = new ArrayList();
                skuList.add("proversion");

                Bundle querySkus = new Bundle();
                querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

                Bundle skuDetails = mService.getSkuDetails(3, getPackageName(), "inapp", querySkus);

                int response = skuDetails.getInt("RESPONSE_CODE");

                if (response == 0) {
                    ArrayList<String> responseList
                            = skuDetails.getStringArrayList("DETAILS_LIST");

                    for (String thisResponse : responseList) {
                        JSONObject object = new JSONObject(thisResponse);
                        String sku = object.getString("productId");
                        String price = object.getString("price");

                        if (sku.equals(SKU_PRO)) {
                            btnComprar.setText(btnComprar.getText().toString() + " " + price);
                            break;
                        }
                    }
                }
            }
            catch (Exception ex) { }
        }
    };

    public void requestBackupPreferences() {
        BackupManager backupManager = new BackupManager(this);
        backupManager.dataChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mHelper != null){
            try {
                mHelper.dispose();
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }finally{}
        }
        mHelper = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();

        if (compraFeita) {
            setResult(RESULT_OK, intent);
        }
        else {
            setResult(RESULT_CANCELED, intent);
        }

        finish();
    }

    public class RandomString {
        /*
         * static { for (int idx = 0; idx < 10; ++idx) symbols[idx] = (char)
         * ('0' + idx); for (int idx = 10; idx < 36; ++idx) symbols[idx] =
         * (char) ('a' + idx - 10); }
         */

        private final Random random = new Random();

        private final char[] buf;

        public RandomString(int length) {
            if (length < 1)
                throw new IllegalArgumentException("length < 1: " + length);
            buf = new char[length];
        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }
    }

    public final class SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();

        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }
}