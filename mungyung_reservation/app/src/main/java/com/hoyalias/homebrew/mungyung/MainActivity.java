package com.hoyalias.homebrew.mungyung;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hoyalias.homebrew.mungyung.api.RegTest;

public class MainActivity extends Activity {
    TextView txt_log = null;

    EditText edt_id = null;
    EditText edt_pw = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_log = (TextView) findViewById(R.id.txt_log);

        edt_id = (EditText) findViewById(R.id.edt_id);
        edt_pw = (EditText) findViewById(R.id.edt_pw);

//        try {
//            Process process = Runtime.getRuntime().exec("logcat -d");
//            BufferedReader bufferedReader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//
//            StringBuilder log = new StringBuilder();
//            String line = "";
//            while ((line = bufferedReader.readLine()) != null) {
//                log.append(line);
//            }
//            txt_log.setText(log.toString());
//        } catch (IOException e) {
//            // Handle Exception
//        }
    }

    public void onClickRun(View v) {
        Toast.makeText(this, "Run", Toast.LENGTH_SHORT).show();

        final String id = edt_id.getText().toString().trim();
        final String pw = edt_pw.getText().toString().trim();

        new Thread() {
            public void run() {
                RegTest.loop = true;
                RegTest.testInstance(logWatcher, id, pw);
            }
        }.start();
    }

    public void onClickStop(View v) {
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();

        RegTest.loop = false;
    }

    public void onClickLogin(View v) {
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
    }

    private RegTest.RegTestWatcher logWatcher = new RegTest.RegTestWatcher() {
        @Override
        public void onLog(final String log) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_log.setText(log);
                    txt_log.invalidate();
                }
            });
        }
    };
}
