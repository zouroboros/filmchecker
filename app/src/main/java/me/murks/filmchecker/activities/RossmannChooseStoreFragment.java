package me.murks.filmchecker.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import me.murks.filmchecker.R;
import me.murks.filmchecker.background.RmStoreQueryTask;
import me.murks.filmchecker.model.RossmannStoreLink;

/**
 * Fragment to choose the rm store
 * @date 9/19/2017
 * @author zouroboros
 */
public class RossmannChooseStoreFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "filmdetails";
    private AddFilmWizardActivity parent;


    public RossmannChooseStoreFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RossmannChooseStoreFragment newInstance(int sectionNumber) {
        RossmannChooseStoreFragment fragment = new RossmannChooseStoreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        parent = (AddFilmWizardActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rossmann_choose_store_fragment, container, false);
        EditText plzInput = (EditText) rootView.findViewById(R.id.plz_input);
        ListView storeList = (ListView) rootView.findViewById(R.id.store_list);
        storeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RossmannStoreLink link = (RossmannStoreLink) adapterView.getAdapter().getItem(i);
                new RmStoreQueryTask(parent).execute(link.storeUrl);
            }
        });

        final ArrayAdapter<RossmannStoreLink> storeAdapter = new ArrayAdapter<>(getContext(), R.layout.store_list_item, R.id.store_list_item_text);
        storeList.setAdapter(storeAdapter);

        plzInput.addTextChangedListener(new RmStoreListTextWatcher(storeAdapter));

        return rootView;
    }

}
