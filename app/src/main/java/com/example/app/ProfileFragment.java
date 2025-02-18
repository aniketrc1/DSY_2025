package com.example.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    FirebaseAuth f1;
    TextView t1, emailView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize FirebaseAuth and TextViews
        f1 = FirebaseAuth.getInstance();
        FirebaseUser user = f1.getCurrentUser();

        t1 = rootView.findViewById(R.id.nameofuser); // For username
        emailView = rootView.findViewById(R.id.profile_email); // For email

        if (user != null) {
            Log.d("ProfileFragment", "User email: " + user.getEmail());
            Log.d("ProfileFragment", "User display name: " + user.getDisplayName());

            // Get the user's display name or a default fallback
            String userName = user.getDisplayName();
            String email = user.getEmail();

            if (userName != null && !userName.contains("@")) {
                // If the display name is valid and does not resemble an email
                t1.setText(userName);
            } else if (email != null) {
                // If no valid display name, fall back to the email (just for display)
                t1.setText(email.split("@")[0]); // Use part before '@' as a placeholder
            } else {
                t1.setText("User");
            }

            // Set the email in the email view
            if (email != null) {
                emailView.setText(email);
            } else {
                emailView.setText("No email provided");
            }
        } else {
            Log.d("ProfileFragment", "No user is signed in.");
            t1.setText("Guest");
            emailView.setText("");
        }

        return rootView;
    }
}
