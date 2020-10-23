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

import static android.content.Context.MODE_PRIVATE;

public class DiscAdapter extends RecyclerView.Adapter<DiscAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DISC> disc;

    //Shared Preferences
    SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;
    String saved;


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

        //initialize shared preferences
        mPreferences = this.mContext.getSharedPreferences(spFileName, MODE_PRIVATE);
        //initialization of editor
        final SharedPreferences.Editor spEditor = mPreferences.edit();

        holder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String type="";
                switch (i) {
                    case R.id.radioButton:
                        type = disc.get(position).getType1();
                        break;
                    case R.id.radioButton2:
                        type = disc.get(position).getType2();
                        break;
                    case R.id.radioButton3:
                        type = disc.get(position).getType3();
                        break;
                    case R.id.radioButton4:
                        type = disc.get(position).getType4();
                        break;
                    default:
                        Toast.makeText(mContext,"Default", Toast.LENGTH_SHORT).show();
                        break;
                }

                saved = String.valueOf(position);
                //put key-value pair
                spEditor.putString(saved,type);
                //save the preferences
                spEditor.apply();

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
