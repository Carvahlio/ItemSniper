package project.major.itemsniper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kurt on 5/13/2017.
 */
public class Comment {

    private String desc;
    private String user;
    private String comment_id;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String product_id;

    public Comment(String description,String user){

    }

    public Comment(JSONObject o){
        try {
            this.desc = o.getString("description");
            this.user  = o.getString("user");
            this.comment_id  = o.getString("comment_id");
            this.product_id = o.getString("product_id");
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

}
