/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author HP.HDS
 */
public class Ass3 {

     /**
     * @param args the command line arguments
     */
        public static ArrayList<linguistic_variable> linguistic_variables;
	public static linguistic_variable output ;
        public static ArrayList<Inference_rule> Inference_rueles ;
	public static int num_variables;
        public static int number_of_inference_rules;
	
	public static void fuzzification()
	{
		for(int i=0 ;i< linguistic_variables.size(); i++)
		{
			for(int j=0 ; j<linguistic_variables.get(i).linguistic_terms.size() ; j++)
			{
				if(linguistic_variables.get(i).linguistic_terms.get(j).set_type.equals("triangle"))
				{
					linguistic_variables.get(i).linguistic_terms.get(j).memebership=linguistic_variables.get(i).linguistic_terms.get(j).calculate_y_triangle(linguistic_variables.get(i).crisp_input);
					//System.out.println();
				}
				else if(linguistic_variables.get(i).linguistic_terms.get(j).set_type.equals("trapezoidal"))
				{
					linguistic_variables.get(i).linguistic_terms.get(j).memebership=linguistic_variables.get(i).linguistic_terms.get(j).calculate_y_trapezoidal(linguistic_variables.get(i).crisp_input);
				}
				else
					System.out.println("Not this or this . ");
			}
		}
	}
	
        public static double get_membership(String var_name , String set_name)
	{
            double membership = 0;
             for(int i =0 ; i<num_variables ; i++)
             {
                if(linguistic_variables.get(i).variablename.equals(var_name))
                {
                    for(int j=0 ; j< linguistic_variables.get(i).number_of_fuzzysets ; j++)
                    {
                       if(linguistic_variables.get(i).linguistic_terms.get(j).fuzzy_set_name.equals(set_name))
                       {
                          membership= linguistic_variables.get(i).linguistic_terms.get(j).memebership ;
                       }
                    }
                }
             }
             return membership;
	}
	
        public static void Inferance()
	{
            double final_result ;
            double x ;
            int size ;
           for(int i=0;i<number_of_inference_rules;i++) // for each rule 
           {
                final_result=get_membership(Inference_rueles.get(i).variable_names.get(0), Inference_rueles.get(i).variable_set.get(0));
                //System.out.print("membership is  "+final_result);
                size=Inference_rueles.get(i).no_variables_in_rule ;
               for(int j =1 ;j<size;j++)
               {
                      x=get_membership(Inference_rueles.get(i).variable_names.get(j) , Inference_rueles.get(i).variable_set.get(j));
                      //System.out.println(" membership is  "+x);
                      if(Inference_rueles.get(i).and_or.get(j-1).equals("AND"))
                      {
                          //System.out.println("AND");
                          if(x<final_result)
                          {
                              final_result=x;
                          }
                      }
                      else  // so it will be OR and get max 
                      {
                          //System.out.println("OR");
                         if(x>final_result)
                         {
                             final_result=x;
                         }
                      }
               }
               //System.out.println();
               Inference_rueles.get(i).output_result_value=final_result;
           }               
	}
	public static double get_output_centroid(String f_set_name)
        {
            int size =output.number_of_fuzzysets ;
            double center =0;
            for(int i=0;i<size;i++)
            {
                if(output.linguistic_terms.get(i).fuzzy_set_name.equals(f_set_name))
                {
                    if(output.linguistic_terms.get(i).set_type.equals("triangle"))
                    {
                        center=(double)output.linguistic_terms.get(i).points.get(1);
                    }
                    else
                    {
                        center=(output.linguistic_terms.get(i).points.get(1)+output.linguistic_terms.get(i).points.get(2) ) / 2.0 ;
                    }
                }
            }
            return center;
        }
	public static double defuzzification()
	{
            double sum_n=0.0;
            double sum_d=0.0;
            double center;
            for(int i=0 ;i<number_of_inference_rules ; i++)
            {
                center=get_output_centroid(Inference_rueles.get(i).output_set_name) ;
                sum_n+=center*Inference_rueles.get(i).output_result_value ;
                sum_d+=Inference_rueles.get(i).output_result_value ;
            }
            return sum_n/(double)(sum_d); // predicted value 
	}
	
	public static void read_file() throws FileNotFoundException
	{
		Scanner scanner;
            scanner = new Scanner(new File("input.txt"));
		num_variables=scanner.nextInt();
		//System.out.println(num_variables);
		
		  linguistic_variable x;
		 linguistic_term y ;
		for(int i=0;i<num_variables;i++)
		{
			x =new linguistic_variable();
			x.variablename=scanner.next();
			//System.out.println(x.variablename);
			x.crisp_input=scanner.nextInt();
			//System.out.println(x.crisp_input);
			x.number_of_fuzzysets=scanner.nextInt();
			
			x.linguistic_terms=new ArrayList<>();
			for(int j=0;j<x.number_of_fuzzysets;j++)
			{
				 y =new linguistic_term();
				 y.fuzzy_set_name=scanner.next();
				 
				 y.set_type=scanner.next();
				 
				 y.points=new ArrayList<>() ;
				 if(y.set_type.equals("trapezoidal"))
				 {
					 for(int k=0;k<4;k++)
					 {
						 y.points.add(scanner.nextInt());
						 
					 }
				 }
				 else
				 {
					 for(int k=0;k<3;k++)
					 {
						 y.points.add(scanner.nextInt());
						 
					 } 
				 }
				 x.linguistic_terms.add(y); 
			}
			linguistic_variables.add(x);
		}
////////////////////////////////////////////////////////////  output variable  
		output.variablename=scanner.next();
		output.number_of_fuzzysets=scanner.nextInt();
		output.linguistic_terms=new ArrayList<>();
		
		for(int i=0;i<output.number_of_fuzzysets;i++)
		{
			y=new linguistic_term();
			y.fuzzy_set_name=scanner.next();
			y.set_type=scanner.next();
			y.points=new ArrayList<>();
			if(y.set_type.equals("trapezoidal"))
			 {
				 for(int k=0;k<4;k++)
				 {
					 y.points.add(scanner.nextInt());
					 
				 }
			 }
			 else
			 {
				 for(int k=0;k<3;k++)
				 {
					 y.points.add(scanner.nextInt());
					 
				 } 
			 }
			output.linguistic_terms.add(y);
		}
/////////////////////////////////////////////////////////////// Reading inferance rules 
               
            number_of_inference_rules= scanner.nextInt();
            for(int j=0 ; j<number_of_inference_rules;j++)
            {
                Inference_rule r =new Inference_rule();
                r.variable_names=new ArrayList<>();
                r.variable_set=new ArrayList<>();
                r.and_or=new ArrayList<>();
                r.no_variables_in_rule = scanner.nextInt();
                for(int k=0;k<r.no_variables_in_rule;k++)
                {
                    r.variable_names.add(scanner.next());
                    String equal =scanner.next();
                    //System.out.print(equal);
                    r.variable_set.add(scanner.next());
                    if(k != (r.no_variables_in_rule-1))
                    {
                        r.and_or.add(scanner.next());
                    }
                    else
                    {
                        String then = scanner.next();
                        //System.out.print(then);
                    }  
                }
                r.output_variable_name=scanner.next();
                String equal=scanner.next();
                //System.out.println(equal);
                r.output_set_name=scanner.next();
                Inference_rueles.add(r);
            }       
		scanner.close();
	}
	
	public static void print()
	{
		for(int i=0;i<linguistic_variables.size();i++)
		{
			System.out.println("variable name "+linguistic_variables.get(i).variablename);
			System.out.println("crisp input "+linguistic_variables.get(i).crisp_input);
			System.out.println("number of fuzzsets "+linguistic_variables.get(i).number_of_fuzzysets);
			
			for(int j=0;j<linguistic_variables.get(i).linguistic_terms.size();j++)
			{
				System.out.println("fuzzysetname  "+linguistic_variables.get(i).linguistic_terms.get(j).fuzzy_set_name);
				System.out.println("set type "+linguistic_variables.get(i).linguistic_terms.get(j).set_type);
				System.out.println("membership is =  "+linguistic_variables.get(i).linguistic_terms.get(j).memebership);
				System.out.println("points ");
				for(int k=0;k<linguistic_variables.get(i).linguistic_terms.get(j).points.size();k++)
				{
					System.out.println(linguistic_variables.get(i).linguistic_terms.get(j).points.get(k));
				}
			}
		}
		
	}
    public static void main(String[] args) throws FileNotFoundException
    {
        // TODO code application logic here
        linguistic_variables=new ArrayList<>();
         Inference_rueles=new ArrayList<>();
        output =new linguistic_variable();
	read_file();
        
//		for(int i=0 ;i<number_of_inference_rules;i++)
//                {
//                    System.out.println(Inference_rueles.get(i).output_set_name);
//                }
		fuzzification();
                System.out.println("After applying Fuzzification  ");
		for(int i=0;i<linguistic_variables.size();i++)
		{
			for(int j=0;j<linguistic_variables.get(i).linguistic_terms.size();j++)
			{
				System.out.println("variable number "+(i+1) +" "+"set number "+(j+1) +" membership is "+ linguistic_variables.get(i).linguistic_terms.get(j).memebership);
			}
		}
		      //System.out.println(get_membership("position","Left"));
		Inferance();
                System.out.println("After applying Inference ");
                for(int i=0;i<number_of_inference_rules;i++)
                {
                    System.out.println(Inference_rueles.get(i).output_result_value);
                }
                System.out.println("Final prediction result =  "+defuzzification());
//		System.out.println(linguistic_variables.size());
//		System.out.println(linguistic_variables.get(0).variablename);
//		System.out.println(output.linguistic_terms.size());
//		System.out.println(linguistic_variables.get(0).linguistic_terms.get(0).points.size());
//		System.out.println(output.linguistic_terms.get(0).set_type);
//		System.out.println(output.linguistic_terms.get(0).points.size());

    }
}
