package tempakunoshiro.automaticotakumatching;

import android.os.Parcelable;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Nan on 2016/09/21.
 */
public class MyData implements Serializable {
    private MyUser user;
    private MyScream scream;
    private byte[] iconBytes;

    public MyData(MyUser user, MyScream scream) {
        this.user = user;
        this.scream = scream;
        this.iconBytes = null;
        if(!MyIcon.OTAKU_URI.equals(user.getIconUri())){
            InputStream is = null;
            try {
                File iconFile = new File(user.getIconUri().toString());
                if(iconFile.exists()){
                    is = new BufferedInputStream(new FileInputStream(iconFile));
                    byte[] b = new byte[1];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while (is.read(b) > 0) {
                        baos.write(b);
                    }
                    baos.close();
                    this.iconBytes = baos.toByteArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(is != null){
                        is.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    public MyUser getUser() {
        return user;
    }

    public MyScream getScream() {
        return scream;
    }

    public byte[] getIconBytes() {
        return iconBytes;
    }

    public String toBase64Data(){
        byte[] outBytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.flush();
            outBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(oos != null){
                    oos.close();
                }
                baos.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        if(outBytes == null){
            return "";
        }else{
            return Base64.encodeToString(outBytes, Base64.NO_WRAP);
        }
    }
}
