package com.example.knowyourself;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFeedFragment extends Fragment {
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog mProgressDialog;

    private DatabaseReference mDatabaseReference;
    private ArrayList<MyFeed> mList;

    private MenuItem searchItem;
    private MyFeedAdapter mMyFeedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_myfeed, container, false);
        setHasOptionsMenu(true);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading MyFeed...");
        mProgressDialog.show();

        //Set Up recyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.myfeed_recycler_view);// use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("myFeed");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList = new ArrayList<MyFeed>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MyFeed f = new MyFeed();

                    String ts = (String) dataSnapshot1.getKey();
                    String feedTitle = (String) dataSnapshot1.child("title").getValue();
                    String feedContent = (String) dataSnapshot1.child("article").getValue();
                    String fileName = (String) dataSnapshot1.child("fileName").getValue();

                    f.setTimeStamp(ts);
                    f.setArticle(feedContent);
                    f.setTitle(feedTitle);
                    f.setFileName(fileName);

                    mList.add(f);
                }
                mMyFeedAdapter = new MyFeedAdapter(getContext(), mList);
                recyclerView.setAdapter(mMyFeedAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    //... menu option
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu,menu);
        final InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        searchItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                imm.showSoftInput(getActivity().getCurrentFocus(), 0);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                imm.hideSoftInputFromWindow((IBinder) getActivity().getParent(), 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMyFeedAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


}
