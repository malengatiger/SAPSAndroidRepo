package com.boha.sapslibrary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boha.sapslibrary.R;
import com.boha.sapslibrary.adapters.PoliceStationAdapter;
import com.boha.vodacom.dto.PoliceStationDTO;
import com.boha.vodacom.dto.ResponseDTO;

import java.util.List;

public class PoliceStationListFragment extends Fragment implements PageFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PoliceStationListFragment() {
    }

    List<PoliceStationDTO> policeStations;
    View view;
    RecyclerView recyclerView;
    PoliceStationAdapter adapter;

    public static PoliceStationListFragment newInstance(List<PoliceStationDTO> list) {
        PoliceStationListFragment fragment = new PoliceStationListFragment();
        Bundle args = new Bundle();
        ResponseDTO w = new ResponseDTO();
        w.setPoliceStations(list);
        args.putSerializable("policeStations", w);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ResponseDTO w = (ResponseDTO) getArguments().getSerializable("policeStations");
            if (w != null) {
                policeStations = w.getPoliceStations();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_policestation_list, container, false);

        setFields();
        setList();
        return view;
    }

    private void setList() {
        adapter = new PoliceStationAdapter(policeStations, new PoliceStationAdapter.PoliceStationListener() {
            @Override
            public void onDirections(PoliceStationDTO station) {
                mListener.onDirections(station);
            }

            @Override
            public void onLove(PoliceStationDTO station) {
                mListener.onLove(station);
            }

            @Override
            public void onChat(PoliceStationDTO station) {
                mListener.onChat(station);
            }

            @Override
            public void onEdit(PoliceStationDTO station) {
                mListener.onEdit(station);
            }
        });
        LinearLayoutManager lm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }
    private void setFields() {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PoliceStationAdapter.PoliceStationListener) {
            mListener = (PoliceStationAdapter.PoliceStationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PoliceStationAdapter.IncidentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    PoliceStationAdapter.PoliceStationListener mListener;
}
