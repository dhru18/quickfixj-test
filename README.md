# quickfixj-test

To print the Garbage Collector into a file, set the following parameter to the JVM:

-Xloggc:/tmp/memory.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime

Then, you can use GCViewer program to load the memory.log file and visualize the GC activity.