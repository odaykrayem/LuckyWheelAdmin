package com.example.luckywheeladmin;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class EndDrawerToggle implements DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private DrawerArrowDrawable arrowDrawable;
    private AppCompatImageButton toggleButton;
    private String openDrawerContentDesc;
    private String closeDrawerContentDesc;

    private Button firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn;


    Activity activity;
    public EndDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                           int openDrawerContentDescRes, int closeDrawerContentDescRes) {

        this.activity = activity;
        this.drawerLayout = drawerLayout;
        this.openDrawerContentDesc = activity.getString(openDrawerContentDescRes);
        this.closeDrawerContentDesc = activity.getString(closeDrawerContentDescRes);

        arrowDrawable = new DrawerArrowDrawable(toolbar.getContext());
        arrowDrawable.setDirection(DrawerArrowDrawable.ARROW_DIRECTION_END);
        toggleButton = new AppCompatImageButton(toolbar.getContext(), null,
                R.attr.toolbarNavigationButtonStyle);
        toolbar.addView(toggleButton, new Toolbar.LayoutParams(GravityCompat.END));
        toggleButton.setImageDrawable(arrowDrawable);
        toggleButton.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                toggle();
                                            }
                                        }
        );
    }

    public void syncState() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            setPosition(1f);
        } else {
            setPosition(0f);
        }
    }

    public void toggle() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void setPosition(float position) {
        if (position == 1f) {
            arrowDrawable.setVerticalMirror(true);
            toggleButton.setContentDescription(closeDrawerContentDesc);
        } else if (position == 0f) {
            arrowDrawable.setVerticalMirror(false);
            toggleButton.setContentDescription(openDrawerContentDesc);
        }
        arrowDrawable.setProgress(position);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        setPosition(Math.min(1f, Math.max(0, slideOffset)));
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        setPosition(1f);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        firstBtn = drawerView.findViewById(R.id.menu_btn_First);
        secondBtn =  drawerView.findViewById(R.id.menu_btn_second);
        thirdBtn = drawerView.findViewById(R.id.menu_btn_third);
        fourthBtn = drawerView.findViewById(R.id.menu_btn_fourth);
        fifthBtn = drawerView.findViewById(R.id.menu_btn_fifth);

        firstBtn.setPressed(DrawerButtonsState.FIRST_BTN);
        secondBtn.setPressed(DrawerButtonsState.SECOND_BTN);
        thirdBtn.setPressed(DrawerButtonsState.THIRD_BTN);
        fourthBtn.setPressed(DrawerButtonsState.FOURTH_BTN);
        fifthBtn.setPressed(DrawerButtonsState.FIFTH_BTN);

        setPosition(0f);

    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
}
