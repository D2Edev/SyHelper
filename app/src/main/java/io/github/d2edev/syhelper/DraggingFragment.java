package io.github.d2edev.syhelper;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import io.github.d2edev.syhelper.logic.MyDragListener;
import io.github.d2edev.syhelper.logic.MyTouchListener;

public class DraggingFragment extends Fragment {
    private LayoutInflater layoutInflater;
    private TableRow[] tableRow = new TableRow[3];
    private String[] letterRow;
    private String vovels;
    private String[] consonants;
    private boolean easySyllable = true;
    private MainActivity mainActivity;
    private int unit;

    public DraggingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dragging, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariables();
        initUI();
    }

    private void initUI() {

        //set drag listener on "accepting" container
        mainActivity.findViewById(R.id.container_big_letters).setOnDragListener(new MyDragListener(this));
        //set click listener which removes all letters from contaner on button click
        Button btnRandom = (Button) mainActivity.findViewById((R.id.btn_random));
        btnRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX, unit * MainActivity.BUTTON_TEXT_MULTIPLIER);
        Button btnClear = (Button) mainActivity.findViewById(R.id.btn_clear);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRandomSyllable();
            }
        });
        btnClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, unit * MainActivity.BUTTON_TEXT_MULTIPLIER);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) mainActivity.findViewById(R.id.container_big_letters)).removeAllViews();
            }
        });


        for (int i = 0; i < letterRow.length; i++) {
            for (int j = 0; j < letterRow[i].length(); j++) {
                tableRow[i].addView(createMyTextView(String.valueOf(letterRow[i].charAt(j)),MainActivity.SMALL_TEXT_MULTIPLIER));
            }

        }

    }

    private void showRandomSyllable() {
        LinearLayout container = (LinearLayout) mainActivity.findViewById(R.id.container_big_letters);
        container.removeAllViews();
        String temp_consonants= consonants[0]+(easySyllable?"":consonants[1]);
        int random_index=(int)(Math.random()*temp_consonants.length());
        View view = createMyTextView(temp_consonants.substring(random_index,random_index+1),MainActivity.BIG_TEXT_MULTIPLIER);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(view);
        random_index=(int)(Math.random()*vovels.length());
        view = createMyTextView(vovels.substring(random_index,random_index+1),MainActivity.BIG_TEXT_MULTIPLIER);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        llp.width = unit * MainActivity.SMALL_TEXT_BOX_MULTIPLIER;
        llp.height = unit * MainActivity.SMALL_TEXT_BOX_MULTIPLIER;
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



    private void initVariables() {
        mainActivity =(MainActivity)getActivity();
        layoutInflater = mainActivity.getLayoutInflater();
        //set quantizer for UI
        unit = mainActivity.currSideLimit() / MainActivity.HEIGHT_DIVIDER;
        tableRow[0] = (TableRow) mainActivity.findViewById(R.id.row01);
        tableRow[1] = (TableRow) mainActivity.findViewById(R.id.row02);
        tableRow[2] = (TableRow) mainActivity.findViewById(R.id.row03);
        letterRow = getString(R.string.alphabet_russian).split(" ");
        consonants = getString(R.string.alphabet_russian_consonants).split(" ");

        vovels = getString(R.string.alphabet_russian_vowels);

    }

    public int getUnit() {
        return unit;
    }

}
