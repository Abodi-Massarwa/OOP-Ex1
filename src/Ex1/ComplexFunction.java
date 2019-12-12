package Ex1;

public class ComplexFunction implements complex_function {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	function left;
	function right;
	Operation p;
	
	/////////////////////////   Constructors  /////////////////////////
	public ComplexFunction() {
		left=new Polynom("0");
		right=null;
		p= Operation.None;
	}
	public ComplexFunction(String op, function f1, function f2) {				
		left = f1;
		right =f2;		
		try {
			this.p= Operation.valueOf(op);
			
		} catch (Exception e) {
			System.out.println("------------------------");
			System.out.println(op +" is not operation!!!");
			System.out.println("------------------------");
		}				
	}
	
		
	public ComplexFunction(Operation op, function f1, function f2) {				
		left = f1;
		right =f2;		
		try {
			p=op;
		} catch (Exception e) {
			System.out.println("------------------------");
			System.out.println(op +" is not operation!!!");
			System.out.println("------------------------");
		}				
	}

	public ComplexFunction(Operation op, function p1, ComplexFunction cf1) {
		left = p1;
		right =cf1;		
		try {
			p=op;
		} catch (Exception e) {
			System.out.println("------------------------");
			System.out.println(op +" is not operation!!!");
			System.out.println("------------------------");
		}	
	}

	public ComplexFunction(function p1) {
		p=Operation.None;
		left=p1;
		right=null;
	}
	public ComplexFunction(ComplexFunction cf) {
		p=Operation.None;
		left=cf;
		right=null;
	}
	
	/////////////////////////   Operators methods  /////////////////////////
	@Override
	public void plus(function f1) {
		function f2 = initFromString(this.toString());
		this.left=f2;
		this.right=f1;
		this.p=Operation.Plus;
		
	}

	@Override
	public void mul(function f1) {
		function f2 = initFromString(this.toString());
		this.left=f2;
		this.right=f1;
		this.p=Operation.Times;
		
	}

	@Override
	public void div(function f1) {
		function f2 = initFromString(this.toString());
		this.left=f2;
		this.right=f1;
		this.p=Operation.Divid;
		
	}

	@Override
	public void min(function f1) {
		function f2 = initFromString(this.toString());
		this.left=f2;
		this.right=f1;
		this.p=Operation.Min;	
		
	}

	@Override
	public void max(function f1) {
		function f2 = initFromString(this.toString());
		this.left=f2;
		this.right=f1;
		this.p=Operation.Max;
	}
	@Override
	public void comp(function f1){
		function f2 = initFromString(this.toString());
		this.left=f2;
		this.right=f1;
		this.p=Operation.Comp;	
	}


	@Override
	public double f(double x) {		
	//	if(right==null)	return	0;
		if(this.left()==null)	return	0;

		double fx=0;
		switch (p) {
		case Plus:
			fx=left().f(x)+right().f(x);
			return fx;
			
		case Times:
			fx=left().f(x)*right().f(x);
			return fx;
		case Divid:
			fx=left().f(x)+right().f(x);
			return fx;
			
		case Max:
			if(left().f(x)<right().f(x)) return right().f(x);
			return left().f(x);
			
		case Min:
			if(left().f(x)>right().f(x)) return right().f(x);
			return left().f(x);
			
		case Comp:
			return left.f(right.f(x));
	
		default:
			break;
		}
		return left.f(x);
	}

	@Override
	public function copy() {
		ComplexFunction cf2 = new ComplexFunction();
		return cf2.initFromString(p+"("+left+","+right+")");		
	}
	
	// Convert from String to ComplexFunction
	@Override
	public function initFromString (String s) {
		ComplexFunction cf1 = new ComplexFunction();
		int st=0;
		boolean isNull=true;
		for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)=='(') {
			st=i;
			String op=s.substring(0, i);
			try {
				cf1.p=Operation.valueOf(op);
				break;
			} catch (Exception e) {
				cf1.p=Operation.Error;
				System.out.println("------------------------");
				System.out.println(op+" is not operation!!!");
				System.out.println("------------------------");
				return null;			
			}
		}			
		}
		// check if s is function!
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)==',' ||  s.charAt(i)=='(') {
				isNull=false;
				break;
			}			
		}
		if (isNull) {
			Polynom p1 = new Polynom(s);
			if(s.equals(new String("0"))) return new Polynom("0");
			if(p1.isZero()) {
				System.out.println("------------------------");
				System.out.println(s+" is not a function!");
				System.out.println("------------------------");
				return null;
			}
			return	p1;
		}
		//check if s is ComplexFunction(p1)!
		isNull=true;
		for (int i = st+1; i < s.length(); i++) {
			if(s.charAt(i)==',') {
				isNull=false;		
				break;
			}
		}
		if (isNull) {		
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i)==')'&& isNull) {
				Polynom p1 = new Polynom(s.substring(st+1, i));			
				if (!p1.isZero()) {
					cf1.left=p1;					
					return cf1;
				}				
			}			
		}
		}
		
		// else: s is complexfunction!
		int c=0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i)=='(') c++;							
			else if(s.charAt(i)==')') c--;
			if (c==1 && s.charAt(i)==',') {
				cf1.left=leftRight(s.substring(st+1,i));
				cf1.right=leftRight(s.substring(i+1,s.length()-1));
			}
		}
		if (left==null) {
			System.out.println("------------------------");
			System.out.println(s + " is not a function!");
			System.out.println("------------------------");
			cf1=null;
		}

		return cf1;
	}

/// toString Function
	@Override
	public String toString() {
		if (right==null) {
			try {
				return p+"("+left+")";
			} catch (Exception e) {
				return  "left function is null!";
			}
		}
			return p+"("+left+","+right+")";
	}
	

///////////////////// Getters functions /////////////////////
	public function left() {
	 if(left==null)	return new Polynom("0");
	 return left;
	}
	public function right() {
		return right;
	}
	public Operation getOp() {		
		if (p==null) return Operation.None;
		else if (p==Operation.Error) {
			System.out.println("------------------------");
			System.out.println(" its2 4unknown operation!");
			System.out.println("------------------------");
			return p;
		}		
		return p;						
	}
	
	////////     private function  //////////
	
	private function leftRight(String s) {
		boolean isNull= true;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)==',') isNull=false;
		}
		if (isNull) {
			Polynom ifPolynom = new Polynom(s);
			if(!ifPolynom.isZero()) return ifPolynom;
		}
		return initFromString(s);
		}



	
	
}