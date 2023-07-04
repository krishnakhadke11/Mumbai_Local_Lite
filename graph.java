import java.util.*;
import java.io.*;

public class graph {
    private class pathOrDistance{
        int shortestDist;
        List<String> path;
        private pathOrDistance(int shortestDist,List<String> path){
            this.shortestDist=shortestDist;
            this.path=path;
        }
        private pathOrDistance(int shortestDist){
            this.shortestDist=shortestDist;
            path = new ArrayList<>();
        }
    }
    static HashMap<String,List<pair>> local_map ;
    static List<String>totalStations;
     static{
        local_map=new HashMap<>();
        totalStations=new ArrayList<>();
        create_map();
    }
    private static void addEdge(String node1,String node2,int dist){
        if(!local_map.containsKey(node1)){
            local_map.put(node1,new ArrayList<>());
        }
        if(!local_map.containsKey(node2)){
            local_map.put(node2,new ArrayList<>());
        }
        local_map.get(node1).add(new pair(node2,dist));
        local_map.get(node2).add(new pair(node1,dist));
    }
    private static void create_map(){
        totalStations.add("Noida Sector 62~B");
        totalStations.add("Botanical Garden~B");
        totalStations.add("Yamuna Bank~B");
        totalStations.add("Rajiv Chowk~BY");
        totalStations.add("Vaishali~B");
        totalStations.add("Moti Nagar~B");
        totalStations.add("Janak Puri West~BO");
        totalStations.add("Dwarka Sector 21~B");
        totalStations.add("Huda City Center~Y");
        totalStations.add("Saket~Y");
        totalStations.add("Vishwavidyalaya~Y");
        totalStations.add("Chandni Chowk~Y");
        totalStations.add("New Delhi~YO");
        totalStations.add("AIIMS~Y");
        totalStations.add("Shivaji Stadium~O");
        totalStations.add("DDS Campus~O");
        totalStations.add("IGI Airport~O");
        totalStations.add("Rajouri Garden~BP");
        totalStations.add("Netaji Subhash Place~PR");
        totalStations.add("Punjabi Bagh West~P");

        addEdge("Noida Sector 62~B", "Botanical Garden~B", 8);
        addEdge("Botanical Garden~B", "Yamuna Bank~B", 10);
        addEdge("Yamuna Bank~B", "Vaishali~B", 8);
        addEdge("Yamuna Bank~B", "Rajiv Chowk~BY", 6);
        addEdge("Rajiv Chowk~BY", "Moti Nagar~B", 9);
        addEdge("Moti Nagar~B", "Janak Puri West~BO", 7);
        addEdge("Janak Puri West~BO", "Dwarka Sector 21~B", 6);
        addEdge("Huda City Center~Y", "Saket~Y", 15);
        addEdge("Saket~Y", "AIIMS~Y", 6);
        addEdge("AIIMS~Y", "Rajiv Chowk~BY", 7);
        addEdge("Rajiv Chowk~BY", "New Delhi~YO", 1);
        addEdge("New Delhi~YO", "Chandni Chowk~Y", 2);
        addEdge("Chandni Chowk~Y", "Vishwavidyalaya~Y", 5);
        addEdge("New Delhi~YO", "Shivaji Stadium~O", 2);
        addEdge("Shivaji Stadium~O", "DDS Campus~O", 7);
        addEdge("DDS Campus~O", "IGI Airport~O", 8);
        addEdge("Moti Nagar~B", "Rajouri Garden~BP", 2);
        addEdge("Punjabi Bagh West~P", "Rajouri Garden~BP", 2);
        addEdge("Punjabi Bagh West~P", "Netaji Subhash Place~PR", 3);
    }
    public void display_stations(){
        System.out.println("\n***********************************************************************\n");
        int i=1;
        for(String stations:totalStations){
            System.out.println(i++ +"."+stations);
        }
        System.out.println("\n***********************************************************************\n");
    }
    public void display_map(){
        System.out.println("\t Delhi Metro Map");
        System.out.println("\t------------------");
        System.out.println("----------------------------------------------------\n");
        for(HashMap.Entry<String,List<pair>> entry : local_map.entrySet()){
            System.out.println(entry.getKey()+"=>");
            for(pair it : entry.getValue()){
                System.out.println("\t"+it.node+"\t"+it.dist);
            }
            System.out.println();
        }
    }
    //Using Djikstra Algorithm --> minHeap
    private pathOrDistance shortest_path(String src,String dest){
         PriorityQueue<pair> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.dist));
         minHeap.add(new pair(src,0));
         HashMap<String,Integer> minDistFromSrc =new HashMap<>();
         HashMap<String,String> parent = new HashMap<>();
         for(String stn : totalStations){
             parent.put(stn,stn);
         }
        for (String totalStation : totalStations) {
            minDistFromSrc.put(totalStation, Integer.MAX_VALUE);
        }
         minDistFromSrc.put(src,0);
         while(!minHeap.isEmpty()){
             String node = minHeap.peek().node;
             int d = minHeap.poll().dist;
             for(pair it : local_map.get(node)){
                 if(d+it.dist<minDistFromSrc.get(it.node)){
                    minDistFromSrc.put(it.node,d+it.dist);
                    minHeap.add(new pair(it.node,minDistFromSrc.get(it.node)));
                    parent.put(it.node,node);
                 }
             }
         }
         List<String>path = new ArrayList<>();
         if(minDistFromSrc.get(dest)==Integer.MAX_VALUE){
             path.add("NA");
             return new pathOrDistance(-1,path);
         }
         String node = dest;
         while(parent.get(node) != node){
             path.add(node);
             node = parent.get(node);
         }
         path.add(src);
         Collections.reverse(path);

         return new pathOrDistance(minDistFromSrc.get(dest),path);
    }
    private List<String> get_interchanges(List<String> path){
         int i=0;
         int j=0;
         int interchange =0;
         List<String> listInter = new ArrayList<>();
         while(i<path.size() && j<path.size()){
             String iAbbr = path.get(i).substring(path.get(i).indexOf("~")+1);
             String jAbbr = path.get(j).substring(path.get(j).indexOf("~")+1);
             if(iAbbr.equals(jAbbr)){
                 j++;
             }else{
                 String s = path.get(i)+"=>"+path.get(j-1);
                 listInter.add(s);
                 i=j;
                 interchange++;
             }
         }
         listInter.add(String.valueOf(interchange+1));
         return listInter;
    }
    public static void main(String[] args) throws IOException {
        graph g = new graph();
//        create_map(g);
        System.out.println("\n*************Welcome to Mumbai local app**********************");
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.println("\t\t\t\t~~LIST OF ACTIONS~~\n\n");
            System.out.println("1. LIST ALL THE STATIONS IN THE MAP");
            System.out.println("2. SHOW THE METRO MAP");
            System.out.println("3. GET SHORTEST DISTANCE FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("4. GET SHORTEST TIME TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("5. GET SHORTEST PATH (DISTANCE WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("6. GET SHORTEST PATH (TIME WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("7. EXIT THE MENU");
            System.out.print("\nENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 7) : ");
            int ch = -1;
            try{
                ch=Integer.parseInt(inp.readLine());
            }catch(Exception e){
                //Handle exceptions
            }
            System.out.print("\n***********************************************************\n");
            switch (ch) {
                case 1 ->
//                    System.out.println("Case 1");
                        g.display_stations();
                case 2 ->
//                    System.out.println("Case 2");
                        g.display_map();
                case 3 -> {
                    System.out.println("Case 3");
                    g.display_stations();
                    String src = "";
                    String dest = "";
                    System.out.println("\n Enter src Serial Number");
                    int choice = Integer.parseInt(inp.readLine());
                    if (choice < 0 || choice > totalStations.size()) {
                        System.out.println("Invalid choice");
                        System.exit(0);
                    } else {
                        src = totalStations.get(choice - 1);
                        choice = -1;
                    }
                    System.out.println("Enter Destination Serial Number");
                    choice = Integer.parseInt(inp.readLine());
                    if (choice < 0 || choice > totalStations.size()) {
                        System.out.println("Invalid choice");
                        System.exit(0);
                    } else {
                        dest = totalStations.get(choice - 1);
                    }
                    System.out.println("Shortest path from " + src + " to " + dest + " : \"" + g.shortest_path(src, dest) + " KM\"");
                }
                case 4 -> {
//                    System.out.println("Case 4");

                }
                case 5 -> {
                    System.out.println("Case 5");
                    g.display_stations();
                    String src = "";
                    String dest = "";
                    System.out.println("\n Enter src Serial Number");
                    int choice = Integer.parseInt(inp.readLine());
                    if (choice < 0 || choice > totalStations.size()) {
                        System.out.println("Invalid choice");
                        System.exit(0);
                    } else {
                        src = totalStations.get(choice - 1);
                        choice = -1;
                    }
                    System.out.println("Enter Destination Serial Number");
                    choice = Integer.parseInt(inp.readLine());
                    if (choice < 0 || choice > totalStations.size()) {
                        System.out.println("Invalid choice");
                        System.exit(0);
                    } else {
                        dest = totalStations.get(choice - 1);
                    }
                    System.out.println("SOURCE STATION : " + src);
                    System.out.println("Destination STATION : " + dest);
                    pathOrDistance pathAndDist = g.shortest_path(src,dest);
                    System.out.println("DISTANCE : " + pathAndDist.shortestDist);
                    System.out.println("NUMBER OF INTERCHANGES : " + (pathAndDist.path.size()-1));
                    System.out.println("~~~~~~~~~~~~~");
                    System.out.println("START  ==>  " + src);
                    for(String it :pathAndDist.path){
                        System.out.println(it);
                    }
                    System.out.print("   ==>    END");
                    System.out.println("\n~~~~~~~~~~~~~");
                }

                case 6 -> System.out.println("Case 6");
                case 7 -> System.exit(0);
                default -> {  //If switch expression does not match with any case,
                    //default statements are executed by the program.
                    //No break is needed in the default case
                    System.out.println("Please enter a valid option! ");
                    System.out.println("The options you can choose are from 1 to 6. ");
                }
            }
        }
    }
}