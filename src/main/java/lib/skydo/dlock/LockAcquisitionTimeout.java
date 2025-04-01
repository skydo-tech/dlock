package lib.skydo.dlock;

public class LockAcquisitionTimeout extends Throwable{
    public LockAcquisitionTimeout(String cause) {
        super(cause);
    }
}
