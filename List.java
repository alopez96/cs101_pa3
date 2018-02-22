//Arturo Lopez
//alopez96
//pa3
//List.java

class List {
  private class Node{
    Object data;
    Node next;
    Node previous;
    //constructor
    Node(Object data) {
      this.data = data; 
      next = null;
      previous = null;
    }	
    //toString() : overrides object's toString() method
    public String toString() {
      return String.valueOf(data);
    }
  }

  //Fields
  private Node front;
  private Node back;
  private Node cursor;
  private int length;
  private int cursorIndex;
  //constructor
  List(){
    back = front = cursor = null;
    length = 0;
    cursorIndex = -1;
  }

  // toString()
  // Overides Object's toString() method.
  public String toString(){
    StringBuffer sb = new StringBuffer();
    Node N = front;
    while(N!=null){
      if(N == front){
        sb.append(N.toString());
        N = N.next;
      }else{
        sb.append( " " + N.toString());
        N = N.next;
      }
    }return new String(sb);
  }

  //Access functions----------------------------------------------------
  //int length()
  //returns the number of elements in this list
  int length(){
    return length;
  }

  //int index()
  //If cursor is defined, returns the index of the cursor, otherwise return -1
  int index(){
    if(cursor == null){
      return -1;
    }else{ 
      return cursorIndex;
    }	
  }

  //int front()
  //Returns front element. Pre: length()>0
  Object front(){
    if( this.length <= 0 ){
      throw new RuntimeException(
          "List error: front() called on length not greater than 0");
    }else{
      return front.data;
    }
  }

  //int back()
  //Returns back element. Pre: length() >0
  Object back(){
    if(this.length <= 0){
      throw new RuntimeException(
          "List error: back() called on length not greater than 0");
    }else{
      return back.data;
    }
  }

  //int get()
  //Returns cursor element
  //Pre: length()>0, index()>=0
  Object get(){
    /*if(length() <= 0 || index() < 0){
      throw new RuntimeException(
      "List error: get() called on length not greater than 0 and index() not greater than 0");
      }else{*/
    if(index() != -1){
      return cursor.data;
    }
    return null;
  }

  //boolean equals(List L)
  //Returns true iff if this List and L are the same integer sequence
  //The states of the of the cursor in the two Lists are not used to
  //determining equality.
  public boolean equals(Object x)
  {
    List L;
    if(x instanceof List){
      L = (List)x;
      Node J = this.front;
      Node K = L.front;
      if(L.length != this.length){
        return false;
      }
      while(J != null && K != null){
        if(K.data != J.data){
          return false;
        }else{	
          J = J.next;
          K = K.next;
        }
      }
    }
    return true;
  }

  //Manipulation procedure*********************************************
  //void clear()	resets the List to its original empty state
  void clear(){
    front = back = cursor = null;
    length = 0;
    cursorIndex = -1;
  }



  //void moveFront()
  //If List is non-empty, places the cursor under the front element,
  //otherwise does nothing.
  void moveFront(){
    if(this.length < 0){
      return;
    }
    cursor = front;
    cursorIndex = 0;
  }

  //void moveBack()
  //If List is non-empty, places the cursor under the back element,
  // otherwise does nothing.
  void moveBack(){
    if(this.length < 0){	
      return;
    }
    cursor = back;
    cursorIndex = length - 1;
  }

  //void movePrev()
  //If cursor is defined and not at front, moves cursor one step toward
  //front of this List, if cursor is defined and at front, cursor becomes
  //undefined, if cursor is undefined does nothing.
  void movePrev() {
    if(this.length < 0){
      throw new RuntimeException("move prev called improper");
    }
    if(this.length >= 0){
      cursor = cursor.previous;
      cursorIndex--;
    }
    else if(cursor != null && cursor == front){
      cursor = null;
      cursorIndex = -1;
    }
    else if(cursor == null){
      return;
    }
  }

  //void moveNext()
  //If cursor is defined and not at back, move cursor one step toward
  //back of List, if cursor is defined and at back, cursor becomes undefined
  //if cursor is undefined, does nothing.
  void moveNext(){
    if(this.length < 0){
      throw new RuntimeException("move next called improper");
    }
    if(cursor == null){
      return;
    }
    else if(cursor == back){
      cursor = null;
      cursorIndex = -1;
    }else{
      cursor = cursor.next;
      cursorIndex++;
    }
  }

  //void append(int data) 
  //Insert new element into this List. 
  //If List is non-empty, insertion takes place after back element.
  void append(Object data){
    Node B = new Node(data);
    if (this.length <= 0){
      front = back = B;
      length++;
    }else{
      B.previous = back;
      back.next = B;
      B.next = null;
      back = B;
      length++;
    }
  }

  //void prepend(int data)
  //Insert new element into this list. If List is non-empty,
  //insertion takes place before front element.	
  void prepend(Object data){
    Node A = new Node(data);
    if (this.length <= 0){
      front = back = A;
      length++;
    }else{
      A.next = front;
      front.previous = A;
      front = A;
      length++;
      if( cursorIndex != -1){
        cursorIndex++;
      }
    }
  }

  //void insertBefore(int data)
  //Insert new element before cursor.
  //Pre: length()>0, index()>=0
  void insertBefore(Object data){
    Node A = new Node(data);
    if(length() <= 0 && index() < 0){
      throw new RuntimeException(
          "error: length() is not greater than 0, or index is not greater than or equal to 0");
    }
    if(front == cursor || index() < 0){
      prepend(data);
      return;
    }else{
      A.next = cursor;
      A.previous = cursor.previous;
      cursor.previous.next = A;
      cursor.previous = A;
      length++;
      cursorIndex++;
    }
  }

  //void insertAfter(int data) 
  //Inserts new element after cursor.
  // Pre: length()>0, index()>=0
  //void insertAfter
  void insertAfter(Object data){
    Node A = new Node(data);
    if(length() <= 0 && index() < 0){
      throw new RuntimeException(
          "error: length() is not greater than 0, and index() is not greater than or equal to 0");
    }
    if(back == cursor || length == 1){
      append(data);
      return;
    }else{
      A.previous = cursor;
      A.next = cursor.next;
      cursor.next.previous = A;
      cursor.next = A;	
      length++;
    }
  }

  //void deleteFront() 
  //Deletes the front element. Pre: length()>0
  void deleteFront(){
    if(length <= 0){
      throw new RuntimeException(
          "error: length is not greater 0");
    }
    if(cursor == front){
      front = front.next;
      cursor = null;	
      cursorIndex = -1;
      length--;
    }else{
      front = front.next;
      length--;
      cursorIndex--;
    }
  }

  //void deleteBack() 
  //Deletes the back element. Pre: length()>0
  void deleteBack(){
    if(length <= 0){
      throw new RuntimeException(
          "error: length is not greater than 0");
    }
    else if(cursor != back || cursor == null){
      back = back.previous;
      length--;
    }
    else if(cursor == back){
      back = back.previous;
      cursor = null;
      cursorIndex = -1;
      length--;
    }
  }

  //void delete() 
  //Deletes cursor element, making cursor undefined.
  //Pre: length()>0, index()>=0
  void delete(){
    if(length()<= 0 || index() < 0){
      throw new RuntimeException(
          "error: length is not greater than 0");
    }
    if(cursor == front){
      deleteFront();
      return;
    }
    if(cursor == back){
      deleteBack();
      return;
    }
    if(cursor == null){
      return;
    }
    if(length <= 0 && cursorIndex < 0){
      //      System.out.println("length <= 0 && cursorIndex < 0");
      //	System.out.println("List error:delete() called with length !> 0\n");
      //	System.out.println("deleteFront called with length !> 0\n");
    }else{
      cursor.previous.next = cursor.next;
      cursor.next.previous = cursor.previous;
      cursor /*= cursor.previous =cursor.next*/ = null;
      cursorIndex--;
      length--;
    }
  }

}
