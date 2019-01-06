package vitek.bakalari;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TokenCalculator {

    public static String calculate(String typ, String ikod, String salt, String passwordOriginal, String username) {
        String passwordEncrypted = salt + ikod + typ + passwordOriginal;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] passwordDigest = md.digest(passwordEncrypted.getBytes("UTF-8"));
            passwordEncrypted = Base64.encodeToString(passwordDigest, Base64.NO_WRAP);
            String token = "*login*" + username + "*pwd*" + passwordEncrypted + "*sgn*ANDR" + new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date());
            byte[] tokenDigest = md.digest(token.getBytes("UTF-8"));
            token = Base64.encodeToString(tokenDigest, Base64.NO_WRAP | Base64.URL_SAFE);
            return token;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
