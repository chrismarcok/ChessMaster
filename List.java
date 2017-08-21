/* Last Edited: by ATHER on
 * JAN 04 2017
 */

public class List{
  private Node head;
  
  class Node
  {
    int [] info = new int [2]; //an array of coordinates {x,y}
    Node link;
    
    Node (int [] info, Node link)
    {
      this.info = info;
      this.link = link;
    }
  }
  
  public void addToFront (int x, int y) //adds a node to the front of the list
  {
    int [] i = {x,y};
    if (head == null)
      head = new Node (i,null) ;
    else
      head = new Node (i,head); 
    
  }
  
  public void deleteList(){
    head = null;
  }
  
  public boolean isAPartOfList( int [] spot){
    for (Node temp = head; temp!=null; temp=temp.link)
    {
      if (temp.info[0] == spot[0] && temp.info[1] == spot[1])
        return true;
    }
    return false;
  }
  
  public void deleteStuff (int x, int y, boolean sameColour){ //deletes all items BEFORE a specified spot, possibly inclusive
    int [] i = {x,y};
    if (head!=null)
    { /*if (head.link == null)
     {
     if (sameColour)
     head = null;
     my dads a chef, you think thats great?
     just try to guess, whats on your plate.
     he mixes things, and then he bakes
     why cant he stick, to chocolate cake?
     }*/
      for (Node temp = head; temp.link != null; temp = temp.link)
      { 
        if (temp.info[1] == i [1] && temp.info [0] == i[0]){
          if (sameColour)
            head = temp.link;
          else
            head=temp;
        }
      }
    }
    
  }
  public int length () //return length of list
  {
    int counter =0;
    if (head!=null)
    {
      counter++;
      for (Node temp =head; temp.link !=null;temp=temp.link)
        counter++;
    }
    
    return counter;
  }
  public String printList(){
    String holder = "";
    for (Node temp=head; temp!=null; temp=temp.link){
      //System.out.println("asdfdsa");
      holder +=( " "+temp.info[0]+","+temp.info[1]+"//");
    }
    return holder;
  }
  
  public int [] returnValue (int spot) //return the value of the List at the spot in question. 0 = first item in list.
  {
	  if (head == null){
		  int[] asdf = {-123,-123};
		  return asdf;
	  }
    Node temp = head;
    
    while (temp!= null && spot>0)
    {
      temp=temp.link;
      spot--;
    }
    return temp.info;
  }
  public void addListToFront (List toBeAdded)
  { 
    if (toBeAdded.head!=null)
    {
      for (Node temp = toBeAdded.head; temp!=null; temp=temp.link)
        this.addToFront(temp.info[0],temp.info[1]);
      
    }
  }
}