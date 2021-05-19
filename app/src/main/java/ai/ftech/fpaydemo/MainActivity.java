package ai.ftech.fpaydemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ai.ftech.fid.AuthState;
import ai.ftech.fid.factory.AuthStateManager;
import ai.ftech.fid.factory.FID;
import ai.ftech.fid.factory.FIDAuthStateChangeCallback;
import ai.ftech.fid.factory.FIDCallbackManager;
import ai.ftech.fid.factory.FIDCallbackType;
import ai.ftech.fid.factory.FIDUserChangeCallback;
import ai.ftech.fpay.FPay;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private AuthStateManager authStateManager;
    private TextView tvFIDStatus;
    private Button btnFIDLogin;
    private Button btnViewPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FID.initialize(this, "fid-dev");
        FPay.initialize(this);
        authStateManager = AuthStateManager.getInstance(this);

        FIDCallbackManager.registerCallback(FIDCallbackType.AUTH_STATE_CHANGE, new FIDAuthStateChangeCallback() {
            @Override
            public void onAuthStateChange(@Nullable AuthState authState, @Nullable Exception e) {
                Log.e(TAG, "onAuthStateChange");
                if (authState != null && authState.isAuthorized()) {
                    FID.fetchUser(MainActivity.this);
                    btnFIDLogin.setText(R.string.fid_logged_in);
                    btnViewPayment.setVisibility(View.VISIBLE);
                } else {
                    btnFIDLogin.setText(R.string.fid_logged_out);
                    tvFIDStatus.setText(R.string.hello_please_log_in_to_view_account_information);
                    btnViewPayment.setVisibility(View.INVISIBLE);
                }
                if (e != null) {
                    tvFIDStatus.setText("An error occurred while logging in FID, please check and try again");
                }
            }
        });

        FIDCallbackManager.registerCallback(FIDCallbackType.USER_CHANGE, new FIDUserChangeCallback() {
            @Override
            public void onUserChange(@Nullable JSONObject user, @Nullable Exception e) {
                if (user != null) {
                    try {
                        String name = user.getString("name");
                        Log.e(TAG, name);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvFIDStatus.setText(String.format("%s %s", getString(R.string.hello), name));
                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                } else {
                    tvFIDStatus.setText(R.string.hello_please_log_in_to_view_account_information);
                }
                if (e != null) {
                    Log.e(TAG, e.toString());
                }
            }
        });

        btnFIDLogin = findViewById(R.id.btnFIDLogin);
        tvFIDStatus = findViewById(R.id.tvFIDStatus);
        btnViewPayment = findViewById(R.id.btnViewPayment);

        btnFIDLogin.setOnClickListener(view -> {
            if (!FPay.isFIDAuthorized(MainActivity.this)) {
                FID.login(MainActivity.this);
            } else {
                FID.logout(MainActivity.this);
            }
        });

        btnViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FPay.browserPayment(MainActivity.this);
            }
        });

        if (authStateManager.getCurrent().isAuthorized()) {
            btnFIDLogin.setText(R.string.fid_logged_in);
            FID.refreshToken(this);
        } else {
            btnFIDLogin.setText(R.string.fid_logged_out);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FIDCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}