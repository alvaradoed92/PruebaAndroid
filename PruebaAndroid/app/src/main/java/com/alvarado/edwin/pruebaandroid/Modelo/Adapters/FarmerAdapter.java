package com.alvarado.edwin.pruebaandroid.Modelo.Adapters;

/**
 * Created by edwinalvarado on 01/06/17.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Farmer;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.LinkItem;
import com.alvarado.edwin.pruebaandroid.R;

import java.util.List;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.MyViewHolder> {

        private List<LinkItem> farmerList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView farmerId, item, phone,category;

            public MyViewHolder(View view) {
                super(view);
                farmerId = (TextView) view.findViewById(R.id.tvfarmerId);
                item = (TextView) view.findViewById(R.id.tvItem);
                phone = (TextView) view.findViewById(R.id.tvphone);
                category = (TextView)view.findViewById(R.id.tvCategory);
            }
        }


        public FarmerAdapter(List<LinkItem> farmerList) {
            this.farmerList = farmerList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.farmer_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            LinkItem farmer = farmerList.get(position);
            holder.farmerId.setText(String.valueOf(farmer.getFarmerId().getFarmerId()));
            holder.phone.setText(farmer.getFarmerId().getPhone1());
            holder.category.setText(farmer.getCategoryId().getCategoryId());
            holder.item.setText(farmer.getItemId().getItemId());
        }

        @Override
        public int getItemCount() {
            return farmerList.size();
        }




}
