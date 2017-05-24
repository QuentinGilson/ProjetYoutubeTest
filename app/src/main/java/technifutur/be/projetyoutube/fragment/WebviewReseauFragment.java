package technifutur.be.projetyoutube.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import technifutur.be.projetyoutube.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebviewReseauFragment extends Fragment {


    @BindView(R.id.webview_reseau)
    WebView webviewReseau;
    Unbinder unbinder;
    private String url;

    public WebviewReseauFragment() {
        // Required empty public constructor
    }

    public static WebviewReseauFragment newInstance(String url) {
        WebviewReseauFragment fragment = new WebviewReseauFragment();
        fragment.url = url;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webview_reseau, container, false);
        unbinder = ButterKnife.bind(this, view);

        WebSettings webSettings = webviewReseau.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webviewReseau.loadUrl(url);
        webviewReseau.setWebViewClient(new WebViewClient());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
