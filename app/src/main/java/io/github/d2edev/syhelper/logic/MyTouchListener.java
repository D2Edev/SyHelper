package io.github.d2edev.syhelper.logic;

import android.app.Fragment;
import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import io.github.d2edev.syhelper.DraggingFragment;
import io.github.d2edev.syhelper.MainActivity;
import io.github.d2edev.syhelper.R;

/**
 * Created by d2e on 22.01.16.
 */
public class MyTouchListener implements View.OnTouchListener {
    private Fragment fragment;

    public MyTouchListener(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            String letter=((TextView)view).getText().toString();
            ViewGroup owner = (ViewGroup) view.getParent();
            if(((ViewGroup) view.getParent()).getId()==R.id.container_big_letters){
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
            }else{
                View newView = ((DraggingFragment)fragment).createMyTextView(letter, MainActivity.SMALL_TEXT_MULTIPLIER);
                view.startDrag(data, shadowBuilder, newView, 0);
            }
            return true;
        } else {
            return false;
        }
    }
}
