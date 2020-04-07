package br.com.cpmh.ata.analysis;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.cpmh.ata.R;

/**
 *
 */
public class MorphologicalAnalysisListFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener
{
	private static final String TAG = "MorphologicalALF";

	private FirebaseFirestore firestore;

	private Patient patient;

	private List<MorphologicalAnalysis> list;

	private MorphologicalAnalysisListAdapter morphologicalAnalysisListAdapter;

	private RecyclerView recyclerView;

	private SearchView searchView;

	private ProgressBar progressBar;

	private DrawerLayout drawerLayout;

	private ImageButton filterButton;

	/**
	 *
	 */
	public MorphologicalAnalysisListFragment()
	{

		list = new ArrayList<>();
		morphologicalAnalysisListAdapter = new MorphologicalAnalysisListAdapter(list);
	}

	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getArguments() != null)
		{
			patient = (Patient) getArguments().getParcelable(getString(R.string.bundle_parcelable_patient_key));
		}
	}

	/**
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 *
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_morphological_analysis_list, container, false);
	}

	/**
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		firestore = FirebaseFirestore.getInstance();

		searchView = view.findViewById(R.id.search_view);

		recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(morphologicalAnalysisListAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		progressBar = view.findViewById(R.id.progress_bar);

		drawerLayout = view.findViewById(R.id.drawer_layout);

		filterButton = view.findViewById(R.id.filter_button);
		filterButton.setOnClickListener(this);

		retrieveMorphologicalAnalysisList();
	}

	/**
	 *
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.filter_button:

				displayFilterOptions(true);
				break;
		}
	}

	/**
	 * @param query
	 *
	 * @return
	 */
	@Override
	public boolean onQueryTextSubmit(String query)
	{
		return false;
	}

	/**
	 * @param newText
	 *
	 * @return
	 */
	@Override
	public boolean onQueryTextChange(String newText)
	{
		return false;
	}

	/**
	 * @param show
	 */
	private void displayProgressBar(boolean show)
	{
		progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	/**
	 *
	 */
	protected void displayFilterOptions(boolean value)
	{
		if (value)
		{
			drawerLayout.openDrawer(Gravity.END);
		}
		else
		{
			drawerLayout.closeDrawer(Gravity.END);
		}
	}

	/**
	 *
	 */
	protected void displayMorphologicalAnalysisViewerActivity(Patient patient ,MorphologicalAnalysis morphologicalAnalysis)
	{
		Intent intent = new Intent(getActivity(),MorphologicalAnalysisViewerActivity.class);

		intent.putExtra(getString(R.string.bundle_parcelable_patient_key),(Parcelable) patient);

		intent.putExtra(getString(R.string.bundle_parcelable_morphological_analyses_key),(Parcelable) morphologicalAnalysis);
		startActivity(intent);
	}

	/**
	 *
	 */
	public void retrieveMorphologicalAnalysisList()
	{
		firestore.collection("patients").document(patient.getUid()).collection("morphologicalAnalyses").limit(10).addSnapshotListener(new AnalysisListFetchListener());
	}

	/**
	 *
	 */
	public class MorphologicalAnalysisListAdapter extends RecyclerView.Adapter<MorphologicalAnalysisListAdapter.ViewHolder> implements Filterable
	{
		private List<MorphologicalAnalysis> list;
		private List<MorphologicalAnalysis> filteredList;

		/**
		 * @param list
		 */
		public MorphologicalAnalysisListAdapter(List<MorphologicalAnalysis> list)
		{
			this.list = list;
			this.filteredList = list;
		}

		/**
		 * @param viewGroup
		 * @param index
		 *
		 * @return
		 */
		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index)
		{
			View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_morphological_analysis, viewGroup, false);

			return new MorphologicalAnalysisListAdapter.ViewHolder(view);
		}

		/**
		 * @param viewHolder
		 * @param index
		 */
		@Override
		public void onBindViewHolder(@NonNull MorphologicalAnalysisListAdapter.ViewHolder viewHolder, int index)
		{
			viewHolder.selectedMorphologicalAnalyses = list.get(index);
			viewHolder.patient = getArguments().getParcelable(getString(R.string.bundle_parcelable_patient_key));
		}

		/**
		 * @return
		 */
		@Override
		public int getItemCount()
		{
			if (filteredList == null)
			{
				return 0;
			}

			return filteredList.size();
		}

		/**
		 * @return
		 */
		@Override
		public Filter getFilter()
		{
			return null;
		}

		/**
		 *
		 */
		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
		{
			private MorphologicalAnalysis selectedMorphologicalAnalyses;
			private Patient patient;
			/**
			 * @param itemView
			 */
			public ViewHolder(@NonNull View itemView)
			{
				super(itemView);

				itemView.setOnClickListener(this);
			}

			/**
			 * @param view
			 */
			@Override
			public void onClick(View view)
			{
				displayMorphologicalAnalysisViewerActivity(patient ,selectedMorphologicalAnalyses);
			}
		}

		/**
		 *
		 */
		public class ListFilter extends Filter
		{
			/**
			 * @param constraint
			 *
			 * @return
			 */
			@Override
			protected FilterResults performFiltering(CharSequence constraint)
			{
				return null;
			}

			/**
			 * @param constraint
			 * @param results
			 */
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results)
			{
				notifyDataSetChanged();
			}
		}
	}

	protected class AnalysisListFetchListener implements EventListener<QuerySnapshot>
	{

		/**
		 * @param snapshot
		 * @param exception
		 */
		@Override
		public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException exception)
		{
			if (exception != null)
			{
				Log.w(TAG, exception.getMessage());
			}
			else
			{
				for (QueryDocumentSnapshot document : snapshot)
				{
					list.add(document.toObject(MorphologicalAnalysis.class));
				}

				displayProgressBar(false);
				morphologicalAnalysisListAdapter.notifyDataSetChanged();
			}
		}
	}
}
