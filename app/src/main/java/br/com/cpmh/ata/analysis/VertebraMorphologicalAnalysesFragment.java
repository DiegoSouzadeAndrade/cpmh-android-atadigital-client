package br.com.cpmh.ata.analysis;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import javax.annotation.Nullable;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.analysis.spine.VertebraMorphologicalAnalysis;

/**
 * A simple {@link Fragment} subclass.
 */
public class VertebraMorphologicalAnalysesFragment extends DialogFragment implements EventListener<QuerySnapshot>
{
    private final String TAG = "VertebraMorphologicalAF";

    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    private Patient patient;
    private MorphologicalAnalysis morphologicalAnalysis;

    private ArrayList<VertebraMorphologicalAnalysis> vertebraMorphologicalAnalyses;

    private VertebraMorphologicalAnalysesAdapter vertebraMorphologicalAnalysesAdapter;
    private RecyclerView recyclerView;


    public VertebraMorphologicalAnalysesFragment() {
        // Required empty public constructor
    }
    public static VertebraMorphologicalAnalysesFragment newInstance(int index)
    {
        VertebraMorphologicalAnalysesFragment vertebraMorphologicalAnalysesFragment = new VertebraMorphologicalAnalysesFragment();

        Bundle args = new Bundle();
        args.putInt("sendSomething", index);
        vertebraMorphologicalAnalysesFragment.setArguments(args);

        return vertebraMorphologicalAnalysesFragment;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@android.support.annotation.Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null)
        {
            patient = (Patient) getArguments().getParcelable(getString(R.string.bundle_parcelable_patient_key));
            morphologicalAnalysis = (MorphologicalAnalysis) getArguments().getParcelable(getString(R.string.bundle_parcelable_morphological_analyses_key));

        }

        return inflater.inflate(R.layout.fragment_vertebra_morphological_analyses, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        //storageReference = firebaseStorage.getReference().child(

        vertebraMorphologicalAnalyses = new ArrayList<>();

        vertebraMorphologicalAnalysesAdapter = new VertebraMorphologicalAnalysesAdapter(vertebraMorphologicalAnalyses);

        recyclerView = view.findViewById(R.id.vertebra_morphological_analyses_recycler_view);
        recyclerView.setAdapter(vertebraMorphologicalAnalysesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        retrieveVertebraMorphologicalAnalyses();

    }

    private void retrieveVertebraMorphologicalAnalyses()
    {
        firebaseFirestore.collection("patients").document("CvYPHFPgHAghYWLiv3JgnrdeFhI2").collection("morphologicalAnalyses").document("Aemkrkq2L0Cm6TK9kRno").collection("vertebraMorphologicalAnalyses").addSnapshotListener(this);
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException exception)
    {
        if (exception != null)
        {
            Log.w("ExamSnapshotFail", "Listen failed.", exception);
            return;
        }
        else
        {
            for (QueryDocumentSnapshot documentSnapshot : snapshot)
            {
                Log.i(TAG, "-1 --->" + documentSnapshot.toString());
                vertebraMorphologicalAnalyses.add(documentSnapshot.toObject(VertebraMorphologicalAnalysis.class));

            }
            vertebraMorphologicalAnalysesAdapter.notifyDataSetChanged();
        }
    }
}
