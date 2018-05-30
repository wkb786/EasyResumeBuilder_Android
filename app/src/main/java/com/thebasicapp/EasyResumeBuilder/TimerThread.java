package com.thebasicapp.EasyResumeBuilder;

import android.os.Handler;
import android.os.Message;

/**
 * Created by absar on 5/6/2017.
 */

public class TimerThread {

    int count = 0;
    TimerInterface timerInterface;

    static final int MSG_DO_IT = 1;
    static final long TIME_BETWEEN_MESSAGES = 1000;  // 10 seconds

    public TimerThread() {
        // Start the timer, executing first event immediately.
        Message newMsg = mHandler.obtainMessage(MSG_DO_IT);
        mHandler.sendMessage(newMsg);
    }

    public TimerInterface getTimerInterface() {
        return timerInterface;
    }

    public void setTimerInterface(TimerInterface timerInterface) {
        this.timerInterface = timerInterface;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DO_IT: {

                    // Update your UI here...
                    count++;
                    Message newMsg = obtainMessage(MSG_DO_IT);
                    sendMessageDelayed(newMsg, TIME_BETWEEN_MESSAGES);
                }
                break;
            }
        }
    };

    public void stopThread() {
        // Stop the timer.
        mHandler.removeMessages(MSG_DO_IT);
        timerInterface.onStop(count);
        count = 0;
    }
}
