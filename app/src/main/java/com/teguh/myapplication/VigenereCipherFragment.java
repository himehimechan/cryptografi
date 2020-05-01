package com.teguh.myapplication;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class VigenereCipherFragment extends Fragment {
    private View view;
    private EditText edtKey, edtPlainText, edtChiperText, edtPlainText2, edtChiperText2;
    private CircularProgressButton btnSave, btnDekrip;
    private String key;
    private String plainText, chiperText = "", plainText2 = "", chiperText2;
    private SwipeRefreshLayout swp;
    public VigenereCipherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vigenere_cipher, container, false);
        edtKey = view.findViewById(R.id.edtKey);
        edtPlainText = view.findViewById(R.id.edtPlaintext);
        edtChiperText = view.findViewById(R.id.edtChipertext);
        edtPlainText2 = view.findViewById(R.id.edtPlaintext2);
        edtChiperText2 = view.findViewById(R.id.edtChipertext2);
        btnSave = view.findViewById(R.id.btn_save);
        btnDekrip = view.findViewById(R.id.btn_dekrip);
        swp = view.findViewById(R.id.swpHome);

        btnDekrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eror = 0;
                key = edtKey.getText().toString();
                if (TextUtils.isEmpty(edtKey.getText())) {
                    edtKey.setError("Required");
                    eror++;
                }

                if (TextUtils.isEmpty(edtChiperText2.getText())) {
                    edtChiperText2.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    String shiftedString;
                    shiftedString = decryptAlgorithm(edtChiperText2.getText().toString(), edtKey.getText().toString());
                    edtPlainText2.setText(shiftedString);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eror = 0;
                key = edtKey.getText().toString();
                if (TextUtils.isEmpty(edtKey.getText())) {
                    edtKey.setError("Required");
                    eror++;
                }
                if (TextUtils.isEmpty(edtPlainText.getText())) {
                    edtPlainText.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    String shiftedString;
                    shiftedString = encryptAlgorithm(edtPlainText.getText().toString(), edtKey.getText().toString());
                    edtChiperText.setText(shiftedString);
                } else {
                    Toast.makeText(getActivity(), "Check Your Form", Toast.LENGTH_LONG).show();
                }
            }
        });

        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swp.setRefreshing(false);
                edtKey.setText("");
                edtPlainText.setText("");
                edtChiperText.setText("");
                edtPlainText2.setText("");
                edtChiperText2.setText("");
            }
        });
        return view;
    }

    private String decryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Character.isAlphabetic(orig)) {
                    if (Character.isUpperCase(orig)) {
                        sb.append((char)((upper - keyphrase.charAt(j) + 26) % 26 + 65));
                        ++j;
                        j %= keyphrase.length();
                    } else {
                        sb.append(Character.toLowerCase((char)((upper - keyphrase.charAt(j) + 26) % 26 + 65)));
                        ++j;
                        j %= keyphrase.length();
                    }
                } else {
                    sb.append(orig);
                }
            }
        }
        return sb.toString();
    }

    private String encryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Character.isAlphabetic(orig)) {
                    if (Character.isUpperCase(orig)) {
                        sb.append((char)((upper + keyphrase.charAt(j) - 130) % 26 + 65));
                        ++j;
                        j %= keyphrase.length();
                    } else {
                        sb.append(Character.toLowerCase((char)((upper + keyphrase.charAt(j) - 130) % 26 + 65)));
                        ++j;
                        j %= keyphrase.length();
                    }
                } else {
                    sb.append(orig);
                }
            }
        }
        return sb.toString();
    }
}
