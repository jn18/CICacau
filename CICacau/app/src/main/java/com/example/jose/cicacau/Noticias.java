package com.example.jose.cicacau;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Noticias extends Fragment{
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View fragmentViewNoticias = inflater.inflate(R.layout.fragment_noticias, container, false);
        //fragmentViewNoticias.setOnClickListener(this);

        return fragmentViewNoticias;
    }

    /*@Override
    public void onClick(View v) {
    }*/
}