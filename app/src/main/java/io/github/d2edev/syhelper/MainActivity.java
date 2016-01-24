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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import io.github.d2edev.syhelper.logic.CreateListener;
import io.github.d2edev.syhelper.logic.MyDragListener;
import io.github.d2edev.syhelper.logic.MyTouchListener;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG_MainActivity";


    private int unit;
    private LayoutInflater layoutInflater;
    private TableRow[] tableRow = new TableRow[3];
    private String[] letterRow;
    private String vovels;
    private String[] consonants;
    public static final int BIG_TEXT_MULTIPLIER = 28;
    public static final int SMALL_TEXT_MULTIPLIER = 6;
    public static final int SMALL_TEXT_BOX_MULTIPLIER = 10;
    public static final int BUTTON_TEXT_MULTIPLIER = 4;
    //assuming we work with 16:9 screen
    public static final int HEIGHT_DIVIDER = 90;
    private boolean easySyllable = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        initUI();


    }

    private void initUI() {
        //set drag listener on "accepting" container
        findViewById(R.id.container_big_letters).setOnDragListener(new MyDragListener(this));
        //set click listener which removes all letters from contaner on button click
        Button btnRandom = (Button) findViewById((R.id.btn_random));
        btnRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX, unit * BUTTON_TEXT_MULTIPLIER);
        Button btnClear = (Button) findViewById(R.id.btn_clear);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRandomSyllable();
            }
        });
        btnClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, unit * BUTTON_TEXT_MULTIPLIER);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.container_big_letters)).removeAllViews();
            }
        });


        for (int i = 0; i < letterRow.length; i++) {
            for (int j = 0; j < letterRow[i].length(); j++) {
                tableRow[i].addView(createMyTextView(String.valueOf(letterRow[i].charAt(j)),SMALL_TEXT_MULTIPLIER));
            }

        }

    }

    private void showRandomSyllable() {
        LinearLayout container = (LinearLayout) findViewById(R.id.container_big_letters);
        container.removeAllViews();
        String temp_consonants= consonants[0]+(easySyllable?"":consonants[1]);
        int random_index=(int)(Math.random()*temp_consonants.length());
        View view = createMyTextView(temp_consonants.substring(random_index,random_index+1),BIG_TEXT_MULTIPLIER);
        container.addView(view);

    }

    public View createMyTextView(String letter, int sizeMultiplier) {
        View view = layoutInflater.inflate(R.layout.alphabet_item, tableRow[getRowIndex(letter)], false);
        TextView alphabetItem = (TextView) view.findViewById(R.id.textView);
        alphabetItem.setText(letter);

        if (isVovel(letter)) {
            alphabetItem.setTextColor(Color.RED);
        } else {
            alphabetItem.setTextColor(Color.BLUE);
        }
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) alphabetItem.getLayoutParams();
        llp.width = unit * SMALL_TEXT_BOX_MULTIPLIER;
        llp.height = unit * SMALL_TEXT_BOX_MULTIPLIER;
        alphabetItem.setLayoutParams(llp);
        alphabetItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, unit * sizeMultiplier);

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
        unit = currSideLimit() / HEIGHT_DIVIDER;
        tableRow[0] = (TableRow) findViewById(R.id.row01);
        tableRow[1] = (TableRow) findViewById(R.id.row02);
        tableRow[2] = (TableRow) findViewById(R.id.row03);
        letterRow = getString(R.string.alphabet_russian).split(" ");
        consonants = getString(R.string.alphabet_russian_consonants).split(" ");

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
