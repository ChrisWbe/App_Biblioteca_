package com.example.christianquintero.app_biblioteca_;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment {
    public WebView webView;
    public ImageView imageView;


    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView)view.findViewById(R.id.webViewChat);
        imageView = (ImageView)view.findViewById(R.id.pagFalseChat);

        imageView.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.VISIBLE);

        String url = "https://aplicacionesbiblioteca.udea.edu.co/biblioappchat.php";
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());



    }
}
