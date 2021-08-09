package com.studentlifemanager.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.studentlifemanager.R;
import com.studentlifemanager.helper.Utils;

public class BrowserFragment extends Fragment {

    private String TAG = BrowserFragment.class.getSimpleName();
    private WebView webView;
    private float m_downX;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;


    public BrowserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browser, container, false);

        //setupView
        setupView(view);


        return view;
    }

    private void setupView(View view) {

        //init class
        setHasOptionsMenu(true);

        //init view
        webView = (WebView) view.findViewById(R.id.webView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.main_content);

        initWebView();

        webView.loadUrl("https://www.google.com");
    }

    private void initWebView() {
        webView.setWebChromeClient(new MyWebChromeClient(getActivity()));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                getActivity().invalidateOptionsMenu();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }

                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.browser, menu);

        if (Utils.isBookmarked(getActivity(), webView.getUrl())) {
            // change icon color
            Utils.tintMenuIcon(getActivity(), menu.getItem(0), R.color.dark_gray);
        } else {
            Utils.tintMenuIcon(getActivity(), menu.getItem(0), android.R.color.white);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        // menu item 0-index is bookmark icon

        // enable - disable the toolbar navigation icons
        if (!webView.canGoBack()) {
            menu.getItem(1).setEnabled(false);
            menu.getItem(1).getIcon().setAlpha(130);
        } else {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).getIcon().setAlpha(255);
        }

        if (!webView.canGoForward()) {
            menu.getItem(2).setEnabled(false);
            menu.getItem(2).getIcon().setAlpha(130);
        } else {
            menu.getItem(2).setEnabled(true);
            menu.getItem(2).getIcon().setAlpha(255);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_bookmark) {
            // bookmark / unbookmark the url
            Utils.bookmarkUrl(getActivity(), webView.getUrl());

            String msg = Utils.isBookmarked(getActivity(), webView.getUrl()) ?
                    webView.getTitle() + "is Bookmarked!" :
                    webView.getTitle() + " removed!";
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
            snackbar.show();


            // refresh the toolbar icons, so that bookmark icon color changes
            // depending on bookmark status
            getActivity().invalidateOptionsMenu();
        }

        if (item.getItemId() == R.id.action_back) {
            back();
        }

        if (item.getItemId() == R.id.action_forward) {
            forward();
        }

        return super.onOptionsItemSelected(item);
    }

    // backward the browser navigation
    private void back() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    // forward the browser navigation
    private void forward() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }
}