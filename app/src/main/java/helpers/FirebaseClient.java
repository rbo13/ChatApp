package helpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by bito on 4/17/2017.
 */

public class FirebaseClient {

    private FirebaseAuth mFirebaseAuth;

    public FirebaseClient() {
        this.mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> loginUserWithEmailAndPassword(String email, String password) {
        return mFirebaseAuth.signInWithEmailAndPassword(email, email);
    }

    public Task<AuthResult> registerUser(String email, String password) {
        return mFirebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public void doLogout() {
        mFirebaseAuth.signOut();
    }
}
