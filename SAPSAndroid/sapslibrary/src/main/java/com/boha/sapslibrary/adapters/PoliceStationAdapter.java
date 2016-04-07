package com.boha.sapslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boha.sapslibrary.R;
import com.boha.vodacom.dto.PoliceStationDTO;

import java.text.DecimalFormat;
import java.util.List;

public class PoliceStationAdapter extends RecyclerView.Adapter<PoliceStationAdapter.ViewHolder> {

    private final List<PoliceStationDTO> policeStations;
    private final PoliceStationListener mListener;
    public interface PoliceStationListener {
        void onDirections(PoliceStationDTO station);
        void onLove(PoliceStationDTO station);
        void onChat(PoliceStationDTO station);
        void onEdit(PoliceStationDTO station);
    }

    public PoliceStationAdapter(List<PoliceStationDTO> policeStations, PoliceStationListener listener) {
        this.policeStations = policeStations;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.station_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder h, int position) {
        final PoliceStationDTO p = policeStations.get(position);
        h.txtName.setText(p.getName());

        h.txtIncidents.setText(df1.format(p.getPanicIncidentList().size()));
        h.txtOfficers.setText(df1.format(p.getOfficerList().size()));
        if (p.getDistance() != null) {
            h.txtDistance.setText(df2.format(p.getDistance()));
            h.txtDistance.setVisibility(View.VISIBLE);
        } else {
            h.txtDistance.setVisibility(View.GONE);
        }
        h.iconDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDirections(p);
            }
        });
        h.iconLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLove(p);
            }
        });
        h.iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChat(p);
            }
        });
        h.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEdit(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return policeStations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtName, txtDistance, txtOfficers,txtIncidents;
        public final ImageView iconDirections, iconLove,iconEdit,iconChat;
        public PoliceStationDTO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtOfficers = (TextView) view.findViewById(R.id.PSN_txtOfficerCount);
            txtIncidents = (TextView) view.findViewById(R.id.PSN_txtCountIncidents);
            txtDistance = (TextView) view.findViewById(R.id.PSN_txtDistance);
            txtName = (TextView) view.findViewById(R.id.PSN_txtName);
            iconLove = (ImageView) view.findViewById(R.id.iconLove);
            iconChat = (ImageView) view.findViewById(R.id.iconChat);
            iconDirections = (ImageView) view.findViewById(R.id.iconDirections);
            iconEdit = (ImageView) view.findViewById(R.id.iconEdit);
        }


    }
    static final DecimalFormat df1 = new DecimalFormat("###,###,###,###");
    static final DecimalFormat df2 = new DecimalFormat("###,###,###,##0.00");
}
