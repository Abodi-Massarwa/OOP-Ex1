package Ex1;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	//public static final Comparator<Monom> _Comp = new Monom_Comperator();
//	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	
	public Monom(String s) {;	
	for (int i = 0; i < s.length(); i++) {

		if (s.charAt(i) == 'x' ) {
		String a = 	s.substring(0,i);	
		 if (a.equals("-")) a = "-1";					
		 else if (a.equals("")) a+=1;				
		try {
			this.set_coefficient(Double.parseDouble(a));
		} catch (Exception e) {

		}
		set_power(setP(s));
		return;
		}
	}
	try {
		this.set_coefficient(Double.parseDouble(s));
		set_power(0);
		
	} catch (Exception e) {

	}			
	}

	public void add(Monom m) {
	
	if(this.get_power() != m.get_power()) {
		System.out.println("------------------------------------");
		System.out.println("ERROR: The Power of the Monoms is Different"); 
		System.out.println("------------------------------------");
		return;
	}	
		this._coefficient= this._coefficient+m._coefficient;//1.70000002
		String s = ""+String.format("%.1f", this.get_coefficient());
		this._coefficient=Double.parseDouble(s);

	}
	
	public void multipy(Monom d) {
			this._coefficient= this._coefficient*d._coefficient;
			String s = ""+String.format("%.1f", this.get_coefficient());
			this._coefficient=Double.parseDouble(s);
			this._power= this._power+d._power;
	}
	
	public String toString() {
		String ans;
		if (get_coefficient() == 0) ans = "0";					
		else if (get_power()==1) ans = get_coefficient() + "x";
		else if (get_power()==0) ans = ""+get_coefficient();
		else	ans = get_coefficient() + "x^"+get_power();		
		return ans;
	}
	// you may (always) add other methods.
	
	/** 
	 * changing the Monom from positive to minus.
	 */
	public void sub(Monom m) {		// 
		m.set_coefficient(m._coefficient*-1);
	}

	
	@Override
	public function initFromString(String s) {
		return new Polynom(s);
	}
	@Override
	public function copy() {
		return new Monom(toString());
	}
	//****************** Private Methods and Data *****************

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(_coefficient);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + _power;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Polynom)) return false;
		if(obj instanceof Polynom) {
			for (int i = -10; i < 10; i++) {
				if(((Polynom) obj).f(i)!=f(i)) {
					return false;
				}
			}			
		}
		return true;
	}
	
	public void divid(Monom d) {
		this._coefficient= this._coefficient/d._coefficient;
		String s = ""+String.format("%.1f", this.get_coefficient());
		this._coefficient=Double.parseDouble(s);
		this._power= this._power-d._power;
		
}
	/** 
	 * getting the power of the monom from String s.
	 * @return 
	 */
	private int setP(String s) {

		for (int i = 0; i < s.length(); i++) {
			
			if (s.charAt(i) == 'x' && i !=s.length()-1) {
				if (s.charAt(i+1) == '^') {
					
					String a = 	s.substring(i+2,s.length());
					try {
						return Integer.parseInt(a);
					} catch (Exception e) {
					//	System.out.println(a+" is not integer");
					}				
				}
				else System.out.println("Warrning: "+s+"  is not a Monom!!");
				
			}		
		}
		
		return 1;
	}
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;

	
	

	
	
}
