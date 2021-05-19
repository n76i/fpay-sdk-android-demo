# fpay-sdk-android-demo v1.0

FPay SDK Integration Guide for Android

Vietnamese version [see here](./README-vi.md)

# Introduce
FPay SDK is a library for apps to interact with FPay. FPay SDK includes the following main functions:
- Use the FID SDK to log into FPay's system
- Currently, the FPay SDK supports all devices with Android 4.1 (API 16) and higher operating systems installed.

## Support
If you have any questions during the integration process, you can:

- Contact directly via email with SDK team for help
- Refer to the demo [here](https://github.com/n76i/fpay-sdk-android-demo)

## Step 1
Successfully integrated FID SDK, instructions can be found [here](https://github.com/n76i/fid-sdk-android-demo/blob/main/README-vi.md)

## Step 2
Download FPay SDK [here](https://github.com/n76i/fpay-sdk-android-demo/raw/main/fpay-sdk/fpay-sdk-release.aar)

## Step 3: Add FPay SDK to the project
In Android Studio:
- Select module app, right click then select `New` -> `Module`
- Scroll down to select `Import .JAR/.AAR`-> `Next`
- In the file name, point to the path to the SDK file loaded in step 1
- In the sub project name you can change the name to `fpay-sdk`

Note that if you find that the import was successful but not usable, it is possible that the SDK has not been added to `app`. You can go to `File` ->` Project Structure` to add the SDK to the `app` module

## Step 4: Additional configuration
### 1, Configuration AndroidManifest.xml
In `AndroidManifest.xml` add the following code in` application`:
```java
<activity android:name="ai.ftech.fpay.inappbrowser.ChromeTabsManagerActivity"/>
```
### 2, Install some additional libraries
Add the following libraries:
```
implementation 'org.greenrobot:eventbus:3.2.0'
```

Finished, to check everything is working we go to the user guide
## Basic instructions for use
For this tutorial, we will need to import the following classes:
```java
import ai.ftech.fpay.FPay;
```

### 1, Initialization
You need to make sure to initialize FPay before using its other functions, which can be called in the Activity's `onCreate` (after the FID constructor)
```java
FPay.initialize(this);
```
Note that FPay will automatically configure the sandbox environment if FID is configured in a sandbox environment

### 2, Log in to FPay and use FPay's functions
Currently, FPay SDK is supporting login and performing deposit and withdrawal operations through the website, to open you use the following function:
```java
FPay.browserPayment(MainActivity.this);
```
