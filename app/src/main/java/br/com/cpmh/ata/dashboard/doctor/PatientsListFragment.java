package br.com.cpmh.ata.dashboard.doctor;

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
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.analysis.Doctor;
import br.com.cpmh.ata.analysis.Patient;

/**
 *
 */
public class PatientsListFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener
{
	private static final String TAG = "PatientsListFragment";

	private FirebaseFirestore firestore;

	private Doctor doctor;

	private List<Patient> list;

	private PatientListAdapter patientListAdapter;

	private RecyclerView recyclerView;

	private SearchView searchView;

	private ProgressBar progressBar;

	private DrawerLayout drawerLayout;

	private ImageButton filterButton;

	/**
	 *
	 */
	public PatientsListFragment()
	{
		list = new ArrayList<>();
		patientListAdapter = new PatientListAdapter(list);
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
			doctor = (Doctor) getArguments().getParcelable(getString(R.string.bundle_parcelable_doctor_key));
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
		return inflater.inflate(R.layout.fragment_patients_list, container, false);
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
		recyclerView.setAdapter(patientListAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		progressBar = view.findViewById(R.id.progress_bar);

		drawerLayout = view.findViewById(R.id.drawer_layout);

		filterButton = view.findViewById(R.id.filter_button);
		filterButton.setOnClickListener(this);

		retrievePatientsList();
	}

	/**
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
	public void retrievePatientsList()
	{
		// Todo: refactor the firestore query.
		firestore.collection(getString(R.string.firestore_patients_collection_path_key)).whereArrayContains(getString(R.string.firestore_patients_collection_array_contains_filter_key), Objects.requireNonNull(doctor).getUid()).limit(10).addSnapshotListener(new PatientListFetchListener());
	}

	/**
	 *
	 */
	public void displayPatientProfileActivity(Patient patient)
	{
		Intent intent = new Intent(getContext(), PatientProfileActivity.class);
		if (patient != null)
		{
			intent.putExtra(getString(R.string.intent_parcelable_patient_key), (Parcelable) patient);

			Log.i("teste",patient.getUid());
		}
		getContext().startActivity(intent);
	}

	/**
	 *
	 */
	public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> implements Filterable
	{
		private List<Patient> list;
		private List<Patient> filteredList;

		/**
		 * @param list
		 */
		public PatientListAdapter(List<Patient> list)
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
			View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_patient, viewGroup, false);

			return new ViewHolder(view);
		}

		/**
		 * @param viewHolder
		 * @param index
		 */
		@Override
		public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index)
		{
			viewHolder.patient = list.get(index);

			viewHolder.patientName.setText(viewHolder.patient.getFullName());
			viewHolder.patientBirthday.setText(viewHolder.patient.getFullName());
			viewHolder.patientTaxpayerNumber.setText(viewHolder.patient.getTaxpayerNumber());
		}

		/**
		 * @return
		 */
		@Override
		public int getItemCount()
		{
			if (list == null)
			{
				return 0;
			}
			return list.size();
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
			private Patient patient;

			private TextView patientName;
			private TextView patientBirthday;
			private TextView patientTaxpayerNumber;

			/**
			 * @param itemView
			 */
			public ViewHolder(@NonNull View itemView)
			{
				super(itemView);

				patientName = itemView.findViewById(R.id.patient_name);
				patientBirthday = itemView.findViewById(R.id.patient_birthday);
				patientTaxpayerNumber = itemView.findViewById(R.id.patient_taxpayer_number);

				itemView.setOnClickListener(this);
			}

			/**
			 * @param view
			 */
			@Override
			public void onClick(View view)
			{
				displayPatientProfileActivity(patient);
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

	/**
	 *
	 */
	public class PatientListFetchListener implements EventListener<QuerySnapshot>
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
					list.add(document.toObject(Patient.class));
				}

				displayProgressBar(false);
				patientListAdapter.notifyDataSetChanged();
			}
		}

	}
}
