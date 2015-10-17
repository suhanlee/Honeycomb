# Honeycomb
Android Crash-Log Reporting Project (client &amp; server)

Introduction
-----------------------

1. Clone the repo from GitHub.

         $ git clone https://github.com/suhanlee/Honeycomb.git

2. Server

         $ cd server
         $ rails s

3. Client (now example)

         $ cd client && chmod +x gradlew
         $ ./gradlew

Library Setup
---------

Here's a example code.

```java
public class ExamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * For emulator ( host computer address : 10.0.2.2 )
         * You can replace address with yours.
         */
        Bee.init(this, "http://10.0.2.2:3000");
    }
}
```
