package xyz.ideeva.playme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mylibrary.FullscreenActivity;

public class HomePage extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

    }
}
