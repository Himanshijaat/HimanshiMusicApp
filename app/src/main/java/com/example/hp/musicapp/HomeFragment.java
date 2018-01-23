package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<Main_Bean> albumList;
    android.support.v7.widget.Toolbar toolbar;
    Button btn_refresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.homefragment,container,false);
        toolbar=(android.support.v7.widget.Toolbar)getActivity().findViewById(R.id.toolbar);
        btn_refresh=(Button)toolbar.findViewById(R.id.btn_refresh);
        btn_refresh.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), albumList);

        prepareAlbums();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.om1,
                R.drawable.om1,
                R.drawable.om1,
                R.drawable.om1};
        String [] trackId=new String[]{""};

        Main_Bean    a = new Main_Bean(getString(R.string.home_festival_aartiyan), 13,covers[0],trackId[0]);
        albumList.add(a);
        a = new Main_Bean(getString(R.string.home_all_aartiyan), 8, covers[1],trackId[0]);
        albumList.add(a);

        a = new Main_Bean(getString(R.string.home_pooja_path), 11, covers[2],trackId[0]);
        albumList.add(a);

        a = new Main_Bean(getString(R.string.home_panchang), 14, covers[3],trackId[0]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
