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
import com.boha.sapslibrary.adapters.IncidentAdapter;
import com.boha.vodacom.dto.PanicIncidentDTO;
import com.boha.vodacom.dto.ResponseDTO;

import java.util.List;

public class IncidentListFragment extends Fragment implements PageFragment {
    private IncidentAdapter.IncidentListener mListener;

    List<PanicIncidentDTO> incidents;
    RecyclerView recyclerView;
    IncidentAdapter adapter;
    View view;

    public IncidentListFragment() {
    }

    public static IncidentListFragment newInstance(List<PanicIncidentDTO> list) {
        IncidentListFragment fragment = new IncidentListFragment();
        Bundle args = new Bundle();
        ResponseDTO w = new ResponseDTO();
        w.setIncidents(list);
        args.putSerializable("list", w);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ResponseDTO w = (ResponseDTO) getArguments().getSerializable("list");
            incidents = w.getIncidents();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_incident_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        setList();
        return view;
    }

    private void setList() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        adapter = new IncidentAdapter(incidents, new IncidentAdapter.IncidentListener() {
            @Override
            public void onDirections(PanicIncidentDTO incident) {
                mListener.onDirections(incident);
            }

            @Override
            public void onCamera(PanicIncidentDTO incident) {
                mListener.onCamera(incident);
            }

            @Override
            public void onChat(PanicIncidentDTO incident) {
                mListener.onChat(incident);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IncidentAdapter.IncidentListener) {
            mListener = (IncidentAdapter.IncidentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IncidentAdapter.IncidentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
