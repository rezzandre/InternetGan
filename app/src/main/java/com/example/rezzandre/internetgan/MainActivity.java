package com.example.rezzandre.internetgan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    Spinner spin;
    ArrayAdapter<CharSequence> site;
    EditText edt;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spin = (Spinner)findViewById(R.id.spinner);
        edt = (EditText)findViewById(R.id.editText2);
        text = (TextView)findViewById(R.id.Text);

        site = ArrayAdapter.createFromResource(this, R.array.line, android.R.layout.simple_spinner_item);
        site.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(site);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());
            }
        });
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new What(this,args.getString("url_link"));

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        text.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    public void GoTo(View view) {
        String protokol , urls;
        protokol = spin.getSelectedItem().toString();
        urls = edt.getText().toString();

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        if (checkConnection()){
            text.setText("loading...");
            Bundle bundle = new Bundle();
            bundle.putString("url_link", protokol+urls);
            getSupportLoaderManager().restartLoader(0, bundle, this);
        } else {
            text.setText("tidak ada koneksi");
        }

    }
    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
