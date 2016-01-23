package io.github.d2edev.syhelper;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import io.github.d2edev.syhelper.logic.CreateListener;
import io.github.d2edev.syhelper.logic.MyDragListener;
import io.github.d2edev.syhelper.logic.MyTouchListener;

public class MainActivity extends AppCompatActivity implements CreateListener {
    public static final String TAG = "TAG_MainActivity";


    private int unit;
    private LayoutInflater layoutInflater;
    private TableRow[] tableRow = new TableRow[3];
    private String[] letterRow = new String[3];
    private String vovels;
    public static final int BIG_LETTER_MULTIPLIER=32;
    public static final int SMALL_LETTER_MULTIPLIER=8;
    public static final int SMALL_LETTER_BOX_MULTIPLIER=11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        //set drag listener on "accepting" container
        findViewById(R.id.container_big_letters).setOnDragListener(new MyDragListener(this));
        //set click listener which removes all letters from contaner on button click
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.container_big_letters)).removeAllViews();
            }
        });



        for (int i = 0; i < letterRow.length; i++) {
            for (int j = 0; j < letterRow[i].length(); j++) {
                tableRow[i].addView(createMyTextView(String.valueOf(letterRow[i].charAt(j))));
            }

        }


    }

    @Override
    public View createMyTextView(String letter) {
        View view = layoutInflater.inflate(R.layout.alphabet_item, tableRow[getRowIndex(letter)], false);
        TextView alphabetItem = (TextView) view.findViewById(R.id.textView);
        alphabetItem.setText(letter);

        if (isVovel(letter)) {
            alphabetItem.setTextColor(Color.RED);
        } else {
            alphabetItem.setTextColor(Color.BLUE);
        }
LinearLayout.LayoutParams llp=(LinearLayout.LayoutParams)alphabetItem.getLayoutParams();
       llp.width=unit*SMALL_LETTER_BOX_MULTIPLIER;
       llp.height=unit*SMALL_LETTER_BOX_MULTIPLIER;
        alphabetItem.setLayoutParams(llp);
        alphabetItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, unit * SMALL_LETTER_MULTIPLIER);

        alphabetItem.setOnTouchListener(new MyTouchListener(this));
        return alphabetItem;
    }

    private int getRowIndex(String letter) {
        int row = -1;
        for (int i = 0; i < letterRow.length; i++) {
            for (int j = 0; j < letterRow[i].length(); j++) {
                if (letter.charAt(0) == letterRow[i].charAt(j)) {
                    row = i;
                    break;
                }
            }
            if (row != -1) break;
        }
        return row;
    }

    private boolean isVovel(String letter) {
        boolean isVovel = false;
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
        tableRow[0] = (TableRow) findViewById(R.id.row01);
        tableRow[1] = (TableRow) findViewById(R.id.row02);
        tableRow[2] = (TableRow) findViewById(R.id.row03);
        letterRow[0] = getString(R.string.alphabet_russian_line01);
        letterRow[1] = getString(R.string.alphabet_russian_line02);
        letterRow[2] = getString(R.string.alphabet_russian_line03);
        vovels = getString(R.string.alphabet_russian_vowels);

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
