package com.kkondratek.savingapp.logic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kkondratek.savingapp.fragments.GoalsPageFragm;
import com.kkondratek.savingapp.fragments.SavingsPageFragm;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final int CARD_ITEM_SIZE = 2;
    private Fragment savingsPageFragm;
    private Fragment goalsPageFragm;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        savingsPageFragm = SavingsPageFragm.newInstance();
        goalsPageFragm = GoalsPageFragm.newInstance();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return this.savingsPageFragm;
        } else {
            return this.goalsPageFragm;
        }
    }

    @Override
    public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}
