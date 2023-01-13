/**
@author Hridika Banik <a 
href="mailto:hridika.banik@ucalgary.ca">firstname.lastname@ucalgary.ca</a>
@version 1.6
@since 1.0
UCID: 30123716
Tutorial number: T05
TA Name: Roghayeh Heidari
*/
import java.io.*;
import java.util.Scanner;

public class Assign3 {
    public static void main(String[] args) throws IOException { 
        if (args.length != 3) {
            System.out.println("Arguments are of invalid number. Enter again.");
            System.exit(1);
        }
        String inputFile = args[0] + ".txt"; 
        String output1 = args[1] + ".txt" ;
        String output2 = args[2] + ".txt" ;  
        BinaryTree binTree= new BinaryTree();
        File inputF = new File(inputFile);
        Scanner scFile = new Scanner(inputF);
        while(scFile.hasNextLine()) {
            String element = scFile.nextLine();
            if (element.toLowerCase().charAt(0) == 'i') {
                binTree.Insert(element);
            }
            else if(element.toLowerCase().charAt(0) == 'd') {
                binTree.Delete(element.substring(1,42));
            }
            else{
                System.out.println("Data field does not contain Is or D as a first character.");
                System.exit(1);
            }
        }scFile.close();
        binTree.depthFirst(output1);
        binTree.breadthFirst(output2);
        System.out.println("\nProgram Complete");
    }

}
/*This code has been adapted from:
     *Reference: https://www.softwaretestinghelp.com/binary-search-tree-in-java/
     */
class BinaryTree {
    Node root;
    static int size;
    BinaryTree(){
        root = null;
    }
    public BinaryTree(int size) {
        this.size = size;
    }
    void Insert(String data) { 
        root = insertOps(root, data);
    } 
    Node insertOps(Node root, String key) { 
        if (root == null) { 
            root = new Node(key); 
            return root; 
        } 
      
        /**The code(' .replaceAll("[^A-Za-z]+", "") ') has been adapted from:
         *Reference:https://stackoverflow.com/questions/9872002/keep-only-alphabet-characters
         */
        if (key.substring(8,33).replaceAll("[^A-Za-z]+", "").toLowerCase().compareTo(root.data.substring(7,32).replaceAll("[^A-Za-z]+", "").toLowerCase()) < 0)     
            root.left = insertOps(root.left, key); 
        else if (key.substring(8,33).replaceAll("[^A-Za-z]+", "").toLowerCase().compareTo(root.data.substring(7,32).replaceAll("[^A-Za-z]+", "").toLowerCase()) > 0)    
            root.right = insertOps(root.right, key); 
        
        return root; 
    }
    
    /*The code has been adapted from:
     *Reference: https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/
     */
    void Delete(String key) { 
        root = deleteOperation(root, key); 
    }
    Node deleteOperation(Node root, String key) {
        if (root == null) {
            return root;
        }    
        
        if (key.substring(7,32).replaceAll("[^A-Za-z]+", "").toLowerCase().compareTo(root.data.substring(7,32).replaceAll("[^A-Za-z]+", "").toLowerCase()) > 0) {
            root.right = deleteOperation(root.right, key);
        }
        else if (key.substring(7,32).replaceAll("[^A-Za-z]+", "").toLowerCase().compareTo(root.data.substring(7,32).replaceAll("[^A-Za-z]+", "").toLowerCase()) < 0) {
            root.left = deleteOperation(root.left, key);
        }
        else {
            if (root.right == null) {
                return root.left;
            
            }
            else if (root.left == null) {
                return root.right;
            }
            root.data = minVal(root.right);
            root.right = deleteOperation(root.right, root.data);
        }
        return root;
    }
    private static int getSize() {
        return BinaryTree.size;
    }
    /*This code has been adapted from:
    *Reference: https://www.geeksforgeeks.org/find-the-minimum-element-in-a-binary-search-tree/
    */
    String minVal(Node root) {
        String minimum = root.data;
        while (root.left != null) {
            minimum = root.left.data;
            root = root.left;
        }
        return minimum;
    }

    void depthFirst(String output1) throws IOException {
        FileWriter writer1 = new FileWriter(output1, true);
        writer1.write("Depth-First,In-Order Traversal of Binary Search tree:");
        writer1.close();
        inOrder(root, output1);
    }
    /*The code has been adapted from
    *Reference: https://medium.com/javarevisited/how-to-print-nodes-of-a-binary-search-tree-in-sorted-order-8a4e52eb8856
    */
    public void inOrder(Node node, String output1) throws FileNotFoundException {
       if (node == null) {
           return;
       }
       inOrder(node.left, output1);
       writeToFile(node.data, output1);
       inOrder(node.right, output1);
    
    }
    /*This code has been adapted from:
    *Reference: https://math.hws.edu/eck/cs124/javanotes3/c11/ex-11-4-answer.html
    */
    void breadthFirst(String output2){
        try{
        FileWriter writer2 = new FileWriter(output2, true);
        writer2.write("Breadth-First Traversal of Binary Search tree:");
        writer2.close();
        OrderTraversal(root, output2);} 
        catch (IOException e){
            System.err.println("An IOException has been caught: " + e.getMessage());
        }
    }
    public void OrderTraversal(Node root, String output2) throws FileNotFoundException {
        if(root == null) {

            return;
          }
  
          Queue queue = new Queue(40); //approximate based on the known number of inputs
          queue.enqueue(root);
          while(!queue.isEmpty()) {
              Node node = queue.dequeue();
              writeToFile(node.data, output2);
              if(node.left != null) {
                  queue.enqueue(node.left);
              }
              if(node.right != null) {
                  queue.enqueue(node.right);
              }
          }
    }
    
    public void writeToFile(String data, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            /**
             * refrence for ' .replaceAll("[^A-Za-z]+", "") ' :
             * https://stackoverflow.com/questions/9872002/keep-only-alphabet-characters
             */
            writer.write("Student number    = " + data.substring(0,7) + "\n");
            writer.write("Student last name = " + data.substring(7,32).replaceAll("[^A-Za-z]+", "") + "\n");
            writer.write("Home department   = " + data.substring(32,36) + "\n");
            writer.write("Program           = " + data.substring(36,40).replaceAll("[^A-Za-z]+", "") + "\n");
            writer.write("Year              = " + data.substring(40) + "\n\n");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*The code has been adapted from: 
 *Reference: https://www.techiedelight.com/queue-implementation-in-java/
 */
class Queue {
    private Node[] arr;      
    private int front;      
    private int rear;         
    private int count;
    private int capacity;      
    Queue(int size) {
        arr = new Node[size];
        capacity = size;
        front = 0;
        rear = -1;
        count = 0;
    } 
    public Node dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow. Program Terminated");
            System.exit(-1);
        }
 
        Node x = arr[front]; 
        front = (front + 1) % capacity;
        count--; 
        return x;
    }
    public void enqueue(Node item) {
        if (isFull()) {
            System.out.println("Queue Overflow. Program Terminated");
            System.exit(-1);
        }
        rear = (rear + 1) % capacity;
        arr[rear] = item;
        count++;
    }

    //Returns the size of the queue
    public int size() {
        return count;
    }

    //Checks is queue is empty
    public boolean isEmpty() {
        return (size() == 0);
    }
 
    //Checks if queue is full
    public boolean isFull() {
        return (size() == capacity);
    }
}

class Node { 
    String data;
    Node right; 
    Node left; 
    Node parent;
    public Node(String stud) { 
        this.data = stud;
        this.right = null;
        this.left = null;
        this.parent = null;
        data = stud.substring(1,42);
    } 

}