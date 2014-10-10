package com.mgoficina;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {

    private final int PAGES = 3;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                
                return new TrabalhosFragment();
            case 1:
            	return new DadosFragment();
            case 2:
            	return new LotesFragment();
          //  case 3:
                
          //      return new EntradaFragment();
                
            default:
                throw new IllegalArgumentException("The item position should be less or equal to:" + PAGES);
        }
    }

    @Override
    public int getCount() {
        return PAGES;
    }
}
