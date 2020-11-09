package com.example.knowyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalityProfileFragment extends Fragment {

    Button btnD, btnI, btnS, btnC;
    TextView tv,title;
    DatabaseReference mDatabaseContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_personality_profile, container, false);

        btnD = (Button) view.findViewById(R.id.btn_D);
        btnI = (Button) view.findViewById(R.id.btn_I);
        btnS = (Button) view.findViewById(R.id.btn_S);
        btnC = (Button) view.findViewById(R.id.btn_C);

        tv = (TextView) view.findViewById(R.id.textView5);
        title = (TextView) view.findViewById(R.id.textViewTitle);

        mDatabaseContent = FirebaseDatabase.getInstance().getReference()
                .child("assessment")
                .child("DISC")
                .child("personality");

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("The \"D\" Personality Style Explained");
                tv.setText("Dominance: direct, strong-willed, and forceful\n\n" +
                        "Goals: Bottom-line results, victory\n\n" +
                        "Fears: Being taken advantage of or appearing weak\n\n" +
                        "Leadership qualities: Showing confidence, taking charge, focusing on results" +
                        "\n\nThe D Personality Style tends to be direct and decisive, " +
                        "sometimes described as dominant. They would prefer to lead than follow, " +
                        "and tend towards leadership and management positions. They tend to have high self-confidence, " +
                        "and are risk takers and problem solvers, which enables others to look to them for decisions " +
                        "and direction. They tend to be self-starters." );
            }
        });

        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("The \"I\" Personality Style Explained");
                tv.setText("Influence: sociable, talkative, and lively (fast-paced and accepting)\n\n" +
                        "Goals: Popularity, approval, excitement\n\n" +
                        "Fears: Rejection, not being heard\n\n" +
                        "Leadership qualities: Showing enthusiasm, building professional networks" +
                        "\n\nThe I Personality Style is not afraid to be the center of attention. " +
                        "They are enthusiastic, optimistic, talkative, persuasive, impulsive and emotional. " +
                        "This personality type will trust others naturally, truly enjoys being around others, " +
                        "and functions best when around people and working in teams." );
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("The \"S\" Personality Style Explained");
                tv.setText("Steadiness: gentle, accommodating, and soft-hearted (moderate-paced and accepting)\n\n" +
                        "Goals: Harmony, stability\n\n" +
                        "Fears: Letting people down, rapid change\n\n" +
                        "Leadership qualities: Staying open to input, showing diplomacy" +
                        "\n\nThe S Personality Type is known for being steady, stable, and predictable. " +
                        "They are even-tempered, friendly, sympathetic with others, and very generous with loved ones. " +
                        "The S is understanding and listens well. Preferring close, personal relationships, " +
                        "the S is very opened with loved ones, but can also be possessive at times and hold them close." );
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("The \"C\" Personality Style Explained");
                tv.setText("Conscientiousness: private, analytical, and logical (moderate-paced and skeptical)\n\n" +
                        "Goals:  Accuracy, objective processes\n\n" +
                        "Fears: Being wrong, strong displays of emotion\n\n" +
                        "Leadership qualities: Communicating with clarity, promoting disciplined analysis" +
                        "\n\nThe C DISC Styles are accurate, precise, detail-oriented and conscientious. " +
                        "They think very analytically and systematically, and make decisions carefully with plenty of " +
                        "research and information to back it up. The C has very high standards for both themselves and others. " +
                        "Because they focus on the details and see what many other styles do not, they tend to be good problem " +
                        "solvers and very creative people." );
            }
        });

        return view;
    }


}
