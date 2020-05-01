package com.teguh.myapplication;

import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
public class CaesarChipherFragment extends Fragment {
    private View view;
    private String[] abjad = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    private String[] abjad2 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    private String[] eabjad = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    private String[] eabjad2 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private EditText edtKey, edtPlainText, edtChiperText, edtPlainText2, edtChiperText2;
    private CircularProgressButton btnSave, btnDekrip;
    private int key;
    private String plainText, chiperText = "", plainText2 = "", chiperText2;
    private SwipeRefreshLayout swp;
    public CaesarChipherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_caesar_chipher, container, false);
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
                key = Integer.parseInt(edtKey.getText().toString());
                if (TextUtils.isEmpty(edtKey.getText())) {
                    edtKey.setError("Required");
                    eror++;
                }
                if (Integer.parseInt(edtKey.getText().toString()) > 26) {
                    key = key % 26;
//                     edtKey.setError("Value Cannot > 26");
//                     eror++;
                }
                if (TextUtils.isEmpty(edtChiperText2.getText())) {
                    edtChiperText2.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    edtPlainText2.setText("");
                    plainText2 = "";

                    int keyTemp = 0;
                    int keyTemp2 = 0;
                    for (int i = 0; i < 26; i++) {
                        keyTemp = i+key;
                        if (keyTemp >= 26) {
                            eabjad2[i] = eabjad[keyTemp2];
                            keyTemp2++;
                        } else {
                            eabjad2[i] = eabjad[keyTemp];
                        }
                    }

                    chiperText2 = edtChiperText2.getText().toString();
//                Log.e("plaintext", String.valueOf(plainText.length()));
                    for (int i = 0; i < chiperText2.length(); i++){
//                     Log.e("plaintext", String.valueOf(plainText.charAt(i)));
                        if(String.valueOf(chiperText2.charAt(i)).equals(" ")) {
                            plainText2 = plainText2 +":";
                        } else {
                            for(int j = 0; j < 26; j++) {
//                         Log.e("abjad", String.valueOf(abjad[j]));
                                if(eabjad2[j].equals(String.valueOf(chiperText2.charAt(i)))) {
                                    plainText2 = plainText2 +""+ eabjad[j];
                                    Log.e("eabjad", String.valueOf(eabjad[j]));
                                }
                            }
                        }

                    }
//                for (int i = 0; i < 26; i++) {
//                    Log.e("abjad2", String.valueOf(abjad2[i]));
//                }
                    edtPlainText2.setText(plainText2);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eror = 0;
                key = Integer.parseInt(edtKey.getText().toString());
                if (TextUtils.isEmpty(edtKey.getText())) {
                    edtKey.setError("Required");
                    eror++;
                }
                if (Integer.parseInt(edtKey.getText().toString()) > 26) {
                    key = key % 26;
//                     edtKey.setError("Value Cannot > 26");
//                     eror++;
                }
                if (TextUtils.isEmpty(edtPlainText.getText())) {
                    edtPlainText.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    edtChiperText.setText("");
                    chiperText = "";

                    int keyTemp = 0;
                    int keyTemp2 = 0;
                    for (int i = 0; i < 26; i++) {
                        keyTemp = i+key;
                        if (keyTemp >= 26) {
                            abjad2[i] = abjad[keyTemp2];
                            keyTemp2++;
                        } else {
                            abjad2[i] = abjad[keyTemp];
                        }
                    }

                    plainText = edtPlainText.getText().toString();
//                Log.e("plaintext", String.valueOf(plainText.length()));
                    for (int i = 0; i < plainText.length(); i++){
//                     Log.e("plaintext", String.valueOf(plainText.charAt(i)));
                        if(String.valueOf(plainText.charAt(i)).equals(" ")) {
                            chiperText = chiperText +":";
                        } else {
                            for(int j = 0; j < 26; j++) {
//                         Log.e("abjad", String.valueOf(abjad[j]));
                                if(abjad[j].equals(String.valueOf(plainText.charAt(i)))) {
                                    chiperText = chiperText +""+ abjad2[j];
                                    Log.e("abjad2", String.valueOf(abjad2[j]));
                                }
                            }
                        }

                    }
//                for (int i = 0; i < 26; i++) {
//                    Log.e("abjad2", String.valueOf(abjad2[i]));
//                }
                    edtChiperText.setText(chiperText);
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
}
