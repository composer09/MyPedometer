package kr.co.composer.pedometer.activity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.activity.fragment.ConfigFragment;
import kr.co.composer.pedometer.activity.fragment.MainFragment;
import kr.co.composer.pedometer.location.layout.HistoryFragment;
import kr.co.composer.pedometer.location.layout.LocationFragment;

/**
 * Created by composer on 2015-06-13.
 */
public class MainActivity extends ActionBarActivity implements OnItemClickListener {
    private final static String MAIN_FRAGMENT = "mainfragment";
    private final static String HISTORY_FRAGMENT = "historyFragment";
    private final static String SERVICE_NAME = "kr.co.composer.pedometer.service.StepService";
    private final static String ALERT_MESSAGE = "만보기가 실행중입니다. 실행 상태로 나가시겠습니까?";

    private FragmentManager fManager;
    private ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LayoutInflater inflater;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTitle = getString(R.string.main_fragment);
        fManager = getSupportFragmentManager();
        FragmentTransaction ftx = fManager.beginTransaction();
        if (ftx.isEmpty()) {
            ftx.add(R.id.container, new MainFragment(), MAIN_FRAGMENT).addToBackStack(null).commit();
        }
        moveDrawerToTop();
        initActionBar();
        initDrawer();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mDrawerLayout.closeDrawer(mDrawerList);
//		fragment = fManager.findFragmentById(R.id.container);
        FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
        if (position == 0) {
            if (isServiceRunning(SERVICE_NAME)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mTitle = getString(R.string.main_fragment);
                restoreActionBar();
                fragtrans.replace(R.id.container, new MainFragment(), MAIN_FRAGMENT);
            }
        } else if (position == 1) {
            if (isServiceRunning(SERVICE_NAME)) {
                runningAlert();
            } else {
                fragtrans.replace(R.id.container, new LocationFragment(), "googleMap");
            }
        } else if (position == 2) {
            if (isServiceRunning(SERVICE_NAME)) {
                runningAlert();
            } else {
                mTitle = getString(R.string.history_fragment);
                restoreActionBar();
                fragtrans.replace(R.id.container, new HistoryFragment(), "HISTORY_FRAGMENT");
            }
        } else if (position == 3) {
            if (isServiceRunning(SERVICE_NAME)) {
                runningAlert();
            } else {
                mTitle = getString(R.string.config_fragment);
                restoreActionBar();
                fragtrans.replace(R.id.container, new ConfigFragment());
            }
        }
        fragtrans.commit();
    }

    private void moveDrawerToTop() {
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor,
                null); // "null" is important.

        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        LinearLayout container = (LinearLayout) drawer
                .findViewById(R.id.drawer_content); // This is the container we
        // defined just now.
        container.addView(child, 0);
        drawer.findViewById(R.id.drawer).setPadding(0, getStatusBarHeight(), 0,
                0);

        // Make the drawer replace the first child
        decor.addView(drawer);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getContentIdResource() {
        return getResources().getIdentifier("content", "id", "android");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        // actionBar = getSupportActionBar();
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);
        mDrawerLayout.setDrawerListener(createDrawerToggle());
        ListAdapter adapter = (ListAdapter) (new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.nav_items)));
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);
    }

    private DrawerListener createDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int state) {
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        return mDrawerToggle;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            if (isServiceRunning(SERVICE_NAME)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("종료 확인")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(ALERT_MESSAGE)
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                homeKey();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                finish();
            }
        }
    }


    private Boolean isServiceRunning(String serviceName) {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            Log.i("serviceName", runningServiceInfo.service.getClassName());
            if (serviceName.equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void runningAlert() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("확인")
                .setMessage("만보기가 실행중입니다.")
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void homeKey() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_FORWARD_RESULT
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
    }

}