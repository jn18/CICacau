package com.example.jose.cicacau.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jose.cicacau.R;


public class Site extends Fragment{
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View fragmentSite = inflater.inflate(R.layout.fragment_site, container, false);

        return fragmentSite;
    }

}