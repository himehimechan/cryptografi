package com.teguh.myapplication;

import android.os.Bundle;

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
public class HillChiperFragment extends Fragment {
    private View view;
    private String[] abjad = {"z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y"};
    private EditText edtKey1, edtKey2, edtKey3, edtKey4, edtPlainText, edtChiperText, edtPlainText2, edtChiperText2;
    private CircularProgressButton btnSave, btnDekrip;
    private String key1, key2, key3, key4;
    private String plainText, chiperText = "", plainText2 = "", chiperText2;
    private SwipeRefreshLayout swp;
    public HillChiperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hill_chiper, container, false);
        edtKey1= view.findViewById(R.id.edtKey1);
        edtKey2= view.findViewById(R.id.edtKey2);
        edtKey3= view.findViewById(R.id.edtKey3);
        edtKey4= view.findViewById(R.id.edtKey4);
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
                key1 = edtKey1.getText().toString();
                if (TextUtils.isEmpty(edtKey1.getText())) {
                    edtKey1.setError("Required");
                    eror++;
                }
                key2 = edtKey2.getText().toString();
                if (TextUtils.isEmpty(edtKey2.getText())) {
                    edtKey2.setError("Required");
                    eror++;
                }
                key3 = edtKey3.getText().toString();
                if (TextUtils.isEmpty(edtKey3.getText())) {
                    edtKey3.setError("Required");
                    eror++;
                }
                key4 = edtKey4.getText().toString();
                if (TextUtils.isEmpty(edtKey4.getText())) {
                    edtKey4.setError("Required");
                    eror++;
                }

                if (TextUtils.isEmpty(edtChiperText2.getText())) {
                    edtChiperText2.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    String shiftedString;
//                    decryptAlgorithm(edtChiperText2.getText().toString());
                    shiftedString = decryptAlgorithm(edtChiperText2.getText().toString());
                    edtPlainText2.setText(shiftedString);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eror = 0;
                key1 = edtKey1.getText().toString();
                if (TextUtils.isEmpty(edtKey1.getText())) {
                    edtKey1.setError("Required");
                    eror++;
                }
                key2 = edtKey2.getText().toString();
                if (TextUtils.isEmpty(edtKey2.getText())) {
                    edtKey2.setError("Required");
                    eror++;
                }
                key3 = edtKey3.getText().toString();
                if (TextUtils.isEmpty(edtKey3.getText())) {
                    edtKey3.setError("Required");
                    eror++;
                }
                key4 = edtKey4.getText().toString();
                if (TextUtils.isEmpty(edtKey4.getText())) {
                    edtKey4.setError("Required");
                    eror++;
                }
                if (TextUtils.isEmpty(edtPlainText.getText())) {
                    edtPlainText.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    String shiftedString;
                    String plainText = edtPlainText.getText().toString().replace(" ", "");
                    shiftedString = encryptAlgorithm(plainText);
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
                edtKey1.setText("");
                edtKey2.setText("");
                edtKey3.setText("");
                edtKey4.setText("");
                edtPlainText.setText("");
                edtChiperText.setText("");
                edtPlainText2.setText("");
                edtChiperText2.setText("");
            }
        });
        return view;
    }

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }

    private String decryptAlgorithm(String chiperText) {
        String plaintext = "";
        double tempKey1 = -Integer.parseInt(key4);
        double tempKey2 = Integer.parseInt(key2);
        double tempKey3 = Integer.parseInt(key3);
        double tempKey4 = -Integer.parseInt(key1);

        int pengali = (Integer.parseInt(key1) * Integer.parseInt(key4)) + (Integer.parseInt(key2) * Integer.parseInt(key3));

        tempKey2 /= tempKey1;
        tempKey3 /= tempKey1;
        tempKey4 /= tempKey1;
        tempKey1 /= tempKey1;

        tempKey1 *= pengali;
        tempKey2 *= pengali;
        tempKey3 *= pengali;
        tempKey4 *= pengali;

        int key1Temp = mod((int) tempKey1, 26);
        int key2Temp = mod((int) tempKey2, 26);
        int key3Temp = mod((int) tempKey3, 26);
        int key4Temp = mod((int) tempKey4, 26);

        Log.e("tempkey1", String.valueOf(key1Temp));
        Log.e("tempkey2", String.valueOf(key2Temp));
        Log.e("tempkey3", String.valueOf(key3Temp));
        Log.e("tempkey4", String.valueOf(key4Temp));
        int loop = chiperText.length();
        if (loop % 2  != 0) {
            chiperText += chiperText.charAt(chiperText.length()-1);
        }
        loop = chiperText.length();
        for(int i = 0; i < loop; i++) {
            String ch1 = String.valueOf(chiperText.charAt(i));
            i = i+1;
            String ch2 = String.valueOf(chiperText.charAt(i));
            int temp1 = 0;
            int temp2 = 0;
            for(int j = 0; j < abjad.length; j++) {
                if (ch1.equals(abjad[j])) {
                    temp1 = j;
                }
                if (ch2.equals(abjad[j])) {
                    temp2 = j;
                }
            }
            int value1, value2;
            value1 = (key1Temp * temp1) + (key2Temp * temp2);
            value2 = (key3Temp * temp1) + (key4Temp * temp2);
            value1 = mod(value1, 26);
            value2 = mod(value2, 26);

            Log.d("value1", String.valueOf(value1));
            Log.d("value2", String.valueOf(value2));
            plaintext += abjad[value1];
            plaintext += abjad[value2];
            Log.d("valueplain", plaintext);
        }
        return plaintext;
    }


    private String encryptAlgorithm(String plainText) {
        String chipertext = "";

        int loop = plainText.length();
        if (loop % 2  != 0) {
            plainText += plainText.charAt(plainText.length()-1);
        }
        loop = plainText.length();
        for(int i = 0; i < loop; i++) {
            String ch1 = String.valueOf(plainText.charAt(i));
            i = i+1;
            String ch2 = String.valueOf(plainText.charAt(i));
            int temp1 = 0;
            int temp2 = 0;
            for(int j = 0; j < abjad.length; j++) {
                if (ch1.equals(abjad[j])) {
                    temp1 = j;
                }
                if (ch2.equals(abjad[j])) {
                    temp2 = j;
                }
            }
            int value1, value2;
            value1 = (Integer.parseInt(key1) * temp1) + (Integer.parseInt(key2) * temp2);
            value2 = (Integer.parseInt(key3) * temp1) + (Integer.parseInt(key4) * temp2);
            value1 = mod(value1, 26);
            value2 = mod(value2, 26);

            Log.d("value1", String.valueOf(value1));
            Log.d("value2", String.valueOf(value2));
            chipertext += abjad[value1];
            chipertext += abjad[value2];
            Log.d("valuechiper", chipertext);
        }

        return chipertext;
    }

}
