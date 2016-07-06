package io.github.d2edev.syhelper;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import io.github.d2edev.syhelper.util.Utiltity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG_MainActivity";


    //TODO move to single activity
    //TODO structure code

    public static final int BIG_TEXT_MULTIPLIER = 28;
    public static final int SMALL_TEXT_MULTIPLIER = 6;
    public static final int SMALL_TEXT_BOX_MULTIPLIER = 10;
    public static final int BUTTON_TEXT_MULTIPLIER = 4;
    //assuming we work with 16:9 screen
    public static final int HEIGHT_DIVIDER = 90;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container_main,new DraggingFragment(),null);
        transaction.commit();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:{
                break;
            }
            case R.id.action_about:{
                showAboutInfo();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAboutInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.action_about_info01))
                .setMessage(getString(R.string.action_about_info02))
                .setIcon(R.drawable.logo_small)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }




}
