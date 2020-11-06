package cn.edu.nuaa.myclinic.exception;

public class SysException extends Exception{
    private String erroMsg;

    public String getErroMsg() {
        return erroMsg;
    }

    public SysException(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    public SysException(String message, String erroMsg) {
        super(message);
        this.erroMsg = erroMsg;
    }

    public SysException(String message, Throwable cause, String erroMsg) {
        super(message, cause);
        this.erroMsg = erroMsg;
    }

    public SysException(Throwable cause, String erroMsg) {
        super(cause);
        this.erroMsg = erroMsg;
    }

    public SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String erroMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.erroMsg = erroMsg;
    }
}
