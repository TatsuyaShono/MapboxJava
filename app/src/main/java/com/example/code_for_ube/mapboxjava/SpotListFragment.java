package com.example.code_for_ube.mapboxjava;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SpotListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Realm mRealm;

    public SpotListFragment() {}

    public static SpotListFragment newInstance() {
        SpotListFragment fragment = new SpotListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_spot_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler);

        /**
         * LinearLayoutManagerクラスのコンストラクタ
         * 書式：LinearLayoutManager(Context context)
         * 引数：context 現在のコンテキスト
         */
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        /**
         * setOrientationメソッド
         * 機能：レイアウトマネージャのスクロール方向を指定します
         * 書式：void setOrientation(int orientation)
         * 引数：orientation   VERTICALを指定すると縦スクロール、HORIZONTALなら横スクロールになります
         */
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        /**
         * setLayoutManagerメソッド
         * 機能：RecyclerViewで使用するレイアウトマネージャを指定します
         * 書式：void setLayoutManager(LayoutManager layout)
         * 引数：layout  使用するレイアウトマネージャ
         */
        recyclerView.setLayoutManager(llm);

        RealmResults<Spot> spots = mRealm.where(Spot.class).findAll();
        SpotRealmAdapter adapter
                = new SpotRealmAdapter(getActivity(), spots, true);

        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onAddSpotSelected();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        /**
         * setHasOptionsMenuメソッド
         * 機能：フラグメントがメニューを持っているか設定します
         * 書式：void setHasOptionsMenu(boolean hasMenu)
         * 引数：hasMenu   trueならフラグメントはメニューを持っています
         */
        setHasOptionsMenu(true);
    }

    /**
     * onCreateOptionsMenuメソッド
     * 機能：オプションメニューを初期化します
     * 書式：void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
     * @param menu       ：Menuクラスのインスタンスが渡されます
     * @param inflater   ：MenuInflaterクラスのインスタンスが渡されます
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        /**
         * inflateメソッド
         * 機能：メニューXMLファイルからメニューを構築します
         * 書式：void inflate(int menuRes, Menu menu)
         * 引数：menuRes   メニューXMLファイルのリソースID
         *      menu      項目を追加する対象(onCreateOptionsMenuの引数として渡されたmenu)
         */
        inflater.inflate(R.menu.menu_spot_list, menu);
        MenuItem addSpot = menu.findItem(R.id.menu_item_add_spot);
        MenuItem deleteAll = menu.findItem(R.id.menu_item_delete_all);
        MyUtils.tintMenuIcon(getContext(), addSpot, android.R.color.white);
        MyUtils.tintMenuIcon(getContext(), deleteAll, android.R.color.white);
    }

    /**
     * onOptionsItemSelectedメソッド
     * 機能：オプションメニューが選択された時に呼ばれます
     * 書式：boolean onOptionsItemSelected(MenuItem item)
     * @param item  ：選択されたメニュー
     * @return      ：メニュー処理を続行するにはfalse、実行済みの場合はtrueを返します
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_spot:
                if(mListener != null) {
                    mListener.onAddSpotSelected();
                }
                return true;
            case R.id.menu_item_delete_all:
                final RealmResults<Spot> spots
                        = mRealm.where(Spot.class).findAll();
                mRealm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm) {
                        spots.deleteAllFromRealm();
                    }
                });
                return true;
        }
        return  false;
    }
}
