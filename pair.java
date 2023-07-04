public class pair{
    String node;
    int dist;
    pair(String node,int dist){
        this.node = node;
        this.dist = dist;
    }
    pair(String node){
        this.node = node;
        this.dist = 0;
    }
}