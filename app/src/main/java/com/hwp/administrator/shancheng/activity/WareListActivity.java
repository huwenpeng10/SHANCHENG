package com.hwp.administrator.shancheng.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hwp.administrator.shancheng.R;
import com.hwp.administrator.shancheng.entity.Tab;
import com.hwp.administrator.shancheng.fragment.CartFragment;
import com.hwp.administrator.shancheng.fragment.CategoryFragment;
import com.hwp.administrator.shancheng.fragment.HomeFragment;
import com.hwp.administrator.shancheng.fragment.HotFragment;
import com.hwp.administrator.shancheng.fragment.MineFragment;
import com.hwp.administrator.shancheng.wight.FragmentTabHost;
import com.hwp.administrator.shancheng.wight.ShopToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WareListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    ShopToolbar shopToolbar;
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabhost;
    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList<>(5);
    public CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_test);
        mInflater = LayoutInflater.from(this);
        ButterKnife.bind(this);
        initTab();
    }

    private void initTab() {
        Tab tab_home = new Tab(HomeFragment.class, R.drawable.selector_icon_home, R.string.home);
        Tab tab_hot = new Tab(HotFragment.class, R.drawable.selector_icon_hot, R.string.hot);
        Tab tab_category = new Tab(CategoryFragment.class, R.drawable.selector_icon_category, R.string.category);
        Tab tab_cart = new Tab(CartFragment.class, R.drawable.selector_icon_cart, R.string.cart);
        Tab tab_mine = new Tab(MineFragment.class, R.drawable.selector_icon_mine, R.string.mine);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                                             @Override
                                             public void onTabChanged(String tabId) {
                                                 if (tabId.equals(getString(R.string.cart))) {
                                                     refData();
                                                 } else {
                                                     shopToolbar.showSearchView();
                                                     shopToolbar.hideTitleView();
                                                 }
                                             }
                                         }

        );
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        mTabhost.setCurrentTab(0);
    }

    private void refData() {
        if (cartFragment == null) {
            Fragment fragment = getSupportFragmentManager().
                    findFragmentByTag(getString(R.string.cart));

            if (fragment != null) {
                cartFragment = (CartFragment) fragment;
                Log.e("TAG", "cartFragment-------" + cartFragment);
                cartFragment.refData();
                cartFragment.changeToolBar();
            }
        } else {
            Log.e("TAG", "666666666666666" + cartFragment);
            cartFragment.refData();
            cartFragment.changeToolBar();
        }
    }


    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setImageResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }
}
