package com.teguh.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class AesFragment extends Fragment {

    public AesFragment() {
        // Required empty public constructor
    }

    private EditText edtKey, edtPlainText, edtChiperText, edtPlainText2, edtChiperText2;
    private CircularProgressButton btnSave, btnDekrip;
    private int key;
    private String plainText, chiperText = "", plainText2 = "", chiperText2;
    private SwipeRefreshLayout swp;
    private View view;
    private String tvIV;

    KeyGenerator keyGenerator;
    SecretKey secretKey;
    byte[] secretKeyen;
    String strSecretKey;
    byte[] IV = new byte[16];
    byte[] cipherText;
    SecureRandom random;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aes, container, false);
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
                if (TextUtils.isEmpty(edtChiperText2.getText())) {
                    edtChiperText2.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    dekrip();
//                    edtPlainText2.setText(plainText2);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eror = 0;

                if (TextUtils.isEmpty(edtPlainText.getText())) {
                    edtPlainText.setError("Required");
                    eror++;
                }
                if(eror == 0) {
                    enkrip();
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

    private void enkrip (){
        try {
            /*Keygenerator simetrik şifreleme anahtarı üretmek için kullanılan bir kütüphanedir.Öncelikle KeyGenerator kurulumu yapılır
             * getInstance ile algoritma isminin bir parametre olarak alınması engellenir.*/
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(256);// init yöntemiyle oluşturulan KeyGenerator örneği başlatılır.Burada 256bit değer kullanıldı.
        secretKey = keyGenerator.generateKey();//Kurulum tamamlandıktan sonra generateKey() ile anahtar üretilir.
        secretKeyen=secretKey.getEncoded();
        strSecretKey = encoderfun(secretKeyen);
        edtKey.setText(strSecretKey);

        /*IV, Başlatma Vektörü anlamına gelir, şifreleme sırasında SecretKey ile birlikte kullanılacak isteğe bağlı bir sayıdır. IV, şifreleme işleminin başlangıcına rastgelelik ekler.
         * */
        random = new SecureRandom();
        random.nextBytes(IV);
        try {
            cipherText = encrypt(edtPlainText.getText().toString().trim().getBytes(), secretKey, IV);

            String sonuc = encoderfun(cipherText);
            edtChiperText.setText(sonuc);
            edtChiperText2.setText(sonuc);


            tvIV = encoderfun(IV);
//            edtS.setText(tvIV);

//            Utils.saveData(this, sonuc, strSecretKey, tvIV);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void dekrip(){
        try {
            String strEncText=edtChiperText2.getText().toString().trim();
            byte[] encText = decoderfun(strEncText);
            String strAnahtar=tvIV;
            byte[] iv = decoderfun(strAnahtar);
            String strSecretKey=edtKey.getText().toString().trim();
            byte[] encodedSecretKey = decoderfun(strSecretKey);
            SecretKey originalSecretKey = new SecretKeySpec(encodedSecretKey, 0, encodedSecretKey.length, "AES");

            String decryptedText = decrypt(encText,originalSecretKey,iv);
            edtPlainText2.setText(decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encoderfun(byte[] decval) {
        String conVal= Base64.encodeToString(decval,Base64.DEFAULT);
        return conVal;
    }

    public static byte[] decoderfun(String enval) {
        byte[] conVal = Base64.decode(enval,Base64.DEFAULT);
        return conVal;

    }
    private static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception
    {   /*Şifreleme sınıfı, gerçek şifreleme ve şifre çözme işlemlerini yapan sınıftır. Cipher sınıfı örneği, Cipher adını parametre olarak geçiren getInstance () yöntemi çağrılarak oluşturulur, biz AES/CBC/PKCS5Padding kullandık.*/
        Cipher cipher = Cipher.getInstance("AES");
        /*SecretKeySpec, bayt verilerini Cipher sınıfının init () yöntemine aktarılmaya uygun bir gizli anahtara dönüştürme mekanizması sağlar.*/
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        /*ivParameterSpec, bir başlatma vektörü için bir sarıcıdır, IV, IvParameterSpec'in konfigüre edilme şeklindeki rastgeleliğini alır.*/
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        /*Cipher örneği oluşturulduktan sonra, init () yöntemini çağırarak şifreli örneğini başlatmamız gerekir. 3 parametreyi init () yöntemine geçirmemiz gerekir.
         * Cipher.ENCRYPT_MODE
         * keySpec
         * ivSpec*/
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    private static String decrypt(byte[] cipherText, SecretKey key, byte[] IV)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}