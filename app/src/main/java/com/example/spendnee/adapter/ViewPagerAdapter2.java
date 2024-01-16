package com.example.spendnee.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.spendnee.fragment.CreatorFragment;
import com.example.spendnee.fragment.MemberFragment;

public class ViewPagerAdapter2 extends FragmentStatePagerAdapter {
    public ViewPagerAdapter2(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CreatorFragment();
            case 1:
                return new MemberFragment();
            default:
                return new CreatorFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Creator";
                break;
            case 1:
                title = "Member";
                break;
        }
        return title;
    }
}
