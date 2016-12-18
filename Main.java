import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main{
	
	HashMap<Integer,DecisionNode> attributeMap = new HashMap<Integer,DecisionNode>();
	HashMap<String,ArrayList<String>> atttributeValue = new HashMap<String,ArrayList<String>>();
	ArrayList<String> attributes = new ArrayList<String>();
	
	HashMap<Integer,HashMap<String,String>> dataSet = new HashMap<Integer,HashMap<String,String>>();
	
	HashMap<String, HashMap<String,Integer>> countRelativeAttr = new HashMap<String, HashMap<String,Integer>>();
	
	HashMap<String,HashMap<String,Integer>> countofclassvalues = new HashMap<String,HashMap<String,Integer>>();
	
	HashMap<String,Integer> countRelativeGain = new HashMap<String,Integer>();
	
	int numYes=0,numNo=0,numYouth=0,numMiddle_age=0,numSenior=0;
	double infoGain;
	
public void rdFile(){
		String fileName = "/Users/Gunjan/Documents/workspace/DecisionTree/src/input.txt";
        String line = null;
        
        
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            int index = 0,elementNum=5;
            
            while((line = bufferedReader.readLine()) != null) {
            	index++;
           
            	String[] arr = line.split(",");
            	DecisionNode dn = new DecisionNode();
            	dn.age = arr[0];
            	dn.income = arr[1];
            	dn.student = arr[2];
            	dn.creditRanking = arr[3];
            	dn.decision = arr[4];
            	attributeMap.put(index, dn);
            	
            	//System.out.println(attributes.get(0)+ "last" + attributes.get(4));
            	
            	
            	HashMap<String,String> valueOfeachObject = new HashMap<String,String>();
            	for(int i=0; i<elementNum; i++){
            		valueOfeachObject.put(attributes.get(i),arr[i]);
            	}
            	dataSet.put(index, valueOfeachObject);
            	
            }
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
	}

public void printMap(){
	
//	for(Map.Entry<String,Integer> j : countRelativeGain.entrySet()){
//		System.out.println(j.getKey() +":");
//		System.out.println(j.getValue());
//	}
	
	System.out.println("<---------------Attribute Class Value HashMap--------------->");
	
	for(Map.Entry<String, HashMap<String,Integer>> i : countofclassvalues.entrySet()){
		System.out.print(i.getKey() + "------>");
		for(Map.Entry<String,Integer> j : i.getValue().entrySet()){
			System.out.print(j.getKey() + " : ");
			System.out.println(j.getValue());
		}
	}
	
}

public void addAttributeValues(){
	ArrayList<String> values1 = new ArrayList<String>();
	values1.add("youth");
	values1.add("middle_aged");
	values1.add("senior");
	ArrayList<String> values2 = new ArrayList<String>();
	values2.add("high");
	values2.add("medium");
	values2.add("low");
	ArrayList<String> values3 = new ArrayList<String>();
	values3.add("yes");
	values3.add("no");
	ArrayList<String> values4 = new ArrayList<String>();
	values4.add("fair");
	values4.add("excellent");
	ArrayList<String> values5 = new ArrayList<String>();
	values5.add("yes");
	values5.add("no");
	
	atttributeValue.put("age",values1);
	atttributeValue.put("income",values2);
	atttributeValue.put("student",values3);
	atttributeValue.put("credit_rating",values4);
	atttributeValue.put( "decision",values5);
	
	attributes.add("age");
	attributes.add("income");
	attributes.add("student");
	attributes.add("credit_rating");
	attributes.add("decision");
}

public void getCountAge(){
	
	for(Map.Entry<Integer, DecisionNode> i : attributeMap.entrySet()){
		if(i.getValue().decision.equals("no")){
			numNo++;
		}
		else{
			numYes++;
		}
	}
}

public void findInfoGain(){
	int numTotal = numYes+numNo;
	
	double ratio1 = (double)numYes/numTotal;
	double ratio2 = (double)numNo/numTotal;

	double term1 = -ratio1 * ((Math.log(ratio1))/(Math.log(2)));
	double term2 = -ratio2 * ((Math.log(ratio2))/(Math.log(2)));

	infoGain = term1+term2;
//	System.out.println();
//	System.out.println("<----------------Printing InfoGain HashMap---------------->");
}

public void findRelativeGain(){	
	for(Map.Entry<String,ArrayList<String>> j: atttributeValue.entrySet()){
		HashMap<String,Integer> t = new HashMap<String,Integer>();
		informationgainattribute(j.getKey());
		ArrayList<String> temp = j.getValue();
		for(int k=0; k<temp.size();k++){
			int x = countAttributes(temp.get(k), j.getKey());
			t.put(temp.get(k), x);
			countRelativeGain.put(temp.get(k), x);
		}
		countRelativeAttr.put(j.getKey(), t);
	}
}


public int countAttributes(String value,String mainAttr){
	int count=0;
	for(Map.Entry<Integer, HashMap<String,String>> i : dataSet.entrySet()){
		HashMap<String,String> map = i.getValue();
		for(Map.Entry<String,String> j : map.entrySet()){
			if(j.getValue().equals(value) && j.getKey().equals(mainAttr)){
				count++;
			}
			
		}
	}
	return count;
}

public void findDecisionCount(){
	for(Map.Entry<String,ArrayList<String>> j: atttributeValue.entrySet()){
		HashMap<String,Integer> t = new HashMap<String,Integer>();
		ArrayList<String> temp = j.getValue();
		for(int k=0; k<temp.size();k++){
			
		}
	}
}


public int countDecisions(String AttrName,String value){
	int count=0;
	for(Map.Entry<Integer, HashMap<String,String>> i : dataSet.entrySet()){
		HashMap<String,String> map = i.getValue();
		for(Map.Entry<String,String> j : map.entrySet()){
			if(j.getValue().equals(value) && j.getValue().equals(AttrName)){
				count++;
			}
			
		}
	}
	
	return count;
}


public double informationgainattribute(String attriname) {
	HashMap<String,Integer> attributetypefreq = new HashMap<String,Integer>();
	ArrayList<String> attributesubvalues = atttributeValue.get(attriname);

	for(String eachvalue:attributesubvalues){
		HashMap<String,Integer> tempstorage = new HashMap<String,Integer>();
		for(int rownumber:dataSet.keySet()){
			HashMap<String,String> selecrow = (HashMap<String,String>) dataSet.get(rownumber);
			
				if(selecrow.get(attriname).equals(eachvalue)){
				if(tempstorage.containsKey(selecrow.get("decision"))){
					tempstorage.put(selecrow.get("decision"),tempstorage.get(selecrow.get("decision"))+1);
				}else{
							tempstorage.put(selecrow.get("decision"),1);	
					}
			}
			
		countofclassvalues.put(eachvalue, tempstorage);	
					
		}
	}
	
	for(int rownumber:dataSet.keySet()){
		HashMap<String,String> selecrow = (HashMap<String,String>) dataSet.get(rownumber);
		String attritype = selecrow.get(attriname);
		if(!attributetypefreq.containsKey(attritype)){
			attributetypefreq.put(attritype, 1);
		}else{
			attributetypefreq.put(attritype,attributetypefreq.get(attritype)+1);
		}
		
	}
	double attriinfo = 0;
	double pi = 0;
	double attrieachsubtypeinfo = 0;
	
	
	for(String aname : attributetypefreq.keySet()){
		int countname = attributetypefreq.get(aname);
		ArrayList<Integer> classvariableval = new ArrayList<Integer>();
		if(countofclassvalues.containsKey(aname)){
			HashMap<String,Integer> classvariablemap =new HashMap<String,Integer>(countofclassvalues.get(aname));
			for(String eachclassvariable: classvariablemap.keySet() ){
				classvariableval.add(classvariablemap.get(eachclassvariable));
			}
		}
		
		
	    pi = (double)countname/dataSet.keySet().size();
		
	    
//	    System.out.println("Attribute : " + attriname);
//		System.out.println("Attribute SubType: " + aname);
		attrieachsubtypeinfo = infoD(classvariableval,countname);
		attriinfo +=(double)attrieachsubtypeinfo*pi;
		
//		System.out.println("Attribute Information Gain : " + attriinfo);
//		System.out.println();
		
	}
	return attriinfo;
}

public double infoD(ArrayList<Integer>cvariablescountset,int tuplecount){
	double info = 0;
			
	for(int i = 0 ; i < cvariablescountset.size() ; i++){
		double var1 = ((double)cvariablescountset.get(i) / tuplecount); 
		info += - (var1 * (Math.log(var1) / Math.log(2)));  
	}
	
	return info;
}

public static void main(String args[]){
	Scanner sc = new Scanner(System.in);
	Main m = new Main();
	m.addAttributeValues();
	m.rdFile();
	m.getCountAge();
	m.findInfoGain();
	m.findRelativeGain();
	m.printMap();
	//m.printGainRatio();
m.printGini();
	int X = m.countDecisions("youth", "yes");
	//m.informationgainattribute();
	
//	System.out.println("Enter attribute value for age : ");
//	String x = sc.nextLine();
//	System.out.println("Enter attribute value for income: ");
//	String y = sc.nextLine();
//	System.out.println("Enter attribute value for student : ");
//	String z = sc.nextLine();
//	System.out.println("Enter attribute value for credit_rating : ");
//	String c = sc.nextLine();
//	System.out.println("Decision value is No");
}
}
