package io.github.d2edev.syhelper;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import io.github.d2edev.syhelper.entities.Letter;
import io.github.d2edev.syhelper.logic.CreateListener;
import io.github.d2edev.syhelper.logic.MyDragListener;
import io.github.d2edev.syhelper.logic.MyTouchListener;

public class MainActivity extends AppCompatActivity implements CreateListener {
    public static final String TAG = "TAG_MainActivity";

    private Letter[] letters;
    private int rowLength;
    private TextView alphabetItem;
    private int unit;
    private LayoutInflater layoutInflater;
    private TableRow tableRow01;
    private TableRow tableRow02;
    private TableRow tableRow03;
    private String abb_line01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        findViewById(R.id.container_big_letters).setOnDragListener(new MyDragListener(this));
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout)findViewById(R.id.container_big_letters)).removeAllViews();
            }
        });


        tableRow01 = (TableRow) findViewById(R.id.row01);
        tableRow02 = (TableRow) findViewById(R.id.row02);
        tableRow03 = (TableRow) findViewById(R.id.row03);

        TableRow temp;
        for (int i = 0; i < 11; i++) {

            tableRow01.addView(createMyTextView(String.valueOf(letters[i].getLetter())));

        }
        for (int i = 11; i < 22; i++) {

            tableRow02.addView(createMyTextView(String.valueOf(letters[i].getLetter())));

        }
        for (int i = 22; i < 33; i++) {

            tableRow03.addView(createMyTextView(String.valueOf(letters[i].getLetter())));

        }


    }

    @Override
    public View createMyTextView(String letter) {
        TableRow temp;
        switch (getLetterRow(letter)){
            case 1:{
                temp=tableRow01;
                break;
            }
            case 2:{
                temp=tableRow02;
                break;
            }
            case 3:{
                temp=tableRow03;
                break;
            }
            default: {
                temp=tableRow01;
            }
        }

        View view = layoutInflater.inflate(R.layout.alphabet_item, temp, false);
        alphabetItem = (TextView) view.findViewById(R.id.textView);
        alphabetItem.setText(letter);

        if (isVovel(letter)) {
            alphabetItem.setTextColor(Color.RED);
        } else {
            alphabetItem.setTextColor(Color.BLUE);
        }
        alphabetItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, unit * 8);
        alphabetItem.setOnTouchListener(new MyTouchListener(this));
        return alphabetItem;
    }

    private int getLetterRow(String letter) {
        int row=1;
        String alphabet = getString(R.string.alphabet_russian);
        for (int i = 0; i <alphabet.length(); i++) {
            if(letter.charAt(0)==alphabet.charAt(i)){
                row=i/rowLength;
                break;
            }

        }
        return row;
    }

    private boolean isVovel(String letter) {
        boolean isVovel = false;
        String vovels = getString(R.string.alphabet_russian_vowels);
        for (int i = 0; i < vovels.length(); i++) {
            if (letter.charAt(0) == vovels.charAt(i)) {
                isVovel = true;
                break;
            }

        }
        return isVovel;
    }

    public int getUnit() {
        return unit;
    }

    private void initVariables() {

        layoutInflater = getLayoutInflater();
        //set quantizer for UI
        unit = currSideLimit() / 90;

        String alphabet = getString(R.string.alphabet_russian);
        String vovels = getString(R.string.alphabet_russian_vowels);
        //set max qty for letters in row
        rowLength = alphabet.length() / 3;
        //init and fill letters array
        letters = new Letter[alphabet.length()];
        for (int i = 0; i < alphabet.length(); i++) {
            letters[i] = new Letter();
            letters[i].setLetter(alphabet.charAt(i));
            //check if letter is vovel
            for (int j = 0; j < vovels.length(); j++) {
                if (alphabet.charAt(i) == vovels.charAt(j)) {
                    letters[i].setVovel(true);
                    break;
                }
            }
        }

    }

    public int currSideLimit() {
        int sideLimit;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        sideLimit = metrics.heightPixels;
//        if (sideLimit > metrics.heightPixels) {
//            sideLimit = metrics.heightPixels - getActionBarHeight();
//        }
//        //Log.d(TAG, " " + metrics.heightPixels + " "+ metrics.widthPixels);
        Log.d(TAG, " " + sideLimit);
//        //Log.d(TAG, " " +getActionBarHeight());

        return sideLimit;
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,
                    true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, getResources().getDisplayMetrics());
        } else {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


}
