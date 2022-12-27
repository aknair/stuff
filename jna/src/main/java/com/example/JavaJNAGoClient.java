package com.example;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.UUID;
import java.nio.charset.Charset;

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
    int LenMap();

    String EncryptMessage(GoString.ByValue key, GoString.ByValue message);
  }

  public static void main(String[] args) throws Exception {
    final GoLib goLib = (GoLib) Native.loadLibrary("/Users/owner/dev/github.com/aknair/stuff/jna/gojnalib.so",
        GoLib.class);

    int numThreads = 10;
    int numIterations = 1000;
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    long startTime = System.nanoTime();
    for (int i = 0; i < numIterations; i++) {
      executor.submit(new Runnable() {
        public void run() {
          try {
            // System.out.println(goLib.GetGoString());
            // System.out.println(goLib.GetGoInt());
            GoLib.GoString.ByValue key = new GoLib.GoString.ByValue();

            // key.p = "i-am-a-key-that-is-32-bytes-long";
            UUID randomUUID = UUID.randomUUID();
            key.p = randomUUID.toString().replaceAll("-", "");
            key.n = key.p.length();

            GoLib.GoString.ByValue message = new GoLib.GoString.ByValue();
            Random rd = new Random();
            byte[] arr = new byte[1048576];
            rd.nextBytes(arr);
            String generatedString = new String(arr, Charset.forName("UTF-8"));
            message.p = generatedString;
            message.n = message.p.length();

            goLib.EncryptMessage(key, message); // Encrypt and ignore the returned value

          } catch (Exception e) {
            System.out.println(e.getMessage());
          } finally {

          }
        }
      });
    }
    executor.shutdown();
    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    long elapsedTime = System.nanoTime() - startTime;
    double elapsedTimeMs = (double) elapsedTime / 1000000;
    System.out.println("Number of unique string lengths is "+goLib.LenMap());
    System.out.printf("Total elapsed time: %.2f milliseconds%n", elapsedTimeMs);

  }
}
