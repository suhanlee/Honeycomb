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
        Reporter.init(getApplicationContext(), "http://10.0.2.2:3000"); // setup crash-log reporter
        
