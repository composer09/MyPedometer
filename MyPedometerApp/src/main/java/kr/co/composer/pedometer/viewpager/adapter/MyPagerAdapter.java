package kr.co.composer.pedometer.viewpager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.composer.pedometer.viewpager.page.Page1Activity;
import kr.co.composer.pedometer.viewpager.page.Page2Activity;
import kr.co.composer.pedometer.viewpager.page.Page3Activity;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
	private LayoutInflater layoutInflater;
	private FragmentManager fragManager;
	private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
	private static final int PAGE_COUNT = 3;
	
	public MyPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.fragManager = fm;
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new Page1Activity();
		case 1:
			return new Page2Activity();
		case 2:
			return new Page3Activity();
		default:
			return null;
		}
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	public long getItemId(int position) {
        return position;
    }
    
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager)collection).removeView((View)view);
    }

    
    @Override
    public float getPageWidth(int position) {
        return 0.8f;
    }

}
