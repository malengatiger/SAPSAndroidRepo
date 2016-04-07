package com.boha.sapslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boha.sapslibrary.R;
import com.boha.vodacom.dto.PanicIncidentDTO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.ViewHolder> {

    private final List<PanicIncidentDTO> incidents;
    private final IncidentListener mListener;
    public interface IncidentListener {
        void onDirections(PanicIncidentDTO incident);
        void onCamera(PanicIncidentDTO incident);
        void onChat(PanicIncidentDTO incident);
    }

    public IncidentAdapter(List<PanicIncidentDTO> incidents, IncidentListener listener) {
        this.incidents = incidents;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder h, int position) {
        final PanicIncidentDTO p = incidents.get(position);
        h.txtType.setText(p.getPanicType().getName());
        h.txtDate.setText(sdf.format(new Date(p.getDateSent())));

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
        h.iconCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCamera(p);
            }
        });
        h.iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChat(p);
            }
        });

    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtType, txtDistance, txtDate;
        public final ImageView iconDirections, iconCamera,iconChat;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtType = (TextView) view.findViewById(R.id.PSN_txtName);
            txtDate = (TextView) view.findViewById(R.id.PSN_txtDate);
            txtDistance = (TextView) view.findViewById(R.id.PSN_txtDistance);
            iconCamera = (ImageView) view.findViewById(R.id.iconCamera);
            iconChat = (ImageView) view.findViewById(R.id.iconChat);
            iconDirections = (ImageView) view.findViewById(R.id.iconDirections);
        }


    }
    static final DecimalFormat df1 = new DecimalFormat("###,###,###,###");
    static final DecimalFormat df2 = new DecimalFormat("###,###,###,##0.00");
    static final SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm");
}
