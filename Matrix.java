//Arturo Lopez
//fall2017,cs101,pa3
//Matrix.java
//this is a java file that uses a doubly linked list to implement
//a matrix calculator

class Matrix{
	private class Entry{
		//fields
		private int column;
		private double value;
		//constructor
		Entry(int column, double value){
			this.column = column;
			this.value = value;
		}
		//toString
		public String toString(){
			return "(" + column + ", " + value + ")";
		}
		public boolean equals(Object x){
			if(x == null){
				return false;
			}
			else if(getClass() != x.getClass()){
				return false;
			}else{
				Entry temp = (Entry) x;
				return (this.column == temp.column && this.value == temp.value);
			}
		}	
	}

	//Fields
	private List row[];
	private int matrixSize;
	private int NNZ;

	//Constructor
	//Makes a new n x n zero matrix
	//pre: n >= 1
	Matrix(int n){
		Matrix M;
		if(n < 1){
			throw new RuntimeException(
			"List error: n is less than 1");
		}else{	
			row = new List[n+1];
			for(int i = 1; i <= n; i++){
				row[i] = new List();
			}
			matrixSize = n;
			NNZ = 0;
		}
	}
	public String toString(){
		String sb = "";
		for(int i = 1; i <= matrixSize; i++){
			if(row[i].length() == 0){
			continue;
			}else{
			sb += i + ": " + row[i].toString() + "\n";
			}
		}
		return sb;
	}

	//Access functions******************************************************
	//returns n, the number of rows and columns of this Matrix
	int getSize(){
		return matrixSize;
	}

	//returns the number of non-zero entries in this Matrix
	int getNNZ(){
		return NNZ;
	}

	public boolean equals(Object x){
		if(!(x instanceof Matrix)){
			throw new RuntimeException("Matrix error: ");
		}
		Matrix M = (Matrix) x;
		if(getSize() == M.getSize() && NNZ == M.getNNZ()){
			for(int i = 1; i <= getSize(); i++){
				if(M.row[i].length() > 0 && row[i].length() > 0){
					row[i].moveFront();
					M.row[i].moveFront();
					while(row[i].index() >= 0){
						Entry one = (Entry) row[i].get();
						Entry two = (Entry) M.row[i].get();
						if(one. value != two.value){
							return false;
						}
						row[i].moveNext();
						M.row[i].moveNext();
					}
				}
			}
			return true;
		}
		return false;
	}
	
	//Manipulation procedures********************************************
	//sets this Matrix to the zero state
	void makeZero(){
		for(int i = 1; i <= matrixSize; i++){
			row[i].clear();
		}
		NNZ = 0;
	} 
	
	//returns a new Matrix having the same entries as this Matrix
	Matrix copy(){
		Matrix M = new Matrix(this.getSize());
		for(int i = 1; i <= matrixSize; i++){
			row[i].moveFront();
			if(row[i].length() == 0){
				continue;
			}else{
			while(row[i].index() >= 0){
				Entry x = (Entry) row[i].get();
				M.changeEntry(i, x.column, x.value);
				row[i].moveNext();
				}
			}
		}
		return M;
	}
	
	//changes ith row, jth column of this Matrix to x
	//pre: 1 <= i <= getSize(), 1 <= j <= getSize()
	void changeEntry(int i, int j, double x){
		Entry a = new Entry(j,x);
		if(!(( 1 <= i && i<= getSize()) && (1 <= j && j <= getSize()))){	//test precondition
			throw new RuntimeException(
			"error: i or j is not between 1 and getSize");
		}
		 if(row[i].length() == 0){
			if(x == 0){						//case1: x=0. Ai,Aj=0 - do nothing
				return;
			}
			else if(x!=0){						//case 2: x!=0, i,j=0 - insert new entry
				this.row[i].prepend(a);
				NNZ++;
			}
		}else{								//length != 0 at this point
			row[i].moveFront();
			if(x != 0){						//case3: x!=0, i,j!=0 - replace existing
				while(row[i].index() >= 0){			//set the while loop to make sure we dont fall off the list
					Entry back = (Entry) row[i].back();	//entry back will get the data at the back of the list
					Entry y = (Entry) row[i].get();		//entry x will get the data at the cursor(j, data)
					if(j > back.column){			//if the column we are inserting in is greater than the index of the back
						row[i].append(a);		//append at the end of the List
						row[i].moveBack();
						NNZ++;
					}		
					else if(j < y.column){			//if the column we are inserting in is less than the entry column
						row[i].insertBefore(a);		//insert before the cursor
						row[i].moveBack();		//move the cursor to the back
						NNZ++;				
					}
					else if(j == y.column){
						row[i].insertBefore(a);
						row[i].delete();		//delete cursor element
						row[i].moveBack();		//move the cursor to the back
					}
					row[i].moveNext();			//move the cursor to the next to make it fall off the list
				}
			}
			else{							//case4: x==0, i,j!=0 delete existing
				while(row[i].index() >= 0){
					Entry back = (Entry) row[i].back();	//entry back will get the data at the back of the list
					Entry y = (Entry) row[i].get();		//entry back will get the data at the back of the list
					if(j > back.column){			//if the column we are inserting is greater than the back
						return;				//do nothing
					}
					else if(j < y.column){			//do nothing
						return;
					}
					else if(j == y.column){			//if they are equal
						row[i].delete();		//delete the current
						row[i].moveBack();
						NNZ--;
					}
					row[i].moveNext();
				}
			}
		}
	}
	//returns new Matrix that is the scaler product of this Matrix with x
	Matrix scalarMult(double x){
		Matrix M = new Matrix(this.getSize());						//get elements from this Matrix
		for(int i = 1; i <= this.getSize(); i++){					//set loop to iterate through loops
			if(row[i].length() == 0){						//if there is nothing in the List
				continue;							//do nothing
			}else{
				row[i].moveFront();
				while(row[i].index() >= 0){
					Entry y = (Entry) row[i].get();				//get the entry from each this Matrix entry
					double a = y.value * x;					//multiply elements by x
					M.changeEntry(i,y.column,a);				//change entry of new Matrix M
					row[i].moveNext();
				}
			}
		}return M;
	}		
	
	//returns a new Matrix that is the sum of this matrix with M
	//pre: getSize() == M.getSize()
	Matrix add(Matrix M){
		if(getSize() != M.getSize()){							//check precondition
			throw new RuntimeException("Marix error: add() called with getSize() != M.getSize()");
		}
		Matrix A = new Matrix(this.getSize());
		if(this.equals(M)){								//if we are adding matrix M by itself
			A = M.scalarMult(2);							//multiply M by a scalar ot 2
		}else{
			for(int i = 1;  i <= matrixSize; i++){
				if(row[i].length() == 0 && M.row[i].length() == 0){		//if both rows are empty, do nothing
					continue;
				}
				else if(row[i].length() != 0 && M.row[i].length() == 0){	//if second list is empty, get entries from first
					//insert entries from 1st matrix into A
					row[i].moveFront();
					while(row[i].index() >= 0){
						Entry z = (Entry) row[i].get();
						A.changeEntry(i,z.column,z.value);
						row[i].moveNext();
					}
				}
				else if(row[i].length() == 0 && M.row[i].length() != 0){	//if first list is empty, get entries from second
					//insert entries from second matrix into A				
					M.row[i].moveFront();
					while(M.row[i].index() >= 0){
						Entry w = (Entry) M.row[i].get();
						A.changeEntry(i,w.column,w.value);
						M.row[i].moveNext();
					}
				}else{								//at this point both lists have something
					row[i].moveFront();
					M.row[i].moveFront();
					while(row[i].index() >= 0 || M.row[i].index() >= 0){	
						Entry x =(Entry) row[i].get();
						Entry y =(Entry) M.row[i].get();
						if(row[i].index() >= 0 && M.row[i].index() == -1){
							A.changeEntry(i,x.column, x.value);
							row[i].moveNext();
						}
						else if(row[i].index() == -1 && M.row[i].index() >= 0){
							A.changeEntry(i,y.column, y.value);
							M.row[i].moveNext();
						}else{
							if(x.column == y.column){
								A.changeEntry(i,x.column, x.value + y.value);
								row[i].moveNext();
								M.row[i].moveNext();
							}
							else if(x.column < y.column){
								A.changeEntry(i,x.column,x.value);
								row[i].moveNext();
							}else if (x.column > y.column){
								A.changeEntry(i,y.column,y.value);
								M.row[i].moveNext();
							}
						}
					}
				}
			}
		}return A;		
	}
	//returns a new Matrix that is the difference of this matrix with M
	//pre: getSize() == M.getSize()
	Matrix sub(Matrix M){
		Matrix sum = new Matrix(this.getSize());
		M = M.scalarMult(-1);								//multiply M by -1
		sum = this.add(M);								//add this matrix with M
		return sum;
	}

	//returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){
		Matrix M = new Matrix(this.matrixSize);
		for(int i = 1; i <= matrixSize; i++){							//iterate through rows
			if(row[i].length() == 0){							//if there is nothing in the row (list is empty) skip the row
				continue;
			}
			row[i].moveFront();
			while(row[i].index() >= 0){
				Entry x = (Entry)row[i].get();						//get the entry from each this Matrix entry
				if(x.value == 0){
					row[i].moveNext();
				}else{
					M.changeEntry(x.column,i,x.value);
					row[i].moveNext();						//increment through this Matrix
				}
			}
		}
		return M;
	}
	
	//helper dot function
	private static double dot(List P, List Q){
		double a = 0;
		//if P.length > 0; moveFront
		if(P.length() > 0){
			P.moveFront();
		}
		//if Q.length > 0; moveFront
		if(Q.length() > 0){
			Q.moveFront();
		}
		while(P.index() >= 0 && Q.index() >= 0){
			Entry x = (Entry) P.get();
			Entry y = (Entry) Q.get();
			if(x.column > y.column){
				Q.moveNext();
			}
			else if(y.column > x.column){
				P.moveNext();
			}else{										//x.column == y.column
				a += (x.value * y.value);						//dot = dot + (x.value + y.value)
				P.moveNext();
				Q.moveNext();
			}
		}
		return a;
	}	

	//returns a new matrix that is the product of this matrix with M
	//pre: getSize() == M.getSize()	
	Matrix mult(Matrix M){
		Matrix N = new Matrix(getSize());				
		Matrix O = M.transpose();
		if(getSize() != M.getSize()){
			throw new RuntimeException("calling mult with dif size matrices");
		}
		for(int i = 1; i <= matrixSize; i++){
			for(int j = 1; j <= matrixSize; j++){
				double x = dot(row[i], O.row[j]);
				if(x != 0){
					N.changeEntry(i,j,x);
				}
			}
		}
		return N;
	}
}
