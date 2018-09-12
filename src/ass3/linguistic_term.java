/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass3;

import java.util.ArrayList;

/**
 *
 * @author HP.HDS
 */
public class linguistic_term 
{
    public  String fuzzy_set_name;
    
	public  String set_type;
        
	public  ArrayList<Integer> points= new ArrayList<Integer>() ;
        
	public  double memebership;
	
        public  double calculate_y_triangle(int value )
	{
	    //// y=ax+b 
		double y;
		int x_lower , x_upper ;
		int y_lower=0   ;
		int y_upper =1 ;
		double slope;
		if((value >= points.get(0) ) && (value < points.get(1)) )
		{
			x_lower = points.get(0);
			x_upper=points.get(1);
			slope= (y_upper-y_lower) /(double) (x_upper -x_lower)  ;
			y= (double)slope * (value-x_lower);
			y=(double)y+ y_lower;
			//System.out.println("h1");
			return y;
			
		}
		else if((value >=points.get(1))&&(value<points.get(2)))
		{
			x_lower=points.get(1);
			x_upper=points.get(2);
			slope=(y_upper-y_lower)/(double)(x_lower-x_upper) ;
			y=slope*(value-x_lower);
			y=y+y_upper;
			//System.out.println("h2");
			return y ;
		}
               //System.out.println("h3 from last return ...");
			return 0.0;
	}
	
        public  double  calculate_y_trapezoidal(int value )
	{
		double y;
		int x_lower , x_upper ;
		int y_lower=0   ;
		int y_upper =1 ;
		double slope;
		if( (value >= points.get(0)) && (value<points.get(1)) )
		{
			x_lower = points.get(0);
			x_upper=points.get(1);
			slope= (y_upper-y_lower) / (double)(x_upper -x_lower)  ;
			y= slope * (value-x_lower);
			y=y+ y_lower;
			return y;
		}
		else if( (value>=points.get(1)) && (value<points.get(2)))
		{
			return 1.0;
		}
		else if((value>=points.get(2)) && (value<points.get(3)) )
		{
			x_lower=points.get(2);
			x_upper=points.get(3);
			slope=(y_upper-y_lower)/(double)(x_lower-x_upper) ;
			y=(double)slope*(value-x_lower);
			y=y+y_upper;
			return (double)y ;
		}
                 //System.out.println("h3 from last return ...");
		return 0.0;
	}
}
