package com.example.knowyourself;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscAdapter extends RecyclerView.Adapter<DiscAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DISC> disc;
    //ArrayList<String> result = new ArrayList<String>();
    //Result r = new Result();
   // Object[] results = new Object[100];
    Map<Integer, String> map = new HashMap<Integer, String>();
    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

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
        String que = "Q" + post + ". Please select.";
        holder.textViewQues.setText(que);
        holder.rBsel1.setText(disc.get(position).getSel1());
        holder.rBsel2.setText(disc.get(position).getSel2());
        holder.rBsel3.setText(disc.get(position).getSel3());
        holder.rBsel4.setText(disc.get(position).getSel4());

        //final String hi = mPreferences.getString(spFileName,"sel1");
        holder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton:
                        String type = disc.get(position).getType1();
                        //Toast.makeText(mContext,type, Toast.LENGTH_SHORT).show();
                        //result.set(position,type);
                        //results[position] = type;
                        //r.setType(type);
                        map.put(position,type);
                        //Toast.makeText(mContext,hi, Toast.LENGTH_SHORT).show();
                        String print =  String.valueOf(map.values());
                        Toast.makeText(mContext,print,Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton2:
                        String type2 = disc.get(position).getType2();
                        map.put(position,type2);
                        String print2 =  String.valueOf(map.values());
                        Toast.makeText(mContext,print2,Toast.LENGTH_SHORT).show();
                        //Toast.makeText(mContext,type2, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(mContext,map.get(position), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton3:
                        String type3 = disc.get(position).getType3();
                        map.put(position,type3);
                        String print3 =  String.valueOf(map.values());
                        Toast.makeText(mContext,print3,Toast.LENGTH_SHORT).show();
                        //Toast.makeText(mContext,type3, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(mContext,map.get(position), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton4:
                        String type4 = disc.get(position).getType4();
                        map.put(position,type4);
                        String print4 =  String.valueOf(map.values());
                        Toast.makeText(mContext,print4,Toast.LENGTH_SHORT).show();
                        //Toast.makeText(mContext,type4, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(mContext,map.get(position), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext,"Default", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return disc.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQues;
        RadioButton rBsel1,rBsel2,rBsel3,rBsel4;
        RadioGroup mRadioGroup;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewQues = (TextView) itemView.findViewById(R.id.textViewQue);
            rBsel1 = (RadioButton) itemView.findViewById(R.id.radioButton);
            rBsel2 = (RadioButton) itemView.findViewById(R.id.radioButton2);
            rBsel3 = (RadioButton) itemView.findViewById(R.id.radioButton3);
            rBsel4 = (RadioButton) itemView.findViewById(R.id.radioButton4);
            mRadioGroup = (RadioGroup) itemView.findViewById(R.id.radioGrp);

        }
    }
}
