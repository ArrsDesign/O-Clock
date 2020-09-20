package com.example.oclock;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText mEmailLG, mPasswordLG;
    private Button mLogInLG;
    private TextView mRegisterLG;
    private ProgressDialog progressDialogLG;
    private FirebaseAuth mAuthLG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmailLG = findViewById(R.id.emailLogIn);
        mPasswordLG = findViewById(R.id.passwordLogIn);
        mLogInLG = findViewById(R.id.logInBtn);
        mRegisterLG = findViewById(R.id.registerLink);
        progressDialogLG = new ProgressDialog(this);
        mAuthLG = FirebaseAuth.getInstance();

        mLogInLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();

            }
        });
        mRegisterLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Login(){
        String email = mEmailLG.getText().toString();
        String password = mPasswordLG.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailLG.setError("Enter your Email");
            return;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordLG.setError("Enter your Password");
            return;
        }

        progressDialogLG.setMessage("Please wait...");
        progressDialogLG.show();
        progressDialogLG.setCanceledOnTouchOutside(false);

        mAuthLG.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "Log In Failed", Toast.LENGTH_LONG).show();
                }
                progressDialogLG.dismiss();

            }
        });
    }
}