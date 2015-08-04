This page discusses any setup needed for hardware communication

# Introduction #

Interactive Spaces runs in Java. Unfortunately the implementors of Java didn't see much need for accessing hardware, so left it out of the language. By now you would think it would be there, but no. Sigh.


# Details #

## Serial Communication ##

### Linux ###

Enter the following command in the shell

```
apt-get install librxtx-java
```

In Terminal go to the folder where you installed the controller.

Copy the file `comm-serial-rxtx.ext` from the `extras` folder to `lib/system/java`

Copy the file `interactivespaces-service-comm-serial-version.jar` from the `extras` folder to `bootstrap`. `version` will be the version number of the serial service jar found in the `extras` folder.

Start the controller.

You should see `comm.serial` listed as one of the services now available.

### Mac OSX ###

Download the RXTX library from http://rxtx.qbang.org/wiki/index.php/Download

Copy the `RXTXcomm.jar` from the above download to `/Library/Java/Extensions`.

If you are using 64-bit OSX, copy the jnilib from http://blog.iharder.net/2009/08/18/rxtx-java-6-and-librxtxserial-jnilib-on-intel-mac-os-x/ to `/Library/Java/Extensions`.

Add the following line to the `.profile` file in your home folder.

```
export DYLD_LIBRARY_PATH=/Library/Java/Extensions:$DYLD_LIBRARY_PATH
```

If you edited the `.profile` file from Terminal and will continue the later steps in the same Terminal session, type the following at the shell

```
source ~/.profile
```

Run the following two commands from Terminal

```
sudo mkdir /var/lock
sudo chmod a+rw /var/lock
```

In Terminal go to the folder where you installed the controller.

Copy the file `comm-serial-rxtx.ext` from the `extras` folder to `lib/system/java`

Edit the `lib/system/java/comm-serial-rxtx.ext` file so that it has only the following contents

```
package:gnu.io
```

Copy the file `interactivespaces-service-comm-serial-version.jar` from the `extras` folder to `bootstrap`. `version` will be the version number of the serial service jar found in the `extras` folder.

Start the controller.

You should see `comm.serial` listed as one of the services now available.