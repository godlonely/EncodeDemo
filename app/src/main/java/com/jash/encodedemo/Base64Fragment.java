package com.jash.encodedemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyPairGenerator;

import javax.crypto.spec.SecretKeySpec;


/**
 * A simple {@link Fragment} subclass.
 */
public class Base64Fragment extends Fragment implements View.OnClickListener {


    private EditText edit_src;
    private EditText edit_res;

    public Base64Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base64, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_src = ((EditText) view.findViewById(R.id.base64_src));
        edit_res = ((EditText) view.findViewById(R.id.base64_res));
        view.findViewById(R.id.base64_encode).setOnClickListener(this);
        view.findViewById(R.id.base64_decode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.base64_encode:
                String src = edit_src.getText().toString();
                if (!TextUtils.isEmpty(src)) {
//                    try {
//                        String string = Base64.encodeToString(src.getBytes("UTF-8"), Base64.DEFAULT);
//                        edit_res.setText(string);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        String encode = URLEncoder.encode(src, "UTF-8");
                        edit_res.setText(encode);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.base64_decode:
                String res = edit_res.getText().toString();
                if (!TextUtils.isEmpty(res)) {
//                    byte[] decode = Base64.decode(res, Base64.DEFAULT);
//                    try {
//                        edit_src.setText(new String(decode, "UTF-8"));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        String decode = URLDecoder.decode(res, "UTF-8");
                        edit_src.setText(decode);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }
}
