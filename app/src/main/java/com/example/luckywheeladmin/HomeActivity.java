package com.example.luckywheeladmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.luckywheeladmin.Fragments.ContestsFragment;
import com.example.luckywheeladmin.Fragments.ParticipantsFragment;
import com.example.luckywheeladmin.Fragments.UsersFragment;
import com.example.luckywheeladmin.Fragments.WinnersFragment;
import com.example.luckywheeladmin.Fragments.WithdrawalRequestsFragment;
import com.google.android.material.navigation.NavigationView;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity {

        UsersFragment usersFragment;
        WinnersFragment winnersFragment;
        ContestsFragment contestsFragment;
        ParticipantsFragment participantsFragment;
        WithdrawalRequestsFragment withdrawalRequestsFragment;

        Button firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn;

        DrawerLayout drawer;
        NavigationView navigationView;
        Toolbar toolbar;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            firstBtn = findViewById(R.id.menu_btn_First);
            secondBtn = findViewById(R.id.menu_btn_second);
            thirdBtn = findViewById(R.id.menu_btn_third);
            fourthBtn = findViewById(R.id.menu_btn_fourth);
            fifthBtn = findViewById(R.id.menu_btn_fifth);

            usersFragment = new UsersFragment();
            winnersFragment = new WinnersFragment();
           contestsFragment = new ContestsFragment();
           participantsFragment = new ParticipantsFragment();
           withdrawalRequestsFragment = new WithdrawalRequestsFragment();

            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.menu_btn_first_txt);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);


            drawer = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            EndDrawerToggle toggle = new EndDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);

            toggle.syncState();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UsersFragment()).commit();
                firstBtn.setPressed(true);
                secondBtn.setPressed(false);
                thirdBtn.setPressed(false);
                fourthBtn.setPressed(false);
                fifthBtn.setPressed(false);

                toolbar.setTitle(R.string.menu_btn_first_txt);

                DrawerButtonsState.FIRST_BTN = fifthBtn.isPressed();
                DrawerButtonsState.SECOND_BTN =  secondBtn.isPressed();
                DrawerButtonsState.THIRD_BTN = thirdBtn.isPressed();
                DrawerButtonsState.FOURTH_BTN = fourthBtn.isPressed();
                DrawerButtonsState.FIFTH_BTN = fifthBtn.isPressed();


            }

        }

        public void goToFirstFragment(View view) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UsersFragment()).commit();
            Toast.makeText(HomeActivity.this, R.string.menu_btn_first_txt, Toast.LENGTH_LONG).show();
            drawer.closeDrawer(GravityCompat.START);

            firstBtn.setPressed(true);
            secondBtn.setPressed(false);
            thirdBtn.setPressed(false);
            fourthBtn.setPressed(false);
            fifthBtn.setPressed(false);

            toolbar.setTitle(R.string.menu_btn_first_txt);

            DrawerButtonsState.FIRST_BTN = firstBtn.isPressed();
            DrawerButtonsState.SECOND_BTN =  secondBtn.isPressed();
            DrawerButtonsState.THIRD_BTN = thirdBtn.isPressed();
            DrawerButtonsState.FOURTH_BTN = fourthBtn.isPressed();
            DrawerButtonsState.FIFTH_BTN = fifthBtn.isPressed();
        }

        public void goToSecondFragment(View view) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WinnersFragment()).commit();
            Toast.makeText(HomeActivity.this, R.string.menu_btn_second_txt, Toast.LENGTH_LONG).show();
            drawer.closeDrawer(GravityCompat.START);

            firstBtn.setPressed(false);
            secondBtn.setPressed(true);
            thirdBtn.setPressed(false);
            fourthBtn.setPressed(false);
            fifthBtn.setPressed(false);

            toolbar.setTitle(R.string.menu_btn_second_txt);

            DrawerButtonsState.FIRST_BTN = fifthBtn.isPressed();
            DrawerButtonsState.SECOND_BTN =  secondBtn.isPressed();
            DrawerButtonsState.THIRD_BTN = thirdBtn.isPressed();
            DrawerButtonsState.FOURTH_BTN = fourthBtn.isPressed();
            DrawerButtonsState.FIFTH_BTN = fifthBtn.isPressed();

        }

        public void goToThirdFragment(View view) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ParticipantsFragment()).commit();
            Toast.makeText(HomeActivity.this, R.string.menu_btn_third_txt, Toast.LENGTH_LONG).show();
            drawer.closeDrawer(GravityCompat.START);

            firstBtn.setPressed(false);
            secondBtn.setPressed(false);
            thirdBtn.setPressed(true);
            fourthBtn.setPressed(false);
            fifthBtn.setPressed(false);

            toolbar.setTitle(R.string.menu_btn_third_txt);

            DrawerButtonsState.FIRST_BTN = fifthBtn.isPressed();
            DrawerButtonsState.SECOND_BTN =  secondBtn.isPressed();
            DrawerButtonsState.THIRD_BTN = thirdBtn.isPressed();
            DrawerButtonsState.FOURTH_BTN = fourthBtn.isPressed();
            DrawerButtonsState.FIFTH_BTN = fifthBtn.isPressed();

        }


        public void goToFourthFragment(View view) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContestsFragment()).commit();
            Toast.makeText(HomeActivity.this, R.string.menu_btn_fourth_txt, Toast.LENGTH_LONG).show();
            drawer.closeDrawer(GravityCompat.START);

            firstBtn.setPressed(false);
            secondBtn.setPressed(false);
            thirdBtn.setPressed(false);
            fourthBtn.setPressed(true);
            fifthBtn.setPressed(false);

            toolbar.setTitle(R.string.menu_btn_fourth_txt);

            DrawerButtonsState.FIRST_BTN = fifthBtn.isPressed();
            DrawerButtonsState.SECOND_BTN =  secondBtn.isPressed();
            DrawerButtonsState.THIRD_BTN = thirdBtn.isPressed();
            DrawerButtonsState.FOURTH_BTN = fourthBtn.isPressed();
            DrawerButtonsState.FIFTH_BTN = fifthBtn.isPressed();

        }
    public void goToFifthFragment(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WithdrawalRequestsFragment()).commit();
        Toast.makeText(HomeActivity.this, R.string.menu_btn_fifth_txt, Toast.LENGTH_LONG).show();
        drawer.closeDrawer(GravityCompat.START);

        firstBtn.setPressed(false);
        secondBtn.setPressed(false);
        thirdBtn.setPressed(false);
        fourthBtn.setPressed(false);
        fifthBtn.setPressed(true);

        toolbar.setTitle(R.string.menu_btn_fifth_txt);

        DrawerButtonsState.FIRST_BTN = firstBtn.isPressed();
        DrawerButtonsState.SECOND_BTN =  secondBtn.isPressed();
        DrawerButtonsState.THIRD_BTN = thirdBtn.isPressed();
        DrawerButtonsState.FOURTH_BTN = fourthBtn.isPressed();
        DrawerButtonsState.FIFTH_BTN = fifthBtn.isPressed();

    }

    }