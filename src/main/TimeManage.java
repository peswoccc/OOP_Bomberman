package main;

import java.util.Calendar;

public class TimeManage {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println(timeNow());
        }
    }

    public static long timeNow() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis() / 100;
    }
}
