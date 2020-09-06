package com.messieyawo.medhack2020.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.messieyawo.medhack2020.Home.UpdateSlotFragment;
import com.messieyawo.medhack2020.Slots.EventFragment;
import com.messieyawo.medhack2020.Testimonials.TestimonialsFragment;
import com.messieyawo.medhack2020.fragments.Appointment;
import com.messieyawo.medhack2020.fragments.MedHackAppointMent;
import com.messieyawo.medhack2020.fragments.MedHackHome;


public class CustomFragmentPageAdapter extends FragmentPagerAdapter {

    private static final String TAG = CustomFragmentPageAdapter.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 3;

    public CustomFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MedHackHome();
            case 1:
                return new UpdateSlotFragment();
            case 2:
                return new EventFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Home";
            case 1:
                return "News";
            case 2:
                return "All Slots";
        }
        return null;
    }
}
