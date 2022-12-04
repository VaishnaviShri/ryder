package com.example.ryderr.ui.main.driver.driverHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.ui.main.driver.driverHome.request_Driver.RequestDriverFragment;
import com.example.ryderr.ui.main.driver.driverHome.upcoming.UpcomingFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import de.hdodenhof.circleimageview.CircleImageView;

public class DriverFragment extends Fragment {

    private DriverViewModel mViewModel;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private UpcomingFragment upcomingFragment;
    private ArrayList<String> titles = new ArrayList<>();
    UpcomingAdapter upcomingAdapter;



    public static DriverFragment newInstance() {
        return new DriverFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_driver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(DriverViewModel.class);
        // TODO: Use the ViewModel

        CircleImageView driverAvatar = view.findViewById(R.id.driver_avatar);
        driverAvatar.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_driverFragment_to_driverProfileFragment);

        });
        titles.add("Upcoming Cabs");
        titles.add("Requests");
        tabLayout = getView().findViewById(R.id.driver_tab_layout);
        viewPager = getView().findViewById(R.id.driver_viewpager);
        upcomingAdapter = new UpcomingAdapter(getChildFragmentManager(), getLifecycle());

        upcomingAdapter.addFragment(new UpcomingFragment());
        upcomingAdapter.addFragment(new RequestDriverFragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(upcomingAdapter);
        new TabLayoutMediator(tabLayout, viewPager, this::onConfigureTab).attach();



    }


    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }

    public class UpcomingAdapter extends FragmentStateAdapter {

        private final int NUM_TABS = 2;
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public UpcomingAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }


        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }

}