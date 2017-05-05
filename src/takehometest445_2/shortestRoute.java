/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package takehometest445_2;


import java.awt.geom.Point2D;
import java.util.*;
import java.io.*;

public class shortestRoute {
    //old code
    static List<Point2D> plist = new ArrayList<>();
    static int numPoints = 0;
    static Point2D pointToConnect = null;
    
    public static void calcDistance(){
        //new way to calc distance
        //*
        Queue <Integer> toVisit = new LinkedList<>();
	Set <Integer> alreadyVisited = new HashSet<>();
	Map <Integer, Integer> parent = new HashMap<>();
        
        alreadyVisited.add(plist.indexOf(0));
        toVisit.add(plist.indexOf(0));
        parent.put(plist.indexOf(0), null);
        
        while(!toVisit.isEmpty()){
            int cur = toVisit.poll();
            
            
                if(!alreadyVisited.contains(plist.indexOf(cur))){
                    parent.put(plist.indexOf(cur), cur);
                    alreadyVisited.add(plist.indexOf(cur));
                    toVisit.offer(plist.indexOf(cur));
                }
        }
        /*
        
        //Old way to calc distance
        
        for(int i = 0; i < numPoints; i++){
            Double shortest_d = Double.MAX_VALUE;
            for(int j = i+1; j < numPoints; j++){
                Point2D p1 = plist.get(i);
                Point2D p2 = plist.get(j);
               
                Double d = p1.distance(p2);
                
                if (d < shortest_d){
                    shortest_d = d;
                    pointToConnect = p2;
                    
                }
            }
            if(i <numPoints-1){
            Collections.swap(plist, i+1, plist.indexOf(pointToConnect));
            }
        }
        */        
    }
    
    public static void printFile() throws IOException{
        FileWriter writer = null;
        
        try{
            writer = new FileWriter("C:/Users/Josh/Desktop/attempt1.dat");
            for(Point2D p: plist){
                Double x = p.getX();
                Double y = p.getY();
                int a = x.intValue();
                int b = y.intValue();
                
                writer.write(Integer.toString(a) + "  " + Integer.toString(b));
                writer.write(System.getProperty( "line.separator" ));
            }
            writer.close();
        }
        catch (IOException e) {
        } finally {
            writer.close();
        }
        
    }
        
    public static void readFile(){
        BufferedReader in = null;
        Scanner s = null;
        int i = 0;
        
        try{
            s = new Scanner(new BufferedReader(new FileReader("C:/Users/Josh/Desktop/challenge1.dat")));
            
            
            while (s.hasNext()) {                
                Point2D p = new Point2D.Double(s.nextDouble(),s.nextDouble());
                plist.add(p);
                i++;
            }
            s.close();
            numPoints = i;

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
            
    public static void printList(){
        int i = 0;
        int count = 0;
        while( i < plist.size()){
            System.out.println("- " + plist.get(i));
            i++;
            count++;
        }
        System.out.println(count);
}
}
