package com.example.hp.musicapp;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hp on 12/20/2017.
 */

public class Pooja_Paath_frag extends Fragment {
//ListView listView;
//    public String [] festNames={"Ganapathy Puja", "Maha Lakhmi Puja", "Saraswathi Puja", "Durga Puja",
//            "Kali Puja",
//            "Nava graha Puja",
//            "Gayathri Puja"};
//    @RequiresApi(api = Build.VERSION_CODES.O)

    MainActivity mainActivity=new MainActivity();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    public Pooja_Paath_frag() {
        // Required empty public constructor

    }



    private void prepareListData() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pooja_paath_frag,container,false);

        String[] questions = {

                getString(R.string.ppath_ganpati_pooja),
                getString(R.string.ppath_laxmi_pooja),
                getString(R.string.ppath_saraswati_pooja),
                getString(R.string.ppath_durga_pooja),
                getString(R.string.ppath_kaali_pooja),
                getString(R.string.ppath_navgrah_pooja),
                getString(R.string.ppath_gayatri_pooja),
                getString(R.string.ppath_holi_pooja),
                getString(R.string.ppath_diwali_pooja),
                getString(R.string.ppath_navratre_pooja)
        };

        String[] answers = {

                getString(R.string.ans_ganpati_pooja),
                getString(R.string.ans_laxmi_pooja),
                getString(R.string.ans_saraswati_pooja),
                getString(R.string.ans_durga_pooja),
                getString(R.string.ans_kaali_pooja),
                getString(R.string.ans_navgrah_pooja),
                getString(R.string.ans_gayatri_pooja),
                getString(R.string.ans_holi_pooja),
                getString(R.string.ans_diwali_pooja),
                getString(R.string.ans_navratre_pooja),
        };


        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (String que : questions) {
            // Adding child data
            listDataHeader.add(que);

        }
//        listDataHeader.add("What research can you do before an interview?");
//        listDataHeader.add("Coming Soon..");
        for (int i = 0; i < answers.length; i++) {
            // Adding child data
            List<String> ansss = new ArrayList<String>();
            ansss.add(answers[i]);
            listDataChild.put(listDataHeader.get(i), ansss); // Header, Child data

        }

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.poojapaath_childitem, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.child_itemview);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.pooja_paath_item, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.poojaname);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

  //  MainActivityDrawer activity;

//    public void setActivity(MainActivityDrawer activity) {
//        this.activity = activity;
//    }

}
