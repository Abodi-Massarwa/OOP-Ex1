package Ex1;
/** https://introcs.cs.princeton.edu/java/15inout/FunctionGraph.java.html
 * 	https://introcs.cs.princeton.edu/java/15inout/
 *  https://introcs.cs.princeton.edu/java/stdlib/StdDraw.java.html
 */


import java.awt.Color;
import java.awt.Font;



public class FunctionGraph {
	public static void main(String[] args) throws InterruptedException {
		StdDraw.setCanvasSize(700, 700);
		// number of line segments to plot
		int n = 500;
		
		Polynom test = new Polynom("x^2");
		double max_x_axis=20 , min_x_axis=-20;
		double max_y_axis=100 , min_y_axis=-100;

		// the function y = sin(4x), sampled at n+1 points
		// between x = 0 and x = pi
		int j=-n/2;
		double[] x = new double[n+1];
		double[] y = new double[n+1];
		for (int i = 0; i <= n; i++) {
			x[i] = 100* j / n;
			y[i] = test.f(x[i]);
			j++;
		}		
		// rescale the coordinate system
		StdDraw.setXscale(min_x_axis, max_x_axis);
		StdDraw.setYscale(min_y_axis, max_y_axis);
		
		//////// vertical lines
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for (int i = 0; i <= n; i=i+10) {
			StdDraw.line(x[i], min_y_axis, x[i], max_y_axis);
		}
		//////// horizontal  lines
		for (double i = min_y_axis; i <= max_y_axis; i=i+10) {
			StdDraw.line(min_x_axis, i, max_x_axis, i);
		}
		//////// x axis		
		StdDraw.setPenColor(Color.red);
			StdDraw.setPenRadius(0.005);
		StdDraw.line(min_x_axis, 0, max_x_axis, 0);
		StdDraw.setFont(new Font("TimesRoman", Font.ITALIC, 15));
		for (double i = min_x_axis; i <= max_x_axis; i=i+5) {
			StdDraw.text(i, -5, Double.toString(i));
			//StdDraw.text
			
		}
		//////// y axis	
		StdDraw.line(0, min_y_axis, 0, max_y_axis);
		for (double i = min_y_axis; i <= max_y_axis; i=i+5) {
			StdDraw.text(-0.7, i+0.5, Double.toString(i));
			
		}
		
		// plot the approximation to the function
		for (int i = 0; i < n; i++) {
			Thread.sleep(1);
			StdDraw.line(x[i], y[i], x[i+1], y[i+1]);
			
			
		}
		
		}
		
	}

