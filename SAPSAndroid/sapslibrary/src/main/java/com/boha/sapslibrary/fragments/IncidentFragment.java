package com.boha.sapslibrary.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.boha.sapslibrary.R;
import com.boha.sapslibrary.util.DataUtil;
import com.boha.sapslibrary.util.OKUtil;
import com.boha.sapslibrary.util.SharedUtil;
import com.boha.vodacom.dto.PanicIncidentDTO;
import com.boha.vodacom.dto.PanicTypeDTO;
import com.boha.vodacom.dto.ResponseDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentFragment extends Fragment {


    Button btnSend;
    RecyclerView recyclerView;
    ImageView iconCamera;
    Spinner spinner;
    View view;
    List<PanicTypeDTO> panicTypes;
    PanicTypeDTO panicType;
    Location location;

    public IncidentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_incident, container, false);
        setFields();
        return view;
    }


    PanicIncidentDTO incident;
    private void sendIncident() {
        if (panicType == null) {
            Snackbar.make(spinner, "Please select Incident Type", Snackbar.LENGTH_SHORT).show();
            return;
        }
        incident = new PanicIncidentDTO();
        incident.setPanicType(panicType);
        incident.setCitizenID(SharedUtil.getCitizen(getContext()).getCitizenID());
        incident.setDateSent(new Date().getTime());
        incident.setLatitude(location.getLatitude());
        incident.setLongitude(location.getLongitude());
        mListener.setBusy(true);
        DataUtil.sendIncident(getContext(), incident, new OKUtil.OKListener() {
            @Override
            public void onResponse(final ResponseDTO response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListener.setBusy(false);
                        incident = response.getIncidents().get(0);
                        showDialog();
                    }
                });
            }

            @Override
            public void onError(final String message) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListener.setBusy(false);
                        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        d.setTitle("Add Pictures")
                .setMessage("Do you want to add pictures to your report?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPictureRequired(incident);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void setFields() {
        spinner = (Spinner) view.findViewById(R.id.spinner);
        iconCamera = (ImageView) view.findViewById(R.id.iconCamera);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIncident();
            }
        });
        iconCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPictureRequired(incident);
            }
        });

        setSpinner();
    }

    private void setSpinner() {
        panicTypes = SharedUtil.getPanicTypes(getContext());
        List<String> list = new ArrayList<>();
        list.add("Please select Incident Type");
        for (PanicTypeDTO p : panicTypes) {
            list.add(p.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    panicType = null;
                } else {
                    panicType = panicTypes.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setLocation(Location location) {
        this.location = location;
        sendIncident();
    }

    public interface IncReportListener {
        void setBusy(boolean busy);

        void getLocation();

        void onPictureRequired(PanicIncidentDTO incident);

        void onFinish();
    }

    IncReportListener mListener;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        if (a instanceof IncReportListener) {
            mListener = (IncReportListener) a;
        } else {
            throw new RuntimeException(a.getLocalClassName() + " must implement IncReportListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }
}
