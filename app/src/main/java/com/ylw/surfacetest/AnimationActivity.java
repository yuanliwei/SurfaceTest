package com.ylw.surfacetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AnimationActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startAnim();
            }

        });

        view = findViewById(R.id.view_obj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "alpha(0f)");
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "alpha(1f)");
        menu.add(Menu.NONE, Menu.FIRST + 3, 3, "x(300)");
        menu.add(Menu.NONE, Menu.FIRST + 4, 4, "x(0)");
        menu.add(Menu.NONE, Menu.FIRST + 5, 5, "y(800)");
        menu.add(Menu.NONE, Menu.FIRST + 6, 6, "y(0)");
        menu.add(Menu.NONE, Menu.FIRST + 7, 7, "x(400).y(800).alpha(0.2f)");
        menu.add(Menu.NONE, Menu.FIRST + 8, 8, "x(0).y(0).alpha(1)");
        menu.add(Menu.NONE, Menu.FIRST + 9, 9, "rotation(720)");
        menu.add(Menu.NONE, Menu.FIRST + 10, 10, "rotation(0)");
        return true;
    }

    int curOp = Menu.FIRST + 1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        curOp = item.getItemId();
        startAnim();
        return false;

    }


    private void startAnim() {
        switch (curOp) {
            case Menu.FIRST + 1:
                view.animate().alpha(0f).setDuration(1000);
                break;
            case Menu.FIRST + 2:
                view.animate().alpha(1f).setDuration(1000);
                break;
            case Menu.FIRST + 3:
                view.animate().x(500).setDuration(1000);
                break;
            case Menu.FIRST + 4:
                view.animate().x(0).setDuration(1000);
                break;
            case Menu.FIRST + 5:
                view.animate().y(800).setDuration(1000);
                break;
            case Menu.FIRST + 6:
                view.animate().y(0).setDuration(1000);
                break;
            case Menu.FIRST + 7:
                view.animate().x(500).y(800).alpha(0.2f).setDuration(1000);
                break;
            case Menu.FIRST + 8:
                view.animate().x(0).y(0).alpha(1).setDuration(1000);
                break;
            case Menu.FIRST + 9:
                view.animate().rotation(720).setDuration(1000).setDuration(1000);
                break;
            case Menu.FIRST + 10:
                view.animate().rotation(0).setDuration(1000);
                break;
        }
    }

}
