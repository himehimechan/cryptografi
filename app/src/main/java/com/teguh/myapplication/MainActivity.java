package com.teguh.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());
        bottomNavigationView.setSelectedItemId(R.id.navBotHome);

        if (savedInstanceState == null){
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainerMain, new CaesarChipherFragment(),
                            Utils.TAG_FRAGMENT_HOME).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navBotHome:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frameContainerMain, new CaesarChipherFragment(),
                                    Utils.TAG_FRAGMENT_HOME).commit();
                    return true;
                case R.id.navBotVegence:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frameContainerMain, new VigenereCipherFragment(),
                                    Utils.TAG_FRAGMENT_VIGENERE).commit();
                    return true;
                case R.id.navBotHill:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frameContainerMain, new HillChiperFragment(),
                                    Utils.TAG_FRAGMENT_HILL).commit();
                    return true;
                case R.id.navBotAes:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frameContainerMain, new AesFragment(),
                                    Utils.TAG_FRAGMENT_AES).commit();
                    return true;
                case R.id.navBotLogout:
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure to logout?")
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    finish();
                                }
                            })
                            .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                    return true;
            }
            return false;
        }
    };
}
