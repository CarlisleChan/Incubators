package com.carlisle.incubators;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import com.carlisle.incubators.Animation.AnimationActivity;
import com.carlisle.incubators.Button.ButtonActivity;
import com.carlisle.incubators.EditText.EditTextActivity;
import com.carlisle.incubators.ImageView.ImageActivity;
import com.carlisle.incubators.PopupWindow.PopupWindowActivity;
import com.carlisle.incubators.Seekbar.SeekBarActivity;
import com.carlisle.incubators.SoftKeyBoard.SoftKeyBoardActivity;
import com.carlisle.incubators.Spannable.SpannableActivity;
import com.carlisle.incubators.TextView.TextViewActivity;
import com.carlisle.incubators.Toast.ToastUtils;
import com.carlisle.incubators.UpdateApp.UpdateActivity;
import com.carlisle.incubators.VertifyCodeParse.VerifyCodeActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onDatePickerClick(View view) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String theDate = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                ToastUtils.showToast(MainActivity.this, theDate);
            }
        }, 2016, 2, 30).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class c = null;

        if (id == R.id.nav_spannable) {
            c = SpannableActivity.class;
        } else if (id == R.id.nav_seekbar) {
            c = SeekBarActivity.class;
        } else if (id == R.id.nav_image_view) {
            c = ImageActivity.class;
        } else if (id == R.id.nav_gift) {
            c = AnimationActivity.class;
        } else if (id == R.id.nav_verify_code) {
            c = VerifyCodeActivity.class;
        } else if (id == R.id.nav_update) {
            c = UpdateActivity.class;
        } else if (id == R.id.nav_edit_text) {
            c = EditTextActivity.class;
        } else if (id == R.id.nav_button) {
            c = ButtonActivity.class;
        } else if (id == R.id.nav_text_view) {
            c = TextViewActivity.class;
        } else if (id == R.id.nav_soft_key_board) {
            c = SoftKeyBoardActivity.class;
        } else if (id == R.id.nav_popup_window) {
            c = PopupWindowActivity.class;
        }

        startActivity(new Intent(this, c));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
