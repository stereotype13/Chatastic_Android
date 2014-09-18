package io.chatastic.chatastic.Models;

import android.content.Context;
import android.telephony.TelephonyManager;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ModelList;
import se.emilsjolander.sprinkles.Query;

/**
 * Created by r on 9/17/2014.
 */
public class PhoneInfo {
    private static PhoneInfo phoneInfo;
    private String mobileNumber;
    private String userName;
    private static ThisDevice DEVICE;

    private PhoneInfo(Context context) {

        //See if we have a previously defined device
        CursorList thisDeviceCursorList = Query.all(ThisDevice.class).get();
        ModelList<ThisDevice> thisDeviceList = ModelList.from(thisDeviceCursorList);

        if(thisDeviceList.size() < 1) {

            //This is the first time the app is run
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            mobileNumber = tm.getLine1Number();

            if(mobileNumber != null) {
                userName = mobileNumber;
            }
            else {
                //Prompt the user to choose a username
            }

            DEVICE = new ThisDevice();
            DEVICE.setMobileNumber(mobileNumber);
            DEVICE.setUserName(userName);
            DEVICE.save();
        }
        else {
            DEVICE = thisDeviceList.get(0);
            this.setMobileNumber(DEVICE.getMobileNumber());
            this.setUserName(DEVICE.getUserName());
        }


    }

    public static PhoneInfo getPhoneInfo(Context context) {

        if(phoneInfo != null) {
            return phoneInfo;
        }
        else {
            phoneInfo = new PhoneInfo(context);
            return phoneInfo;
        }

    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
