package ClientSide;

import java.io.Serializable;

public class JoinData implements Serializable {

    private String ip;

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public JoinData(String ip){
        setIp(ip);
    }
}
