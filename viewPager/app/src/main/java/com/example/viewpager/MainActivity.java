package com.example.viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //Item margin 조정
        viewPager.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        //imageList선언
        // Fragment로 넘길 Image Resource
        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.dddd);
        listImage.add(R.drawable.f56c965dddc6eab12f98f86784782253);
        listImage.add(R.drawable.psh);
        listImage.add(R.drawable.ruda2);
        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가

        for (int i = 0; i < listImage.size(); i++) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            adapter.addItem(imageFragment);
        }
        adapter.notifyDataSetChanged();
    }

    class FragmentAdapter extends FragmentPagerAdapter{
        //ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();

        public FragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        void addItem(Fragment fragment){
            fragments.add(fragment);
        }
    }
}