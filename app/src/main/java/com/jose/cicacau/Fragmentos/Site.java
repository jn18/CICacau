package com.jose.cicacau.Fragmentos;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jose.cicacau.R;


public class Site extends Fragment{
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View fragmentSite = inflater.inflate(R.layout.fragment_site, container, false);

        return fragmentSite;
    }

}
