package com.example;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import java.util.List;
import java.util.Arrays;

public class JavaJNAGoClient {

    public interface GoLib extends Library {

        // GoString class maps to:
        // C type struct { const char *p; GoInt n; }
        public class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {
            }

            public String p;
            public long n;

            protected List getFieldOrder() {
                return Arrays.asList(new String[] { "p", "n" });
            }
        }

        String GetGoString();

        int GetGoInt();

        String EncryptMessage(GoString.ByValue key, GoString.ByValue message);
    }

    public static void main(String[] args) {
        GoLib goLib = (GoLib) Native.loadLibrary("/Users/owner/dev/github.com/aknair/stuff/jna/gojnalib.so",
                GoLib.class);
        System.out.println(goLib.GetGoString());
        System.out.println(goLib.GetGoInt());

        GoLib.GoString.ByValue key = new GoLib.GoString.ByValue();
        key.p = "i-am-a-key-that-is-32-bytes-long";
        key.n = key.p.length();

        GoLib.GoString.ByValue message = new GoLib.GoString.ByValue();
        message.p = "My Super Secret Message";
        message.n = key.p.length();

        System.out.println(goLib.EncryptMessage(key, message));

    }
}
