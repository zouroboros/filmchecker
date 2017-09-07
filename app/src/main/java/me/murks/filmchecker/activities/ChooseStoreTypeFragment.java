package me.murks.filmchecker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.murks.filmchecker.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChooseStoreTypeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "choosestoretype";

    public ChooseStoreTypeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChooseStoreTypeFragment newInstance(int sectionNumber) {
        ChooseStoreTypeFragment fragment = new ChooseStoreTypeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.choose_store_type_fragment, container, false);
        Button dmButton = (Button) rootView.findViewById(R.id.dm_button);
        Button rmButton = (Button) rootView.findViewById(R.id.rm_button);
        dmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWizard().jumpToDm();
            }
        });

        rmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWizard().jumpToRossmann();
            }
        });
        return rootView;
    }

    private AddFilmWizardActivity getWizard() {
        return (AddFilmWizardActivity) getActivity();
    }
}
