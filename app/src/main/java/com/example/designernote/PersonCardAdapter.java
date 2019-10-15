package com.example.designernote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonCardAdapter extends RecyclerView.Adapter<PersonCardAdapter.PersonViewHolder>{
    private List<Person> personItemList;
    Context context;

    public PersonCardAdapter(List<Person> personItemList, Context context) {
        this.personItemList = personItemList;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View personView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_card, parent, false);
        PersonViewHolder gvh = new PersonViewHolder(personView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position) {
        holder.personImage.setImageResource(personItemList.get(position).getPersonImage());
        holder.customerName.setText(personItemList.get(position).getCustomerName());
        holder.totalPrice.setText(personItemList.get(position).getDebtPrice());
        holder.debtPrice.setText(personItemList.get(position).getTotalPrice());
        holder.workQty.setText(personItemList.get(position).getWorkQty());

        holder.personImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = personItemList.get(position).getCustomerName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return personItemList.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        ImageView personImage;
        TextView customerName;
        TextView totalPrice;
        TextView debtPrice;
        TextView workQty;
        public PersonViewHolder(View view) {
            super(view);
            personImage =view.findViewById(R.id.idPersonImage);
            customerName =view.findViewById(R.id.idPersonName);
            totalPrice = view.findViewById(R.id.idTotalPrice);
            debtPrice = view.findViewById(R.id.idDebtPrice);
            workQty = view.findViewById(R.id.idWorkQty);
        }
    }
}