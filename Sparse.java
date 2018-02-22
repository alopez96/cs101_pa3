//Arturo Lopez
//alopez96
//pa3
//Sparse.java
import java.io.*;
import java.util.Scanner;
   
public class Sparse{
   public static void main(String[] args) throws IOException{

	System.out.println("Hello this is Sparse");
	if(args.length < 2){
		System.err.println("Usage Sparse infile outfile");
		System.exit(1);
	}
	Scanner in = new Scanner(new File(args[0]));			//input file
	PrintWriter out = new PrintWriter(new FileWriter(args[1]));	//output file
	
	int size = Integer.valueOf(in.next());				//set the size equal to the first entry value
	int ANNZ = Integer.valueOf(in.next());				//set the NNZ for A to the second entry value
	int BNNZ = Integer.valueOf(in.next());				//set the NNZ for B to the third entry value
	
	Matrix A = new Matrix(size);					//create new Matrix A of size int size
	Matrix B = new Matrix(size);					//create new Matrix B of size int size

	for(int i = 1; i <= ANNZ; i++){					//get values into matrix A
		A.changeEntry(Integer.valueOf(in.next()), Integer.valueOf(in.next()), Double.valueOf(in.next()));
	}
	for(int i = 1; i <= BNNZ; i++){					//get values into matrix B
		B.changeEntry(Integer.valueOf(in.next()), Integer.valueOf(in.next()), Double.valueOf(in.next()));
	}
	
	out.println("A has " + ANNZ + " non-zero entries:");		//print to output file
	out.println(A.toString());					//print matrix A
	
	out.println("B has " + BNNZ + " non-zero entries:");
	out.println(B.toString());					//print matrix B
	
	out.println("(1.5)*A =");					//print 1.5*A to output file
	Matrix C = A.scalarMult(1.5);					
	out.println(C.toString());					//print matrix A
			
	out.println("A+B =");						//print 1.5*A to output file
	Matrix D = A.add(B);						//A + B					
	out.println(D.toString());					

	out.println("A+A =");					
	Matrix E = A.add(A);						//A + A
	out.println(E.toString());
	
	out.println("B-A =");
	Matrix F = B.sub(A);						//A - B
	out.println(F.toString());
	
	out.println("A-A =");
	Matrix G = A.sub(A);						//A - B
	out.println(G.toString());
	
	out.println("Transpose(A) =");
	Matrix H = A.transpose();					//Transpose A
	out.println(H.toString());
	
	out.println("A*B =");
	Matrix I = A.mult(B);						//A*B
	out.println(I.toString());
	
	out.println("B*B =");
	Matrix J = B.mult(B);						//B*B
	out.println(J.toString());
	
      	in.close();
      	out.close();
   }
}
