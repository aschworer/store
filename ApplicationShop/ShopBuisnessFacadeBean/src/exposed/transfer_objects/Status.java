package exposed.transfer_objects;

import java.io.Serializable;

public enum Status implements Serializable {
    NEW, IN_PACK, DELIVERING, COMPLETE;

    public static String convertToString(Status status) {
        String res = null;
        if (status == Status.NEW) {
            res = "new";
        } else if (status == Status.IN_PACK) {
            res = "in_pack";

        } else if (status == Status.DELIVERING) {
            res = "delivering";
        } else if (status == Status.COMPLETE) {
            res = "complete";
        }
        return res;
    }

    public static Status getStatus(String status) {
        Status res = Status.NEW;
        if ("new".equals(status)) {
            res = Status.NEW;
        } else if ("in_pack".equals(status)) {
            res = Status.IN_PACK;
        } else if ("delivering".equals(status)) {
            res = Status.DELIVERING;
        } else if ("complete".equals(status)) {
            res = Status.COMPLETE;
        }
        return res;
    }

    public static Status getStatus(int num){
        Status res = Status.NEW;
        if (num==0) {
            res = Status.NEW;
        } else if (num==1) {
            res = Status.IN_PACK;
        } else if (num==2) {
            res = Status.DELIVERING;
        } else if (num==3) {
            res = Status.COMPLETE;
        }
        return res;
    }
}
