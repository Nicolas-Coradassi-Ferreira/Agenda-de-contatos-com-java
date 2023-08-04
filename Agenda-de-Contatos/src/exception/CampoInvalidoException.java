package exception;

public class CampoInvalidoException extends RuntimeException{
    public CampoInvalidoException(String msg){
        super(msg);
    }
}
