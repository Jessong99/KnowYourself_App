package com.example.knowyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiscAdapter extends RecyclerView.Adapter<DiscAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DISC> disc;
    ArrayList<String> result = new ArrayList<String>();
    Object[] results = new Object[100];

    public DiscAdapter(Context c, ArrayList<DISC> list) {
        mContext = c;
        disc = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        int post = position+1;
        String i = "Q" + post + ". Please select.";
        holder.textViewQues.setText(i);
        holder.rBsel1.setText(disc.get(position).getSel1());
        holder.rBsel2.setText(disc.get(position).getSel2());
        holder.rBsel3.setText(disc.get(position).getSel3());
        holder.rBsel4.setText(disc.get(position).getSel4());
        while (result.size() < position)
        {
            result.add(null);
        }
        holder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton:
                        String type = disc.get(position).getType1();
                        Toast.makeText(mContext,type, Toast.LENGTH_SHORT).show();
                        result.set(position,type);
                        results[position] = type;
                        break;
                    case R.id.radioButton2:
                        Toast.makeText(mContext,disc.get(position).getType2(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton3:
                        Toast.makeText(mContext,disc.get(position).getType3(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton4:
                        Toast.makeText(mContext,disc.get(position).getType4(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext,"Default", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        result.get(result.indexOf(0));
        holder.m.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "SubmitBtn", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return disc.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQues;
        RadioButton rBsel1,rBsel2,rBsel3,rBsel4;
        RadioGroup mRadioGroup;
        Button m;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewQues = (TextView) itemView.findViewById(R.id.textViewQue);
            rBsel1 = (RadioButton) itemView.findViewById(R.id.radioButton);
            rBsel2 = (RadioButton) itemView.findViewById(R.id.radioButton2);
            rBsel3 = (RadioButton) itemView.findViewById(R.id.radioButton3);
            rBsel4 = (RadioButton) itemView.findViewById(R.id.radioButton4);
            mRadioGroup = (RadioGroup) itemView.findViewById(R.id.radioGrp);
            m = (Button) itemView.findViewById(R.id.btnSubmitTest);

        }
    }
}
