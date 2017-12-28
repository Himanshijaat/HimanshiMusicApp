package com.example.hp.musicapp;

import android.Manifest;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<Main_Bean> albumList;

    String lat = null;
    String lon = null;
    LocationListener loc;
    LocationManager locManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), albumList);


        prepareAlbums();
        getCurrentLoctaion();
        permission();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getCurrentLoctaion() {
        locManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        final Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        loc = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = String.valueOf(location.getLatitude());
                lon = String.valueOf(location.getLongitude());

                String city = null;
                String state = null;
                String country = null;
                String sublocality = null;
                String featureName=null;

                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses == null || addresses.size() == 0) {

                    Toast.makeText(getActivity(), "No address found", LENGTH_SHORT).show();

                } else {
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    sublocality = addresses.get(0).getSubLocality();
                    featureName=addresses.get(0).getFeatureName();

                    Toast.makeText(getActivity(), "Your location: "+featureName+", " + sublocality + ", " + city + ", " + state + "," + country, LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    private void permission() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET},10);
            }
            return;
        }
        locManager.requestLocationUpdates(locManager.NETWORK_PROVIDER,0,0,loc);
    }



    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.om1,
                R.drawable.om1,
                R.drawable.om1,
                R.drawable.om1,
                R.drawable.om1,
                R.drawable.om1,
        };
        Main_Bean a = new Main_Bean(getString(R.string.home_panchang), 13, covers[0]);
        albumList.add(a);

        a = new Main_Bean(getString(R.string.home_all_aartiyan), 8, covers[1]);
        albumList.add(a);

        a = new Main_Bean(getString(R.string.home_pooja_path), 11, covers[2]);
        albumList.add(a);

        a = new Main_Bean(getString(R.string.home_horoscope), 12, covers[3]);
        albumList.add(a);

        a = new Main_Bean(getString(R.string.home_festival_aartiyan), 14, covers[4]);
        albumList.add(a);

        a = new Main_Bean(getString(R.string.home_dainik_aartiyan), 1, covers[5]);
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
