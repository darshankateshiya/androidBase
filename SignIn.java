
package prowebtech.com.discounter.loginSignUpForgotPassword.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignIn {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private UserSignIn user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserSignIn getUser() {
        return user;
    }

    public void setUser(UserSignIn user) {
        this.user = user;
    }

}
