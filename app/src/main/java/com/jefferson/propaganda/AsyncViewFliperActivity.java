package com.jefferson.propaganda;


import android.os.AsyncTask;


public class AsyncViewFliperActivity extends AsyncTask<Object, Object, Object> {

    private String url = "https://gist.githubusercontent.com/cabrall10/0db36b0d5cb0134d0e703a053a092a0e/raw/877c1df95fc0d64830328a620e9d835c2a917f4c/propagandas.json";


    @Override
    protected Object doInBackground(Object... strings) {
        return HttpRequest.get(url).body();
    }

}
