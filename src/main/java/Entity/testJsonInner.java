package Entity;

/**
 * Created by yamengwenjing on 2017-03-18.
 */
public class testJsonInner {


    public Data data;

    public static class Data{
        public String message;
        public String domain_id;
        public Data(String mess,String di){
            this.message =mess;
            this.domain_id =di;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
