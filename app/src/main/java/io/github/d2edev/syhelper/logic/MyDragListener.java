package io.github.d2edev.syhelper.logic;

import android.app.Activity;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.d2edev.syhelper.DraggingFragment;
import io.github.d2edev.syhelper.MainActivity;
import io.github.d2edev.syhelper.R;

/**
 * Created by d2e on 22.01.16.
 */
public class MyDragListener implements View.OnDragListener {

    private Fragment fragment;

    public MyDragListener(Fragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public boolean onDrag(View v, DragEvent event) {
        View view = (View) event.getLocalState();
        ViewGroup owner = (ViewGroup) view.getParent();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundResource(R.drawable.big_letter_container_drop);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundResource(R.drawable.big_letter_container);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                if (owner != null) {
                    owner.removeView(view);
                }
                LinearLayout container = (LinearLayout) v;
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((DraggingFragment) fragment).getUnit() * MainActivity.BIG_TEXT_MULTIPLIER);
                ((TextView) view).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundResource(R.drawable.big_letter_container);
                if (dropEventNotHandled(event)) {
                    if (owner != null) {
                        owner.removeView(view);
                    }
                    //                  view.setVisibility(View.VISIBLE);
                }
            default:
                break;
        }

        return true;
    }

    private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
    }
}