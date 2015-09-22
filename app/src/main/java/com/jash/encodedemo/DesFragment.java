package com.jash.encodedemo;


import android.os.Bundle;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

//try {
//        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
//        KeyPair pair = rsa.generateKeyPair();
//        System.out.println("mod:" + ((RSAPublicKey) pair.getPublic()).getModulus().toString());
//        System.out.println("公钥:" + ((RSAPublicKey) pair.getPublic()).getPublicExponent().toString());
//        System.out.println("私钥:" + ((RSAPrivateKey) pair.getPrivate()).getPrivateExponent().toString());
//        } catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//        }
/**
 * Des:数据标准加密，是一种对称加密，密钥为8位byte数组
 * 3Des:对称加密，密钥为24位byte数组 DESede 就是des算法做三次，加解加
 * AES:高级数据标准加密，对称加密，密钥为32位byte数组
 * RSA:非对称加密，
 */
public class DesFragment extends Fragment implements View.OnClickListener {


    private EditText edit_key;
    private EditText edit_src;
    private EditText edit_res;
    private String mod = "101135391169380975888313359539585667957017107504198369172068657107792479137615224346518381472688963742319160978574569992384908939522194055396760889865073182129383643304231512201667253482861428868489518558363226916557323061644694060254592931537134014739133191997600452544997222109159244367816410608593116951449";
    private String publicKey = "65537";
    private String privateKey = "205242947121895567284826537967329811225464627585308804185195101932288626658266701833879255014230620530821496408905165158417274043005505430028368682607606855708130968163551742165053180417139435712199272213075142246893247857957525198949188430831719450756208942343947389942691890276260606382089608182090055297";

    public DesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentg
        return inflater.inflate(R.layout.fragment_des, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_key = ((EditText) view.findViewById(R.id.des_key));
        edit_src = ((EditText) view.findViewById(R.id.des_src));
        edit_res = ((EditText) view.findViewById(R.id.des_res));
        view.findViewById(R.id.des_encrypt).setOnClickListener(this);
        view.findViewById(R.id.des_decrypt).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        try {
            KeyFactory rsa = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(mod), new BigInteger(publicKey));
            PublicKey publicKey = rsa.generatePublic(spec);
            RSAPrivateKeySpec spec1 = new RSAPrivateKeySpec(new BigInteger(mod), new BigInteger(privateKey));
            PrivateKey privateKey = rsa.generatePrivate(spec1);
            Cipher cipher = Cipher.getInstance("RSA");
            switch (v.getId()){
                case R.id.des_encrypt:
                    String src = edit_src.getText().toString();
                    if (!TextUtils.isEmpty(src)) {
                        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                        byte[] doFinal = cipher.doFinal(src.getBytes("UTF-8"));
                        edit_res.setText(Base64.encodeToString(doFinal, Base64.DEFAULT));
                    }
                    break;
                case R.id.des_decrypt:
                    String res = edit_res.getText().toString();
                    if (!TextUtils.isEmpty(res)) {
                        cipher.init(Cipher.DECRYPT_MODE, privateKey);
                        byte[] doFinal = cipher.doFinal(Base64.decode(res, Base64.DEFAULT));
                        edit_src.setText(new String(doFinal, "UTF-8"));
                    }
                    break;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void onClick(View v) {
//        String key = edit_key.getText().toString();
//        if (!TextUtils.isEmpty(key)) {
//            try {
//                byte[] b = new byte[32];
//                byte[] bytes = key.getBytes("UTF-8");
//                System.arraycopy(bytes, 0, b, 0, Math.min(bytes.length, b.length));
//                //生成一个Des的密钥
//                SecretKeySpec des_key = new SecretKeySpec(b, "AES");
//                //加解密工具
//                Cipher cipher = Cipher.getInstance("AES");
//                switch (v.getId()){
//                    case R.id.des_encrypt:
//                        String src = edit_src.getText().toString();
//                        if (!TextUtils.isEmpty(src)) {
//                            //初始化模式和密钥
//                            cipher.init(Cipher.ENCRYPT_MODE, des_key);
//                            byte[] doFinal = cipher.doFinal(src.getBytes("UTF-8"));
//                            edit_res.setText(Base64.encodeToString(doFinal, Base64.DEFAULT));
//                        }
//                        break;
//                    case R.id.des_decrypt:
//                        String res = edit_res.getText().toString();
//                        if (!TextUtils.isEmpty(res)) {
//                            cipher.init(Cipher.DECRYPT_MODE, des_key);
//                            byte[] doFinal = cipher.doFinal(Base64.decode(res, Base64.DEFAULT));
//                            edit_src.setText(new String(doFinal, "UTF-8"));
//                        }
//                        break;
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (NoSuchPaddingException e) {
//                e.printStackTrace();
//            } catch (InvalidKeyException e) {
//                e.printStackTrace();
//            } catch (BadPaddingException e) {
//                e.printStackTrace();
//            } catch (IllegalBlockSizeException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
